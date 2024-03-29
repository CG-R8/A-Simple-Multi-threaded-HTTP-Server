

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.activation.MimetypesFileTypeMap;

public class Server_Response {
	public String success_reponse(String date, String last_modified_date_string, long content_length,
			String content_type) {
		String Resp_headers = "HTTP/1.0 200 OK\r\n";
		Resp_headers = Resp_headers + "Server: " + "Chetan's Server" + "\r\n";
		Resp_headers = Resp_headers + "Date: " + date + "\r\n";
		Resp_headers = Resp_headers + "Last-Modified: " + last_modified_date_string + "\r\n";
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
		String RFC_7231_format_string = "EEE, dd MMM yyyy HH:mm:ss zzz";
		SimpleDateFormat RFC_7231_format = new SimpleDateFormat(RFC_7231_format_string);
		RFC_7231_format.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date today_date = new Date();
		String date = RFC_7231_format.format(today_date);
		try {
			// System.out.println("File name ------------->>> " + Client_Request.URL);
			// What if the file name is null.
			if (Client_Request.URL.isEmpty()) {
				// System.out.println("------------------We Got blank URL------------------------");
				// System.out.println("File DO NOT Exist");
				byte[] b = fail_response(date).getBytes();
				output.write(b);
				fis = new FileInputStream("404_html.html");
				int ch = fis.read(bytes, 0, 2048);
				while (ch != -1) {
					output.write(bytes, 0, ch);
					ch = fis.read(bytes, 0, 2048);
				}
				returncode = 404;
			} else {
				Client_Request.URL = "www/" + Client_Request.URL; // Appended www directory
				File requestedfile = new File(Client_Request.URL);
				long last_modified = requestedfile.lastModified();
				Date last_modified_date = new Date(last_modified);
				String last_modified_date_string = RFC_7231_format.format(last_modified_date);
				String content_type = new MimetypesFileTypeMap().getContentType(requestedfile);
				long content_length = requestedfile.length();
				if (requestedfile.exists()) {
					if (Client_Request.URL.contains("404.gif") == true) {
						// System.out.println("404gif Exist sending failure respose ");
						fis = new FileInputStream(requestedfile);
						int ch = fis.read(bytes, 0, 2048);
						byte[] b = fail_response(date).getBytes();
						output.write(b);
						while (ch != -1) {
							output.write(bytes, 0, ch);
							ch = fis.read(bytes, 0, 2048);
						}
					} else {
						// System.out.println("File Exist Sending success response");
						fis = new FileInputStream(requestedfile);
						int ch = fis.read(bytes, 0, 2048);
						byte[] b = success_reponse(date, last_modified_date_string, content_length, content_type)
								.getBytes();
						// byte[] b = construct_http_header(200, 3).getBytes();
						output.write(b);
						while (ch != -1) {
							output.write(bytes, 0, ch);
							ch = fis.read(bytes, 0, 2048);
						}
					}
				} else {
					// file not found
					// System.out.println("FILE DO NOT Exist preparing 404 HTML : sending failure respose ");
					byte[] b = fail_response(date).getBytes();
					output.write(b);
					fis = new FileInputStream("404_html.html");
					int ch = fis.read(bytes, 0, 2048);
					while (ch != -1) {
						output.write(bytes, 0, ch);
						ch = fis.read(bytes, 0, 2048);
					}
					returncode = 404;
				}
			}
		} catch (Exception e) {
			// thrown if cannot instantiate a File object
			System.err.println("This is the error in server response." + e.toString());
			e.printStackTrace();
		} finally {
			if (fis != null)
				fis.close();
		}
		return returncode;
	}
}
