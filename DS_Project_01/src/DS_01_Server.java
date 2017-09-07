
import java.net.*;
import java.io.*;

public class DS_01_Server extends Thread {
	private ServerSocket serverSocket;
	public OutputStream output = null;

	public DS_01_Server() throws IOException {
		serverSocket = new ServerSocket(8080);
		serverSocket.setSoTimeout(100000);
	}

	public void run() {
		while (true) {
			try {
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();
				System.out.println("Connection Done");
				InputStream input = server.getInputStream();
				output = server.getOutputStream();
				int i;
				byte[] buffer = new byte[2048];
				StringBuffer request = new StringBuffer(2048);;
				try {
					i = input.read(buffer);
					for (int j = 0; j < i; j++) {
						request.append((char) buffer[j]);
					}
					System.out.println(request.toString());
				} catch (IOException e) {
					e.printStackTrace();
					i = -1;
				}
				//System.out.println("Printed the GET request");
				String request_tocken = request.toString();
				String[] lines = request_tocken.split(System.getProperty("line.separator"));
				for (int k = 0; k < lines.length; k++) {
					// System.out.println(":: " + lines[k] +lines.length);
				}
				if (lines[0].substring(0, 3).compareToIgnoreCase("GET") == 0) {
					//System.out.println("This is the GET request");
					Client_Request.Req_handler(lines[0]);
				}
				else
				{
					System.out.println("This is not the GET request");
				}
				Server_Response SR = new Server_Response();
				if (SR.sendStaticResource(output) != 0) {
					System.out.println("We are good to send the reponse");
				}
				server.close();
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public static void main(String[] args) {
		try {
			Thread t = new DS_01_Server();
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}