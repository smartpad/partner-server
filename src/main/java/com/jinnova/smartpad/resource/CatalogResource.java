package com.jinnova.smartpad.resource;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.jinnova.smartpad.UserLoggedInManager;
import com.jinnova.smartpad.domain.User;
import com.jinnova.smartpad.util.JsonResponse;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class CatalogResource {

	@GET
	@Path("catalog")//{catalogId}
    public JsonResponse getCatalog(/*@PathParam("catalogId") String catalogId,*/ @QueryParam("userName") String userName) {
		User user = UserLoggedInManager.instance.getUser(userName);
		if (user == null) {
			return new JsonResponse(false, "User not logged in!");
		}
		try {
			user.loadCatalog();
		} catch (SQLException e) {
			return new JsonResponse(false, "Cannot load catalog info: " + e.getMessage());
		}
		return new JsonResponse(true, user.getCatalog());
	}

}