package parser;
//@@author A0121535R
//Parser that calls the required methods for adding a task
import java.util.Calendar;
import java.util.logging.Level;
import logic.commands.Command;
import logic.commands.CommandAdd;
import logic.commands.CommandInvalid;
import logic.tasks.Task;

public class AddParser extends GeneralParser {
	
	/* extracts the various parts of a Command from the given string
	 * and returns a Command object
	 * 
	 * can either add <string>
	 * or add "<title>" <rest of strings>
	 */
	public Command parse(String inputArgs){
		try{
			String notTitleToken = inputArgs;
			int[] startEndArray = new int[2];
			String title;
			boolean check = checkAbsoluteTitle(inputArgs,startEndArray);
			if(check == true){
				title=inputArgs.substring(startEndArray[0]+1,startEndArray[1]);
				notTitleToken = inputArgs.substring(startEndArray[1]+1);
			} else{
				title = getTitle(inputArgs);
			}
			Command cmdDetails = consolidateAddFields(inputArgs, notTitleToken, title);
			return cmdDetails;

		} catch(NullPointerException e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "add processing error", e);
			return new CommandInvalid();
		}
	}
	
	//creates an Command object from the given fields
	private Command consolidateAddFields(String inputArgs, String notTitleToken, String title) {
		Calendar[] date = getDateArray(notTitleToken);
		int recurring = getRecurring(notTitleToken);
		Task task = createTask(title,date,recurring);
		Command cmdDetails = new CommandAdd(task);
		return cmdDetails;
	}
	
	//check if there is "<title>" as well as obtain the index array if true
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
