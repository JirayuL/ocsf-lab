package ocsf;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.lloseng.ocsf.client.AbstractClient;

public class Client extends AbstractClient {
	Object msg;

	public Client(String host, int port) {
		super(host, port);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		this.msg = msg;
		System.out.println(msg);
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
//		System.out.print("Input your IP adrress : ");
//		String MY_IP = scanner.nextLine();
//		System.out.print("Input your port : ");
//		String PORT = scanner.nextLine();
		Client client = new Client("158.108.139.122", 5001);
		try {
			client.openConnection();
			while (client.isConnected()) {
				client.sendToServer("Login Gear");
				for (int i = 0; i < 100000; i++) {
					try {
						TimeUnit.MILLISECONDS.sleep(1000);
						client.sendToServer(client.computeBinary(client.msg));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
//				String input = scanner.nextLine();
//				if (input.equals("quit"))
//					client.closeConnection();
//				client.sendToServer(input);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		scanner.close();
	}
	
	private String computeBinary(Object msg){
		this.msg = msg.toString().substring(8, msg.toString().length() - 1);
		String[] mag = this.msg.toString().split(" ");
		int intBinary1 = Integer.parseInt(Integer.toBinaryString(Integer.parseInt(mag[0])));
		System.out.println(mag[0]);
		String syntax = mag[1];
		int intBinary2 = Integer.parseInt(Integer.toBinaryString(Integer.parseInt(mag[2])));
		System.out.println(mag[2]);
		int result = 0;
		if (syntax.equals("&"))
			result = intBinary1 & intBinary2;
		if (syntax.equals("|"))
			result = intBinary1 | intBinary2;
		if (syntax.equals("^"))
			result = intBinary1 ^ intBinary2;
//		System.out.println(result);
		return Integer.parseInt(String.format("%d", result), 2) + "";
	}

}
