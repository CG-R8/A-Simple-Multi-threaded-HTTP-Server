package multiThreaded;

import java.lang.reflect.Array;
import java.util.Arrays;

public class count_hits {

	static String[][] req_res_array = new String[15][5];
	static int count = 0;

	public static void process_req_resourses() {

		String request = Client_Request.req_resourse_ulr;
		String request_ip = DS_01_Server.connected_client;

		req_res_array[count][0] = request;
		req_res_array[count][1] = "|";
		req_res_array[count][2] = request_ip;
		req_res_array[count][3] = "|";

		int res_count = 1;
		for (int i = 0; i < count; i++) {
			if (req_res_array[i][0].compareTo(request) == 0) {
				res_count++;
			}
		}
		req_res_array[count][4] = "" + res_count;
		System.out.println("" + Arrays.toString(req_res_array[count]).replaceAll("(\\[|\\]|,)", ""));
		count++;

		System.out.println("====================================");

		for (int i = 0; i < count; i++) {
			for (int j = 0; j < 5; j++) {
				System.out.print("" + req_res_array[i][j]);
			}
			System.out.println();
		}

	}

}
