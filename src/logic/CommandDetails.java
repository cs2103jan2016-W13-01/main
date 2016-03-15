package logic;

public class CommandDetails {
	private CommandType cmd;
	private Task task;
	private String inputNum;

	
	public CommandDetails(CommandType cmd,Task task){
		this.cmd=cmd;
		this.task=task;
	}
	
	public CommandType getCommand() {
		return cmd;
	}
	
	public Task getTask() {
		return task;
	}
	
	public void setInputNum(String inputNum){
		this.inputNum=inputNum;
	}
	public String getInputNum(){
		return inputNum;
	}
	
}
