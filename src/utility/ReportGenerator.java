package utility;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

public class ReportGenerator {
	private Logger logger = Logger.getLogger("flightReservation");
	public void generateReport(String content){
		try {
			FileWriter writer = new FileWriter(Constant.textReportPath,true);
			PrintWriter write = new PrintWriter(writer);
			write.println(content);
			write.close();
		} catch (IOException e) {
			logger.error("Exception occurred while writing in to the text file : "+e.getMessage());
		}
		
	}

}
