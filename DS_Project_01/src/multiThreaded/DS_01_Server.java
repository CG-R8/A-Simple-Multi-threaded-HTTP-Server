package multiThreaded;

import java.net.*;
import java.io.*;

class DS_01_Server_runnable implements Runnable {
	private Thread t;
	public OutputStream output = null;
	private Socket server;

	public DS_01_Server_runnable(Socket server) throws IOException {
		this.server = server;
	}

	@SuppressWarnings({})
	public void run() {
		{
			try {
				InputStream input = server.getInputStream();
				output = server.getOutputStream();
				int i;
				byte[] buffer = new byte[2048];
				StringBuffer request = new StringBuffer(2048);
				try {
					i = input.read(buffer);
					for (int j = 0; j < i; j++) {
						request.append((char) buffer[j]);
					}
				} catch (IOException e) {
					e.printStackTrace();
					i = -1;
				}
				// Printed the GET request
				String request_tocken = request.toString();
				String[] lines = request_tocken.split(System.getProperty("line.separator"));
				for (int k = 0; k < lines.length; k++) {
					// System.out.println(":: " + lines[0] + lines.length);
				}
				if (lines[0].substring(0, 3).compareToIgnoreCase("GET") == 0) {
					// System.out.println("This is the GET request");
					Client_Request.Req_handler(lines[0]);
				} else {
					System.out.println("This is not the GET request");
				}
				Server_Response SR = new Server_Response();
				if (SR.sendStaticResource(output) != 0) {
					// System.out.println("We are good to send the response ");
				}
				System.out.println("====================================");
				System.out.println("Socket closed");
				server.close();
			} catch (SocketTimeoutException s) {
				System.err.println("Socket timed out!");
			} catch (IOException e) {
				System.err.println("IOException occured while reading client request");
			}
		}
	}

	public void start() {
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}
}

public class DS_01_Server {
	public static String connected_client;
	public static String client_Address;
	public static int client_Port;

	public static void main(String args[]) throws IOException {
		File wwwDirctory = new File("www");
		if (wwwDirctory.exists()) {
			// System.out.println("WWW directory exist");
		} else {
			System.err.println("WWW directory is missing.... Existing server.....");
			System.exit(0);
		}
		ServerSocket serverSocket = new ServerSocket(0);
		//ServerSocket serverSocket = new ServerSocket(8080);
		System.out.println("Server socket is created on the port : " + serverSocket.getLocalPort() );
		while (true) {
			Socket server = serverSocket.accept();
			System.out.println("New client connection done : " + server.getRemoteSocketAddress());
			connected_client = server.getRemoteSocketAddress().toString();
			client_Address = server.getInetAddress().toString();
			client_Address = client_Address.substring(1);
			client_Port = server.getPort();
			// System.out.println("[[" + client_Address + "][" + client_Port + "]]");
			DS_01_Server_runnable R1 = new DS_01_Server_runnable(server);
			R1.start();
		}
	}
}
