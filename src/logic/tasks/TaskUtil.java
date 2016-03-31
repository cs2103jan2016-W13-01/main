/* @@author A0112184R */
package logic.tasks;

import java.util.Comparator;
import java.util.Date;

/* @@author A0112184R
 * This class contains the getInstance() method to generate a Task object
 * and the comparator for tasks
 */
public class TaskUtil {
	
	public static class TaskComparator implements Comparator<Task> {
		public int compare(Task task1, Task task2) {
			Date date1 = task1.getMainDate();
			Date date2 = task2.getMainDate();
			if (date1 == null && date2 == null) {
				return task1.getTitle().compareToIgnoreCase(task2.getTitle());
			} else if (date1 == null) {
				return 1;
			} else if (date2 == null) {
				return -1;
			} else {
				return date1.compareTo(date2);
			}
		}
	}
	
	public Task getInstance(String title, Date startDate, Date endDate, int recurringPeriod) {
		if (startDate == null && endDate == null) {
			return new Task(title);
		} else if (recurringPeriod > 0) {
			return new RecurringTask(title, startDate, endDate, recurringPeriod);
		} else if (endDate == null){
			return new Deadline(title, startDate);
		} else {
			return new Session(title, startDate, endDate);
		}
	}
	
	public Task getInstance(String title, Date startDate, Date endDate) {
		return getInstance(title, startDate, endDate, 0);
	}
	
	public Task getInstance(String title, Date startDate, int recurringPeriod) {
		return getInstance(title, startDate, null, recurringPeriod);
	}
	
	public Task getInstance(String title, Date startDate) {
		return getInstance(title, startDate, null, 0);
	}
}
