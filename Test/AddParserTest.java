import static org.junit.Assert.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import org.junit.Test;

import Parser.AddParser;
import Parser.CommandParser;
import Parser.DateParser;

import logic.Task;
import logic.commands.Command;
import logic.commands.CommandType;

public class AddParserTest {

	@Test
	public void test() throws ParseException, SecurityException, IOException {
		AddParser ap = new AddParser();
		CommandParser cp = new CommandParser();
		CommandParser.parserLogger.log(Level.INFO, "End of logging");

		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		sdf.setLenient(false);
		DateParser dp = new DateParser();
		Date date = dp.getDate("16 march 2016");
		Task task = new Task("meeting by the seaside at johnsons place", date);
		Command cmdDetails = new CommandAdd(task);
		Command cmdDetails2 =ap.parse("meeting by the seaside at johnsons place 16 march 2016");
		System.out.println(date);
		
		System.out.println(cmdDetails.getTask()+ " vs "+cmdDetails2.getCommand());
		assertEquals(cmdDetails.getCommand(),cmdDetails2.getCommand());
		
		System.out.println(cmdDetails.getTask().getTitle()+ " vs "+cmdDetails2.getTask().getTitle());
		assertEquals(cmdDetails.getTask().getTitle(),cmdDetails2.getTask().getTitle());
		
		System.out.println(cmdDetails.getTask().getDate()+ " vs "+cmdDetails2.getTask().getDate());	
	}

}
