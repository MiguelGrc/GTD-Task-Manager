package uo.sdi.acciones.comparators;

import java.util.Comparator;

import uo.sdi.dto.User;

public class UserEmailComparator implements Comparator<User> {

	@Override
	public int compare(User firstUser, User secondUser) {
		
		
		return firstUser.getEmail().compareTo(secondUser.getEmail());
	}

}
