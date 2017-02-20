package uo.sdi.comparators;

import java.util.Comparator;
import java.util.Date;

import uo.sdi.dto.Task;
import uo.sdi.dto.User;

public class UserLoginComparator implements Comparator<User> {

	@Override
	public int compare(User firstUser, User secondUser) {
		
		
		return firstUser.getLogin().compareTo(secondUser.getLogin());
	}

}
