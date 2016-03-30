/* @@author A0112184R
 * Logger for Logic
 */
package logic;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogicLogger {
	
	public static Logger logicLogger;
	
	public static void initialize() {
		logicLogger = Logger.getLogger("LogicLogger");
	}
	
	public static void log(Level level, String message) {
		logicLogger.log(level, message);
	}
}
