package logic.tasks;

import java.util.Calendar;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.time.DateUtils;

/* @@author A0112184R
 * This class contains details for recurring tasks
 */
public class RecurringTask extends Task {
	
	private static final String DAILY = "every day";
	private static final String WEEKLY = "every week";
	private static final String MONTHLY = "every month";
	private static final String YEARLY = "every year";
	private static final String PERIODLY = "every %s day";
	private static final int EVERY_DAY = 1;
	private static final int EVERY_YEAR = -2;
	private static final int EVERY_MONTH = -1;
	private static final int EVERY_WEEK = 7;
	private static final Comparator<Calendar> DAY_COMP = new Comparator<Calendar>() {
		public int compare(Calendar date1, Calendar date2) {
			return DateUtils.truncatedCompareTo(date1, date2, Calendar.DATE);
		}
	};
	
	private int period;
	private SortedSet<Task> doneInstances;
	private SortedSet<Task> deletedInstances;

	public RecurringTask(String title, Calendar start, Calendar end, int time) {
		super(title, start, end);
		period = time;
		doneInstances = new TreeSet<Task>();
		deletedInstances = new TreeSet<Task>();
	}
	
	// constructors
	public RecurringTask(String title, Calendar start, Calendar end) {
		this(title, start, end, EVERY_DAY);
	}
	
	public RecurringTask(String title, Calendar start, int time) {
		this(title, start, null, time);
	}
	
	public RecurringTask(String title, int time) {
		this(title, Calendar.getInstance(), time);
	}
	
	public RecurringTask(String title) {
		this(title, Calendar.getInstance(), EVERY_DAY);
	}
	// end
	
	// methods for period
	@Override
	public int getPeriod() {
		return period;
	}
	
	public String getPeriodString() {
		if (period == EVERY_YEAR) {
			return YEARLY;
		} else if (period == EVERY_MONTH) {
			return MONTHLY;
		} else if (period == EVERY_WEEK) {
			return WEEKLY;
		} else if (period == EVERY_DAY) {
			return DAILY;
		} else {
			return String.format(PERIODLY, period);
		}
	}
	
	public void setPeriod(int newPeriod) {
		period = newPeriod;
	}
	// end
	
	private int daysUntil(Calendar date) {
		return TaskUtil.daysBetween(getMainDate() , date);
	}
	
	private boolean isSameDayOfMonth(Calendar date) {
		return getMainDate().get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH);
	}
	
	private boolean isSameDayOfYear(Calendar date) {
		return (getMainDate().get(Calendar.MONTH) == date.get(Calendar.MONTH))
				&& (getMainDate().get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH));
	}
	
	/*
	private Calendar getClosestDate(Calendar start, Calendar des, int step) {
		if (start == null) {
			return null;
		}
		Calendar count = Calendar.getInstance();
		count.setTime(start.getTime());
		while (DAY_COMP.compare(count, des) < 0) {
			count.add(Calendar.DATE, step);
		}
		return count;
	} */
	
	private Calendar[] getClosestDates(Calendar[] starts, Calendar des, int step) {
		Calendar start = null;
		Calendar[] results = new Calendar[starts.length];
		for (int i=starts.length-1; i>=0; i--) {
			Calendar date = starts[i];
			results[i] = TaskUtil.cloneDate(date);
			if (date != null) {
				start = results[i];
			}
		}

		if (start == null) {
			return results;
		}
		while ((DAY_COMP.compare(start, des) < 0) || (!willOccur(start))) {
			for (Calendar date: results) {
				TaskUtil.addDate(date, Calendar.DATE, step);
			}
		}
		return results;
	}
	
	/*
	public boolean willOccurWithinNDays(Calendar date, int n) {
		Calendar closest = getClosestDate(getStartDate(), date, period);
		return (TaskUtil.daysBetween(date, closest) <= n);
	} */
	
	@Override
	public boolean willOccur(Calendar date) {
		if (date.before(getStartDate()) || isException(date)) {
			return false;
		}
		if (period == EVERY_YEAR) {
			return isSameDayOfYear(date);
		}
		if (period == EVERY_MONTH) {
			return isSameDayOfMonth(date);
		}
		int dayDistance = daysUntil(date);
		return (dayDistance % period) == 0;
	}
	
	public Task generate(Calendar date) {
		Calendar[] dates = {getStartDate(), getEndDate()};
		Calendar[] results = getClosestDates(dates, date, period);
		Task instance =  TaskUtil.getInstance(getTitle(), results[0], results[1]);
		instance.setParent(this);
		return instance;
	}
	public Task generateNearestInstance() {
		return generate(getMainDate());
	}
	
	// methods for done instances
	public SortedSet<Task> getDoneInstances() {
		return doneInstances;
	}
	
	public boolean addDoneInstance(Task instance) {
		return doneInstances.add(instance);
	}

	public boolean isDoneInstance(Task instance) {
		return doneInstances.contains(instance);
	}
	
	public boolean removeDoneInstance(Task instance) {
		return doneInstances.remove(instance);
	}
	// end
	
	// methods for deleted instances
	public SortedSet<Task> getDeletedInstances() {
		return deletedInstances;
	}
	
	public boolean addDeletedInstance(Task instance) {
		return deletedInstances.add(instance);
	}
	
	public boolean isDeletedInstance(Task instance) {
		return deletedInstances.contains(instance);
	}
	
	public boolean reviveDeletedInstance(Task instance) {
		return deletedInstances.remove(instance);
	}
	// end
	
	public boolean isException(Task instance) {
		return isDoneInstance(instance) || isDeletedInstance(instance);
	}
	
	public boolean isException(Calendar date) {
		boolean result = false;
		for (Task instance: doneInstances) {
			if (DateUtils.isSameDay(instance.getMainDate(), date)) {
				result = true;
				break;
			}
		}
		if (result) {
			return result;
		}
		for (Task instance: deletedInstances) {
			if (DateUtils.isSameDay(instance.getMainDate(), date)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	// equals
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RecurringTask) {
			return equalTitles(getTitle(), ((RecurringTask) obj).getTitle())
					&& equalDates(getStartDate(), ((RecurringTask) obj).getStartDate())
					&& equalDates(getEndDate(), ((RecurringTask) obj).getEndDate())
					&& (getPeriod() == ((RecurringTask) obj).getPeriod());
		}
		return false;
	}
}
