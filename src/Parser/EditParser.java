package Parser;
import java.util.Calendar;
/* @@author A0121535R
 * Parser for editing a task
 */
import java.util.logging.Level;
import logic.Priority;
import logic.commands.Command;
import logic.commands.CommandEdit;
import logic.commands.CommandInvalid;
import logic.tasks.Task;

public class EditParser extends GeneralParser {

	protected Command parse(String inputArgs){
		try{
			Command cmdDetails=null;
			String[] inputTokens = inputArgs.split(Regex.REGEX_SPACE,2);
			int inputNum = getInputNum(inputTokens[0]);

			Task task = getEditTask(inputTokens[1]);
			if(task ==null){
				return new CommandInvalid();
			}
			cmdDetails = new CommandEdit(inputNum,task);
			return cmdDetails;
		} catch(Exception e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "processing error", e);
			return new  CommandInvalid();
		}

	}

	private Task getEditTask(String inputArgs) {
		String titleToken=inputArgs;
		String notTitleToken = inputArgs;
		int[] startEndArray = new int[2];
		String title=null;
		boolean check = checkAbsoluteTitle(inputArgs,startEndArray);
		if(check == true){
			title=inputArgs.substring(startEndArray[0]+1,startEndArray[1]);
			notTitleToken = inputArgs.substring(startEndArray[1]+1);
			System.out.println("this is treueeueueueuue");
		}
		Calendar[] date = getDateArray(notTitleToken);
		Priority tag = getTag(inputArgs);
		int recurring = getRecurring(inputArgs);
		Task task = createTask(title,date,tag,recurring);
		return task;
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