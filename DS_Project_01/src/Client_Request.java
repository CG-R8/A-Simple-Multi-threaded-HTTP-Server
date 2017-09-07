import java.util.StringTokenizer;

public class Client_Request {

	public static String URL;

	public static void Req_handler(String first_request_line) {
		// TODO Auto-generated method stub
		System.out.println("First line tokenisation " + first_request_line);

		StringTokenizer st = new StringTokenizer(first_request_line);
		{
			st.nextToken();
			URL = st.nextToken().substring(1);
			System.out.println("URL : " + URL);
		}

		String[] resource = URL.split("/");
		System.out.println("File name requested is : " + resource[resource.length - 1]);

	}
}
