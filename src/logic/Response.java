package logic;

import java.util.ArrayList;

/**
 * @author Bao Linh
 * This class contains all the information the Logic send to the UI for displaying
 */
public class Response {
	
	private String message;
	private ArrayList<String> taskList;
        
        public Response() {
	
	}
	
	
	public Response(String msg, ArrayList<String> list) {
		message = msg;
		taskList = list;
	}
	
	public Response(String msg) {
		message = msg;
	}
	
	public Response(ArrayList<String> list) {
		taskList = list;
	}
	
	public String getMessage() {
		return message;
	}
	
	public ArrayList<String> getTaskList() {
		return taskList;
	}
	
	public boolean equals(Response res) {
		return (getMessage().equals(res.getMessage()) && getTaskList().equals(res.getTaskList()));
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getMessage());
		if (taskList == null) {
			sb.append("\r\n");
			sb.append("null");
		} else {
			for (String str: taskList) {
				sb.append("\r\n");
				sb.append(str);
			}
		}
		return sb.toString();
	}
}
