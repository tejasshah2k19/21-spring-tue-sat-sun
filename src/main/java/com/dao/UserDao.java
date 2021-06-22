package com.dao;

import java.util.ArrayList;

import com.bean.UserBean;

public class UserDao {

	public static ArrayList<UserBean> users = new ArrayList<>();
	static {
		UserBean u1 = new UserBean();
		u1.setFirstName("roy");
		u1.setEmail("roy@edu.com");
		u1.setPassword("royal");
		users.add(u1);

		UserBean u2 = new UserBean();
		u2.setFirstName("hanuman");
		u2.setEmail("hanuman@ji.com");
		u2.setPassword("ram");
		users.add(u2);

	
	
	}
}
