package storage;

/* @@author A0112184R
 * Logger for Logic
 */
import java.util.logging.Level;
import java.util.logging.Logger;

public class StorageLogger {
	
	public static Logger storageLogger;
	
	public static void initialize() {
		storageLogger = Logger.getLogger("StorageLogger");
	}
	
	public static void log(Level level, String message) {
		storageLogger.log(level, message);
	}
}
