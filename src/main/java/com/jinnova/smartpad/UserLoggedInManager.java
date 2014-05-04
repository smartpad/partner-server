package com.jinnova.smartpad;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.jinnova.smartpad.domain.User;
import com.jinnova.smartpad.partner.IUser;
import com.jinnova.smartpad.partner.PartnerManager;

public class UserLoggedInManager {
	
	public static UserLoggedInManager instance;
	
	private final Map<String, User> allLoggedInUser;
	
	private UserLoggedInManager() {
		this.allLoggedInUser = new HashMap<>();
	}
	
	public static final void initialize() {
		instance = new UserLoggedInManager();
	}

	public User login(String login, String password) throws SQLException {
		IUser user = PartnerManager.instance.login(login, password);
		if (user == null) {
			allLoggedInUser.remove(login);
			return null;
		}
		User result = new User(user);
		allLoggedInUser.put(login, result);
		return result;
	}
	
	public void logout(String login) {
		// TODO handle user logged out
	}

	public User getUser(String userName) {
		return allLoggedInUser.get(userName);
	}
}
