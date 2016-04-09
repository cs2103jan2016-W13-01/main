package Parser;
//@@author A0121535R
//Parser that calls the required methods for adding a task
import java.util.Calendar;
import java.util.logging.Level;
import logic.Priority;
import logic.commands.Command;
import logic.commands.CommandAdd;
import logic.commands.CommandInvalid;
import logic.tasks.Task;

public class AddParser extends GeneralParser {

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
			Calendar[] date = getDateArray(notTitleToken);
			Priority tag = getTag(inputArgs);
			int recurring = getRecurring(notTitleToken);
			System.out.println("add reccur "+recurring);
			Task task = createTask(title,date,tag,recurring);
			Command cmdDetails = new CommandAdd(task);
			return cmdDetails;

		} catch(NullPointerException e){
			e.printStackTrace();
			CommandParser.parserLogger.log(Level.WARNING, "add processing error", e);
			return new CommandInvalid();
		}
	}
	
	//check if there is "<title>"
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
