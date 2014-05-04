package com.jinnova.smartpad.resource;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jinnova.smartpad.UserLoggedInManager;
import com.jinnova.smartpad.domain.User;
import com.jinnova.smartpad.util.JsonResponse;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

	@POST
	@Path("acc")
	@Consumes(MediaType.APPLICATION_JSON)
    public JsonResponse getAcc(User user) {
		try {
			User userLogged = UserLoggedInManager.instance.login(user.getUserNameText(), user.getPasswordText());
			if (userLogged == null) {
				return new JsonResponse(false, "Incorrect account!");
			}
			return new JsonResponse(true, userLogged); 
		} catch (SQLException e) {
			return new JsonResponse(false, e.getMessage());
		}
	}
}