package logic;

public class CommandDetails {
	private CommandType cmd;
	private Task task;
<<<<<<< HEAD
	private int taskNumber;
=======
	private String inputNum;
>>>>>>> 74db55326c83cd26e3eaff546aa36fde5e88997a

	public CommandDetails(CommandType cmd, Task task, int taskNumber) {
		
		this.cmd = cmd;
		this.task = task;
		this.taskNumber = taskNumber;
	}
	
<<<<<<< HEAD
	public CommandDetails(CommandType cmd, Task task){
		
=======
	public CommandDetails(CommandType cmd,Task task){
>>>>>>> 74db55326c83cd26e3eaff546aa36fde5e88997a
		this.cmd=cmd;
		this.task=task;
		taskNumber = 0;
	}
	
	public CommandDetails(CommandType cmd, int taskNumber) {
		
		this.cmd = cmd;
		this.task = null;
		this.taskNumber = taskNumber;
	}
	
	public CommandType getCommand() {
		return cmd;
	}
	
	public Task getTask() {
		return task;
	}
	
<<<<<<< HEAD
	public int getTaskNumber() {
		return taskNumber;
	}
	
=======
	public void setInputNum(String inputNum){
		this.inputNum=inputNum;
	}
	public String getInputNum(){
		return inputNum;
	}
>>>>>>> 74db55326c83cd26e3eaff546aa36fde5e88997a
	
}
