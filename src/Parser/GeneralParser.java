package parser;
//@@author A0121535R
//parser class with the general methods

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import logic.commands.Command;
import logic.tasks.Task;
import logic.tasks.TaskUtil;

public abstract class GeneralParser {


	protected abstract Command parse(String inputArgs);

	//Obtain title from input
	protected String getTitle(String input){
		String title = TitleParser.getParsedTitle(input);
		return title;
	}

	//Obtain dates from input
	protected Calendar[] getDateArray(String input){
		Calendar[] dates = DateParser.getDates(input);
		return dates;
	}

	//Obtain number from input
	protected int getInputNum(String inputArgs) {
		int num = InputNumParser.getInputNum(inputArgs);
		return num;
	}

	protected boolean checkInputArgs(String inputArgs, int size){
		String[] inputToken = inputArgs.split(Regex.REGEX_SPACE);
		if(inputToken.length<size){
			return false;
		}
		return true;
	}

	//Return a task object from the given fields
	protected Task createTask(String title, Calendar[] dates, int recurringPeriod) {
		System.out.println("Recurring! "+recurringPeriod);
		if (dates.length == 0) {
			if(title==null){
				System.out.println("Recurring only ! "+recurringPeriod);
			}
			return TaskUtil.getInstance(title, recurringPeriod);
		} else if (dates.length == 1) {
			return TaskUtil.getInstance(title, dates[0],recurringPeriod);
		} else {
			return TaskUtil.getInstance(title, dates[0], dates[1], recurringPeriod);
		}
	}

	//Obtain the int values that represents the recurring dates
	protected static int getRecurring(String inputArgs){
		inputArgs = inputArgs.toLowerCase();

		if(inputArgs.contains("every day")){
			return 1;
		} else if(inputArgs.contains("every week")){
			return 7;
		} else if(inputArgs.contains("every month")){
			return -1;
		} else if(inputArgs.contains("every year")){
			return -2;
		} else{
			int num = getCustomPeriod(inputArgs);
			return num;
		}

	}

	//Obtain user input number recurring periods
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
		Pattern pattern = Pattern.compile(Regex.PERIOD_YEAR_REGEX_MUL);
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
		Pattern pattern = Pattern.compile(Regex.PERIOD_MONTH_REGEX_MUL);
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
		Pattern pattern = Pattern.compile(Regex.PERIOD_WEEK_REGEX_MUL);
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
		Pattern pattern = Pattern.compile(Regex.PERIOD_DAY_REGEX_MUL);
		Matcher matcher = pattern.matcher(inputArgs);
		if (matcher.find( )) {
			System.out.println("Found value: " + matcher.group() );
			num = getPeriod(matcher.group());
		}
		return num;
	}


	private static int getPeriod(String inputArgs) {
		Pattern pattern = Pattern.compile(Regex.NUM_REGEX);
		Matcher matcher = pattern.matcher(inputArgs);
		if (matcher.find( )) {
			System.out.println("Found value: " + matcher.group() );
		}
		int num = Integer.parseInt(matcher.group());
		return num;
	}

}
