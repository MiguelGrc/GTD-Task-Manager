package uo.sdi.comparators;

import java.util.Comparator;
import java.util.Date;

import uo.sdi.dto.Task;

public class TodayTaskComparator implements Comparator<Task> {

	@Override
	public int compare(Task firstTask, Task secondTask) {
		
		Long firstCateg = firstTask.getCategoryId();
		Long secondCateg = secondTask.getCategoryId();
		
		if(firstCateg==null){
			return 1;
		}
		
		if(secondCateg==null){
			return -1;
		}
		
		if(firstCateg<secondCateg)
			return 1;
		else if(firstCateg>secondCateg)
			return -1;
		else{
			Date firstTaskDate = firstTask.getPlanned();
			Date secondTaskDate = secondTask.getPlanned();
		
			if(firstTaskDate.before(secondTaskDate))
				return -1;
			else if(firstTaskDate.after(secondTaskDate))
				return 1;
			else
				return 0;
		}
	}

}
