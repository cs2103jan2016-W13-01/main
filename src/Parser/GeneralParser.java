package Parser;
import java.util.Calendar;
import java.util.regex.Matcher;
/* @@author A0121535R
 * parser class with the basic methods
 */
import java.util.regex.Pattern;

import logic.Priority;
import logic.commands.Command;
import logic.tasks.Task;
import logic.tasks.TaskUtil;

public abstract class GeneralParser {

	private static final String REGEX_SPACE = "\\s";
	private static final String PERIOD_DAY_REGEX = "("+" every " +"\\d+"+" day(?:s) "+")";
	private static final String PERIOD_WEEK_REGEX = "("+" every " +"\\d+"+" week(?:s) "+")";
	private static final String PERIOD_MONTH_REGEX = "("+" every " +"\\d+"+" month(?:s) "+")";
	private static final String PERIOD_YEAR_REGEX = "("+" every " +"\\d+"+" year(?:s) "+")";
	private static final String NUM_REGEX = "(\\d+)";
	protected abstract Command parse(String inputArgs);


	protected String getTitle(String input){
		String title = TitleParser.getTitle(input);
		return title;
	}

	protected Calendar[] getDateArray(String input){
		Calendar[] dates = DateParser.getDates(input);
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

	protected Task createTask(String title, Calendar[] dates, Priority tag, int recurringPeriod) {
		if (dates.length == 0) {
			return TaskUtil.getInstance(title, null);
		} else if (dates.length == 1) {
			return TaskUtil.getInstance(title, dates[0], recurringPeriod);
		} else {
			return TaskUtil.getInstance(title, dates[0], dates[1], recurringPeriod);
		}
	}

	/*
	protected Task createTask(String title, Date[] dates, Priority tag, int isRecurring){
		Task task=null;
		System.out.println(dates.length);
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
	 */
	protected static Priority getTag(String inputArgs) {

		return Priority.NULL;
	}
	protected static int getRecurring(String inputArgs){
		inputArgs = inputArgs.toLowerCase();

		int num = getCustomPeriod(inputArgs);

		if(num!=0){
			return num;
		}

		if(inputArgs.contains("every day")){
			return 1;
		}
		else if(inputArgs.contains("every week")){
			return 7;
		}
		else if(inputArgs.contains("every month")){
			return -1;
		}
		else if(inputArgs.contains("every year")){
			return -2;
		}
		else{
			return 0;
		}

	}


	private static int getCustomPeriod(String inputArgs) {

		int num = 0;

		num = getCustomDays(inputArgs);
		if(num!=0){
			return num;
		}
		num = getCustomWeeks(inputArgs);
		if(num!=0){
			return num;
		}
		num = getCustomMonths(inputArgs);
		if(num!=0){
			return num;
		}
		num = getCustomYears(inputArgs);
		if(num!=0){
			return num;
		}
		return num;
	}


	private static int getCustomYears(String inputArgs) {
		int num=0;  
		Pattern pattern = Pattern.compile(PERIOD_YEAR_REGEX);
		Matcher matcher = pattern.matcher(inputArgs);
		if (matcher.find( )) {
			System.out.println("Found value: " + matcher.group() );
			num = getPeriod(matcher.group());
			num=-2*num;
		}
		
		return num;
	}


	private static int getCustomMonths(String inputArgs) {
		int num=0;  
		Pattern pattern = Pattern.compile(PERIOD_MONTH_REGEX);
		Matcher matcher = pattern.matcher(inputArgs);
		if (matcher.find( )) {
			System.out.println("Found value: " + matcher.group() );
			num = getPeriod(matcher.group());
			num=(1-2*num);
		}
		return num;
	}


	private static int getCustomWeeks(String inputArgs) {
		int num=0;  
		Pattern pattern = Pattern.compile(PERIOD_WEEK_REGEX);
		Matcher matcher = pattern.matcher(inputArgs);
		if (matcher.find( )) {
			System.out.println("Found value: " + matcher.group() );
			num = getPeriod(matcher.group());
			num=num*7;
		}
		return num;
	}


	private static int getCustomDays(String inputArgs) {
		int num=0;  
		Pattern pattern = Pattern.compile(PERIOD_DAY_REGEX);
		Matcher matcher = pattern.matcher(inputArgs);
		if (matcher.find( )) {
			System.out.println("Found value: " + matcher.group() );
			num = getPeriod(matcher.group());
		}
		return num;
	}


	private static int getPeriod(String inputArgs) {
		Pattern pattern = Pattern.compile(NUM_REGEX);
		Matcher matcher = pattern.matcher(inputArgs);
		if (matcher.find( )) {
			System.out.println("Found value: " + matcher.group() );
		}
		int num = Integer.parseInt(matcher.group());
		return num;
	}

}
