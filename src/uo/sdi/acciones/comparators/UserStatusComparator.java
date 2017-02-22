package uo.sdi.acciones.comparators;

import java.util.Comparator;

import uo.sdi.dto.User;

public class UserStatusComparator implements Comparator<User> {

	@Override
	public int compare(User firstUser, User secondUser) {
		
		
		return firstUser.getStatus().compareTo(secondUser.getStatus());
	}

}
