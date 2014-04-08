package com.jinnova.smartpad.resource;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.jinnova.smartpad.partner.IUser;
import com.jinnova.smartpad.partner.PartnerManager;
import com.jinnova.smartpad.util.JsonResponse;

@Path("/acc")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

	@GET
    public JsonResponse getFeed(@QueryParam("userId")String userName, @QueryParam("passwordId")String password, 
    		@Context HttpServletResponse context) {
		context.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
		try {
			IUser userLogged = PartnerManager.instance.login(userName, password);
			if (userLogged == null) {
				return new JsonResponse(false, "Incorrect account!");
			}
			return new JsonResponse(true, userLogged.getLogin()); 
		} catch (SQLException e) {
			return new JsonResponse(false, e.getMessage());
		}
	}
}