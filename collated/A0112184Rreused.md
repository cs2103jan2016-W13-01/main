# A0112184Rreused
###### src\logic\tasks\TaskUtil.java
``` java
	 * Source: http://stackoverflow.com/questions/20165564/calculating-days-between-two-dates-with-in-java
	 */
	public static int daysBetween(Calendar day1, Calendar day2){
	    Calendar dayOne = (Calendar) day1.clone(),
	            dayTwo = (Calendar) day2.clone();

	    if (dayOne.get(Calendar.YEAR) == dayTwo.get(Calendar.YEAR)) {
	        return dayOne.get(Calendar.DAY_OF_YEAR) - dayTwo.get(Calendar.DAY_OF_YEAR);
	    } else {
	        if (dayTwo.get(Calendar.YEAR) > dayOne.get(Calendar.YEAR)) {
	            return -1;
	        }
	        int extraDays = 0;

	        int dayOneOriginalYearDays = dayOne.get(Calendar.DAY_OF_YEAR);

	        while (dayOne.get(Calendar.YEAR) > dayTwo.get(Calendar.YEAR)) {
	            dayOne.add(Calendar.YEAR, -1);
	            // getActualMaximum() important for leap years
	            extraDays += dayOne.getActualMaximum(Calendar.DAY_OF_YEAR);
	        }

	        return extraDays - dayTwo.get(Calendar.DAY_OF_YEAR) + dayOneOriginalYearDays ;
	    }
	}
	
	// @author A0112184R
	
	public static boolean isBeforeDay(Calendar date1, Calendar date2) {
		return (DateUtils.truncatedCompareTo(date1, date2, Calendar.DATE) < 0);
	}
	
	public static boolean isAfterDay(Calendar date1, Calendar date2) {
		return (DateUtils.truncatedCompareTo(date1, date2, Calendar.DATE) > 0);
	}

	public static boolean willOccur(Task task, Calendar date) {
		if (task.getStartDate() == null) {
			return false;
		}
		if (task instanceof RecurringTask) {
			return ((RecurringTask) task).willOccur(date);
		}
		return DateUtils.isSameDay(task.getStartDate(), date);
	}
	
	public static boolean isWithinNDays(Task task, Calendar date, int n) {
		if (task.getStartDate() == null) {
			return false;
		}
		int distance = daysBetween(task.getStartDate(), date);
		return ((distance >= 0) && (distance <= n));
	}
	
	public static boolean isWithinPeriod(Task task, Calendar start, Calendar end) {
		Calendar mainDate = task.getStartDate();
		if ((mainDate == null) || (end.before(start))) {
			return false;
		}
		return (isAfterDay(mainDate, start) && isBeforeDay(mainDate, end));
	}

	public static Calendar cloneDate(Calendar date) {
		if (date == null) {
			return null;
		} else {
			Calendar result = Calendar.getInstance();
			result.setTime(date.getTime());
			return result;
		}
	}
	
	public static void addDate(Calendar date, int field, int step) {
		if (date != null) {
			date.add(field, step);
		}
	}
}
```
