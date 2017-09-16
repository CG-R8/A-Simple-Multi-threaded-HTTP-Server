package multiThreaded;

public class count_hits {
	static String[][] req_res_array = new String[50][7];
	static int count = 0;

	public static void process_req_resourses() {
		String request = Client_Request.req_resourse_ulr;
		String request_ip = DS_01_Server.client_Address;
		int port = DS_01_Server.client_Port;
		req_res_array[count][0] = request;
		req_res_array[count][1] = "|";
		req_res_array[count][2] = request_ip;
		req_res_array[count][3] = "|";
		req_res_array[count][4] = "" + port;
		req_res_array[count][5] = "|";
		int res_count = 1;
		for (int i = 0; i < count; i++) {
			if (req_res_array[i][0].compareTo(request) == 0) {
				res_count++;
			}
		}
		req_res_array[count][6] = "" + res_count;
		count++;
		System.out.println("====================================");
		for (int i = 0; i < count; i++) {
			for (int j = 0; j < 7; j++) {
				System.out.print("" + req_res_array[i][j]);
			}
			System.out.println();
		}
	}
}
