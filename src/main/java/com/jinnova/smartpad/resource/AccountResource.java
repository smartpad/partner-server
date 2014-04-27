package com.jinnova.smartpad.resource;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jinnova.smartpad.domain.User;
import com.jinnova.smartpad.partner.IUser;
import com.jinnova.smartpad.partner.PartnerManager;
import com.jinnova.smartpad.util.JsonResponse;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

	@POST
	@Path("acc")
	@Consumes(MediaType.APPLICATION_JSON)
    public JsonResponse getFeed(User user) {
		try {
			IUser userLogged = PartnerManager.instance.login(user.getUserNameText(), user.getPasswordText());
			if (userLogged == null) {
				return new JsonResponse(false, "Incorrect account!");
			}
			return new JsonResponse(true, new User(userLogged)); 
		} catch (SQLException e) {
			return new JsonResponse(false, e.getMessage());
		}
	}
}