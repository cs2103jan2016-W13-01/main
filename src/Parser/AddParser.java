package Parser;
import java.util.Calendar;
/* @@author A0121535R
 * Parser for adding a task
 */
import java.util.logging.Level;

import logic.Priority;
import logic.commands.Command;
import logic.commands.CommandAdd;
import logic.commands.CommandInvalid;
import logic.tasks.Task;

public class AddParser extends GeneralParser {

	public Command parse(String inputArgs){
		try{
			String titleToken=inputArgs;
			String notTitleToken = inputArgs;
			int[] startEndArray = new int[2];
			boolean check = checkAbsoluteTitle(inputArgs,startEndArray);
			if(check == true){
				titleToken=inputArgs.substring(startEndArray[0]+1,startEndArray[1]);
				notTitleToken = inputArgs.substring(startEndArray[1]+1);
			}
			String title = getTitle(titleToken);
			Calendar[] date = getDateArray(notTitleToken);
			Priority tag = getTag(inputArgs);
			int recurring = getRecurring(notTitleToken);
			System.out.println("add reccur "+recurring);
			Task task = createTask(title,date,tag,recurring);
			Command cmdDetails = new CommandAdd(task);
			return cmdDetails;

		}
		catch(Exception e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "processing error", e);
			return new CommandInvalid();
		}
	}
	private static boolean checkAbsoluteTitle(String inputArgs, int[] array) {
		int absIndexStart = inputArgs.indexOf("\""); 
		if(absIndexStart>=0){
			int absIndexEnd = inputArgs.indexOf("\"",absIndexStart+1);
			if(absIndexEnd >absIndexStart && absIndexEnd>=0){
				array[0]=absIndexStart;
				array[1]=absIndexEnd;
				return true;
			}
		}
		return false;
	}

}
