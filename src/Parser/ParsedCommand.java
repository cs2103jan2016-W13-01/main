package Parser;

import java.util.Date;

public class ParsedCommand {
	private String title;
	private String taskType;
	private Date date;
	private String description;
	
	public ParsedCommand(){
	
		this.title=null;
		this.taskType=null;
		this.date=null;
		this.description=null;
	}
	
	public ParsedCommand(String title,String taskType,Date date,String description){
		this.title=title;
		this.taskType=taskType;
		this.date=date;
		this.description=description;
	}

}
