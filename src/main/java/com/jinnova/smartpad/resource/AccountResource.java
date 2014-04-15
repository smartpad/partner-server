package com.jinnova.smartpad.resource;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.jinnova.smartpad.domain.User;
import com.jinnova.smartpad.partner.IUser;
import com.jinnova.smartpad.partner.PartnerManager;
import com.jinnova.smartpad.util.JsonResponse;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

	@GET
	@Path("test")
	@Consumes(MediaType.APPLICATION_JSON)
    public JsonResponse testGetFeed(/*@QueryParam("userId")String userName, @QueryParam("passwordId")String password,*/
    		User user,
    		@Context HttpServletResponse context) {
		context.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
		context.setHeader("Access-Control-Allow-Credentials", "true");
		
		try {
			IUser userLogged = PartnerManager.instance.login(user.getUserNameText(), user.getPasswordText());
			if (userLogged == null) {
				return new JsonResponse(false, "Incorrect account!");
			}
			return new JsonResponse(true, userLogged); 
		} catch (SQLException e) {
			return new JsonResponse(false, e.getMessage());
		}
	}
	
	@POST
	@Path("acc")
	@Consumes(MediaType.APPLICATION_JSON)
    public JsonResponse getFeed(/*@QueryParam("userId")String userName, @QueryParam("passwordId")String password,*/
    		User user,
    		@Context HttpServletResponse context) {
		context.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
		context.setHeader("Access-Control-Allow-Credentials", "true");
		
		try {
			IUser userLogged = PartnerManager.instance.login(user.getUserNameText(), user.getPasswordText());
			if (userLogged == null) {
				return new JsonResponse(false, "Incorrect account!");
			}
			return new JsonResponse(true, userLogged); 
		} catch (SQLException e) {
			return new JsonResponse(false, e.getMessage());
		}
	}
}