/* @@author A0112184R-unused
 * This class stores the responses for the UI to display
 * Reason: we omitted the parallel execution scheme
 */
package logic;

import java.util.LinkedList;
import java.util.Queue;

public class ResponseQueue {
	
	private static Queue<Response> responseQueue;
	
	public static Response getResponse() {
		return responseQueue.poll();
	}
	
	public static boolean isEmpty() {
		return responseQueue.isEmpty();
	}
	
	public static boolean addResponse(Response response) {
		return responseQueue.offer(response);
	}
	
	public static void initialize() {
		responseQueue = new LinkedList<Response>();
	}
}
