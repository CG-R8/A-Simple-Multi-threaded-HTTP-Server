package multiThreaded;

import java.util.StringTokenizer;

public class Client_Request {

	public static String URL;
	public static String req_resourse_ulr;

	public static void Req_handler(String first_request_line) {

		StringTokenizer st = new StringTokenizer(first_request_line);
		{
			st.nextToken();
			URL = st.nextToken();
			req_resourse_ulr = URL;
			URL = URL.substring(1);
			System.out.println("URL : " + URL);
		}
		String[] resource = URL.split("/");
	}
}
