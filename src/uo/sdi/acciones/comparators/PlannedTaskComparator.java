package uo.sdi.acciones.comparators;

import java.util.Comparator;
import java.util.Date;

import uo.sdi.dto.Task;

public class PlannedTaskComparator implements Comparator<Task> {

	@Override
	public int compare(Task firstTask, Task secondTask) {
		
		Date firstTaskDate = firstTask.getPlanned();
		Date secondTaskDate = secondTask.getPlanned();
		
		if(firstTaskDate==null)
			return 1;
		if(secondTaskDate==null){
			return -1;
		}
		
		if(firstTaskDate.before(secondTaskDate))
			return -1;
		else if(firstTaskDate.after(secondTaskDate))
			return 1;
		else
			return 0;
	}

}
