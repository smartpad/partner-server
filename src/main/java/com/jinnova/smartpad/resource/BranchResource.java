package com.jinnova.smartpad.resource;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jinnova.smartpad.UserLoggedInManager;
import com.jinnova.smartpad.domain.User;
import com.jinnova.smartpad.util.JsonResponse;

@Path("/branch")
@Produces(MediaType.APPLICATION_JSON)
public class BranchResource {

	@GET
	@Path("/{user}")
    public JsonResponse getBranch(@PathParam("user") String userName) {
		User user = UserLoggedInManager.instance.getUser(userName);
		if (user == null) {
			return new JsonResponse(false, "User not logged in!");
		}
		try {
			JsonResponse result = new JsonResponse(true);
			result.put("rootBranch", user.getBranch());
			result.put("allStores", user.getAllStoreList());
			return result;
		} catch (SQLException e) {
			return new JsonResponse(false, "Cannot load branch info: " + e.getMessage());
		}
	}
}
