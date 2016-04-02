package Parser;
/* @@author A0121535R
* parser class with the basic methods
*/
import java.util.Date;

import logic.Priority;
import logic.commands.Command;
import logic.tasks.Deadline;
import logic.tasks.RecurringTask;
import logic.tasks.Session;
import logic.tasks.Task;
import logic.tasks.TaskUtil;

public abstract class GeneralParser {
	
	private static final String REGEX_SPACE = "\\s";

	protected abstract Command parse(String inputArgs);
	
	
	protected String getTitle(String input){
		String title = newTitleParser.getTitle(input);
		return title;
	}
	
	protected Date[] getDateArray(String input){
		Date[] dates = DateParser.getDates(input);
		return dates;
	}

	protected int getInputNum(String inputArgs) {
		int num = InputNumParser.getInputNum(inputArgs);
		return num;
	}
	
	protected boolean checkInputArgs(String inputArgs, int size){
		String[] inputToken = inputArgs.split(REGEX_SPACE);
		if(inputToken.length<size){
			return false;
		}
		return true;
	}
	
	
	
	protected Task createTask(String title, Date[] dates, Priority tag, int isRecurring){
		Task task=null;
		if(isRecurring==1){
			task = createRecurringTask(title,dates,tag,isRecurring);
		}
		else{
			task = createNonRecurringTask(title,dates,tag);
		}
		return task;
	}


	private Task createNonRecurringTask(String title, Date[] dates, Priority tag) {
		Task task = null;
		if(dates.length==1||dates.length==0){
			task = createDeadlineTask(title,dates,tag);
		}
		else if(dates.length==2){
			task = createSessionTask(title,dates,tag);
		}
		return task;
	}


	private Task createRecurringTask(String title, Date[] dates, Priority tag, int isRecurring) {
		RecurringTask task = null;
		if(dates.length==1||dates.length==0){
			task = (RecurringTask) TaskUtil.getInstance(title,dates[0],isRecurring);
			
		}
		else if(dates.length==2){
			task =(RecurringTask) TaskUtil.getInstance(title,dates[0],dates[1],isRecurring);
		}
		return task;
	}


	private Task createDeadlineTask(String title, Date[] dates, Priority tag) {
		Deadline task;
		if(dates.length==1){
		task = (Deadline) TaskUtil.getInstance(title, dates[0]);
		}
		else{
		task = (Deadline) TaskUtil.getInstance(title, null);	
		}
		task.setPriority(tag);
		return task;
	}


	private Task createSessionTask(String title, Date[] dates, Priority tag) {
		Session task = (Session) TaskUtil.getInstance(title,dates[0],dates[1]);
		
		return task;
	}
	
	protected Priority getTag(String inputArgs) {
		
		return Priority.NULL;
	}
	protected int getRecurring(String inputArgs){
		return 0;
		
	}
	
	/*
	private static String getDescription(String[] inputTokens) {
		String description = "";
		for (int i = 0; i < inputTokens.length; i++) {
			if (inputTokens[i].charAt(0) == '/') {
				StringBuilder sb = new StringBuilder(inputTokens[i]);
				sb = sb.deleteCharAt(0);
				description = sb.toString();
				break;
			}
		}
		return description;
	}
*/
}
