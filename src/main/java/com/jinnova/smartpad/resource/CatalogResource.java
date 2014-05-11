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
import com.jinnova.smartpad.domain.Catalog;
import com.jinnova.smartpad.domain.User;
import com.jinnova.smartpad.partner.PartnerManager;
import com.jinnova.smartpad.util.JsonResponse;

@Path("/catalog")
@Produces(MediaType.APPLICATION_JSON)
public class CatalogResource {

	@GET
	@Path("/{user}")//{catalogId}
    public JsonResponse getCatalog(/*@PathParam("catalogId") String catalogId, @QueryParam*/ @PathParam("user") String userName) {
		User user = UserLoggedInManager.instance.getUser(userName);
		if (user == null) {
			return new JsonResponse(false, "User not logged in!");
		}
		try {
			return new JsonResponse(true, user.getCatalog());
		} catch (SQLException e) {
			return new JsonResponse(false, "Cannot load catalog info: " + e.getMessage());
		}
	}

	@POST
	@Path("/")
	public JsonResponse saveCatalog(Catalog updateCatalog) {
		// TODO
		if (updateCatalog == null) {
			// TODO validate
		}
		// TODO validate security or logic... 
		User user = UserLoggedInManager.instance.getUser(updateCatalog.getToken().getUserName());
		if (user == null) {
			return new JsonResponse(false, "User not logged in!");
		}
		/*if (updateCatalog.isNew()) {
			// TODO save new cat
		}*/
		// Update catalog
		try {
			updateCatalog = user.updateCatalog(updateCatalog);
		} catch (SQLException e) {
			return new JsonResponse(false, "Cannot save catalog info: " + e.getMessage());
		}
		return new JsonResponse(true, updateCatalog);
	}
	
	@DELETE
	@Path("/")
	public JsonResponse deleteCatalog(Catalog catalog) {
		// TODO
		return null;
	}
}