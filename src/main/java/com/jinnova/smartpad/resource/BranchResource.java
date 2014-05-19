package com.jinnova.smartpad.resource;

import java.sql.SQLException;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jinnova.smartpad.UserLoggedInManager;
import com.jinnova.smartpad.domain.Branch;
import com.jinnova.smartpad.domain.Catalog;
import com.jinnova.smartpad.domain.User;
import com.jinnova.smartpad.partner.PartnerManager;
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
			return getBranch(user, true);
		} catch (SQLException e) {
			return new JsonResponse(false, "Cannot load branch info: " + e.getMessage());
		}
	}
	
	private static final JsonResponse getBranch(User user, boolean getSysCat) throws SQLException {
		JsonResponse result = new JsonResponse(true);
		result.put("rootBranch", user.getBranch());
		result.put("allStores", user.getAllStoreList());
		if (getSysCat) {
			result.put("sysCat", new Catalog(null, PartnerManager.instance.getSystemRootCatalog(), user.toUserDB(), user.getToken()));
		}
		return result;
	}
	
	@POST
	@Path("/")
	public JsonResponse saveBranch(final Branch updateBranch) {
		// TODO
		if (updateBranch == null) {
			// TODO validate
		}
		// TODO validate security or logic... 
		User user = UserLoggedInManager.instance.getUser(updateBranch.getUserName());
		if (user == null) {
			return new JsonResponse(false, "User not logged in!");
		}
		// Update branch
		try {
			Branch result = user.updateBranch(updateBranch);
			if (result == null) {
				return new JsonResponse(false, "Cannot find or update branch: " + updateBranch.getName());
			}
			return getBranch(user, false);
		} catch (SQLException e) {
			return new JsonResponse(false, "Cannot save branch info: " + e.getMessage());
		}
	}

	@DELETE
	@Path("/{user}/{branchId}")
	public JsonResponse deleteBranch(@PathParam("user") String userName, @PathParam("branchId") String branchId) {
		User user = UserLoggedInManager.instance.getUser(userName);
		if (user == null) {
			return new JsonResponse(false, "User not logged in!");
		}
		try {
			String deleteResult = user.deleteBranch(branchId);
			if (deleteResult != null) {
				return new JsonResponse(false, "Cannot delete branch info: " + deleteResult);
			}
			return new JsonResponse(true, user.getBranch());
		} catch (SQLException e) {
			return new JsonResponse(false, "Cannot delete branch info: " + e.getMessage());
		}
	}
}
