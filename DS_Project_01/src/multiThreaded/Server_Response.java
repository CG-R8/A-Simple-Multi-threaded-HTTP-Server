package multiThreaded;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
import java.util.Date;

import javax.activation.MimetypesFileTypeMap;

public class Server_Response {

	public String success_reponse(String date, Date last_modified_date, long content_length, String content_type) {
		String Resp_headers = "HTTP/1.0 200 OK\r\n";
		Resp_headers = Resp_headers + "Server: " + "Chetan's Server" + "\r\n";
		Resp_headers = Resp_headers + "Date: " + date + "\r\n";
		Resp_headers = Resp_headers + "Last-Modified: " + last_modified_date + "\r\n";
		Resp_headers = Resp_headers + "Content-Length: " + content_length + "\r\n";
		Resp_headers = Resp_headers + "Content-Type: " + content_type + "\r\n";
		Resp_headers = Resp_headers + "\r\n";

		count_hits.process_req_resourses();
		return Resp_headers;
	}

	public String fail_response(String date) {
		String Resp_headers = "HTTP/1.0 404 Not Found\r\n";
		Resp_headers = Resp_headers + "Date: " + date + "\r\n";
		Resp_headers = Resp_headers + "\r\n";
		return Resp_headers;
	}

	public int sendStaticResource(OutputStream output) throws IOException {
		int returncode = 0;
		byte[] bytes = new byte[2048];
		FileInputStream fis = null;
		try {
			// System.out.println(Client_Request.URL);
			File requestedfile = new File(Client_Request.URL);
			// String date =
			// java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME
			// .format(ZonedDateTime.now(ZoneId.of("GMT")));
			String date = "Temporary date";
			long last_modified = requestedfile.lastModified();
			Date last_modified_date = new Date(last_modified);
			// last_modified_date=
			// java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME
			// .format(
			String content_type = new MimetypesFileTypeMap().getContentType(requestedfile);
			long content_length = requestedfile.length();
			System.out.println("Last modified : " + last_modified_date + "\nContent Type: " + content_type);
			System.out.println("Content Length : " + content_length);
			if (requestedfile.exists()) {

				if (Client_Request.URL.compareToIgnoreCase("404.gif") == 0) {
					// System.out.println("File Exist");
					fis = new FileInputStream(requestedfile);
					int ch = fis.read(bytes, 0, 2048);
					byte[] b = fail_response(date).getBytes();
					output.write(b);
					while (ch != -1) {
						output.write(bytes, 0, ch);
						ch = fis.read(bytes, 0, 2048);
					}
				} else {
					// System.out.println("File Exist");
					fis = new FileInputStream(requestedfile);
					int ch = fis.read(bytes, 0, 2048);
					byte[] b = success_reponse(date, last_modified_date, content_length, content_type).getBytes();
					// byte[] b = construct_http_header(200, 3).getBytes();
					output.write(b);
					while (ch != -1) {
						output.write(bytes, 0, ch);
						ch = fis.read(bytes, 0, 2048);
					}
				}

			} else {
				// file not found
				// System.out.println("File DO NOT Exist");
				byte[] b = fail_response(date).getBytes();
				output.write(b);

				fis = new FileInputStream("lmn.html");
				int ch = fis.read(bytes, 0, 2048);

				while (ch != -1) {
					output.write(bytes, 0, ch);
					ch = fis.read(bytes, 0, 2048);
				}

				returncode = 404;
			}
		} catch (Exception e) {
			// thrown if cannot instantiate a File object
			System.out.println("This is the error in server response." + e.toString());

		} finally {
			if (fis != null)
				fis.close();
		}
		return returncode;
	}

}
