package logic.commands;

public class CommandDeleteRecurring implements Command {
	private int taskNumberToDelete;
	public CommandDeleteRecurring(int num){
		taskNumberToDelete = num;
	}
	
	@Override
	public CommandType getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String undo() {
		// TODO Auto-generated method stub
		return null;
	}

}
