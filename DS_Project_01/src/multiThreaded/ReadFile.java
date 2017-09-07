package multiThreaded;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.activation.MimetypesFileTypeMap;

public class ReadFile {

	public static void main(String[] args) throws IOException {

		System.out.println(
				java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT"))));
		try (BufferedReader br = new BufferedReader(new FileReader("abc.html"))) {

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		File f = new File("abc.html");
		System.out.println("Mime Type of " + f.getName() + " is " + new MimetypesFileTypeMap().getContentType(f));
		// expected output :
		// "Mime Type of gumby.gif is image/gif"
	}

}
