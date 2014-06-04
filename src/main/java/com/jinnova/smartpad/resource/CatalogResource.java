package com.jinnova.smartpad.resource;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.jinnova.smartpad.UserLoggedInManager;
import com.jinnova.smartpad.domain.Catalog;
import com.jinnova.smartpad.domain.Paging;
import com.jinnova.smartpad.domain.User;
import com.jinnova.smartpad.partner.PartnerManager;
import com.jinnova.smartpad.util.JsonResponse;

@Path("/catalog")
@Produces(MediaType.APPLICATION_JSON)
public class CatalogResource {

	@GET
	@Path("/{user}")
    public JsonResponse getCatalog(@PathParam("user") String userName) {
		User user = UserLoggedInManager.instance.getUser(userName);
		if (user == null) {
			return new JsonResponse(false, "User not logged in!");
		}
		try {
			Catalog catResult = user.getCatalog();
			if (catResult == null) {
				return new JsonResponse(false, "Cannot find catalog");
			}
			/*JsonResponse result = new JsonResponse(true, catResult);
			result.put("subSysCat", newListCatFromDB(PartnerManager.instance.getSystemSubCatalog(catResult.getParentId()),
					user.toUserDB(), user.getToken()));*/
			//result.put("branchName", user.getCatalogItemBranchNameDefault());
			return catResult.getJsonResponse(user.toUserDB(), user.getToken());
		} catch (SQLException e) {
			return new JsonResponse(false, "Cannot load catalog info: " + e.getMessage());
		}
	}

	@GET
	@Path("/sys/{user}")
    public JsonResponse getSysCatalog(@PathParam("user") String userName) {
		User user = UserLoggedInManager.instance.getUser(userName);
		if (user == null) {
			return new JsonResponse(false, "User not logged in!");
		}
		try {
			return new Catalog(null, PartnerManager.instance.getSystemRootCatalog(), user.toUserDB(), user.getToken())
							.getJsonResponse(user.toUserDB(), user.getToken());
		} catch (SQLException e) {
			return new JsonResponse(false, "Cannot load catalog info: " + e.getMessage());
		}
	}

	@POST
	@Path("/")
	public JsonResponse saveCatalog(final Catalog updateCatalog) {
		// TODO
		if (updateCatalog == null) {
			// TODO validate
		}
		// TODO validate security or logic... 
		User user = UserLoggedInManager.instance.getUser(updateCatalog.getToken().getUserName());
		if (user == null) {
			return new JsonResponse(false, "User not logged in!");
		}
		// Update catalog
		try {
			Catalog updateCatalogResult = user.updateCatalog(updateCatalog);
			if (updateCatalogResult == null) {
				return new JsonResponse(false, "Cannot find or update catalog: " + updateCatalog.getName());
			}
			return new JsonResponse(true, updateCatalogResult);
		} catch (SQLException e) {
			return new JsonResponse(false, "Cannot save catalog info: " + e.getMessage());
		}
	}

	@DELETE
	@Path("/{user}/{catalogId}")
	public JsonResponse deleteCatalog(/*Catalog catalog*/@PathParam("user") String userName, @PathParam("catalogId") String catalogId) {
		User user = UserLoggedInManager.instance.getUser(userName);
		if (user == null) {
			return new JsonResponse(false, "User not logged in!");
		}
		try {
			String deleteResult = user.getCatalog().deleteSubCatalog(catalogId, user.toUserDB());
			if (deleteResult != null) {
				return new JsonResponse(false, "Cannot delete catalog info: " + deleteResult);
			}
			return new JsonResponse(true, user.getCatalog());
		} catch (SQLException e) {
			return new JsonResponse(false, "Cannot delete catalog info: " + e.getMessage());
		}
	}

	////////////////////////////////
	// Catalog item resource
	// TODO move to CatItemResource
	////////////////////////////////
	/*@GET
	@Path("/true/{user}/{catalogId}")
	@Consumes(MediaType.APPLICATION_JSON)
    public JsonResponse getSysCatalogItem(@PathParam("user") String userName, @PathParam("catalogId") String catalogId, @QueryParam("pageNumber") int pageNumber, @QueryParam("pageSize") int pageSize) {
		return getCatalogItemInternal(userName, catalogId, new Paging(pageSize, null, false, pageNumber), true);
	}*/

	@GET
	@Path("/false/{user}/{catalogId}/")
	@Consumes(MediaType.APPLICATION_JSON)
	public JsonResponse getCatalogItem(@PathParam("user") String userName, @PathParam("catalogId") String catalogId, @QueryParam("pageNumber") int pageNumber, @QueryParam("pageSize") int pageSize/*Paging paging*/) {
		return getCatalogItemInternal(userName, catalogId, new Paging(pageSize, null, false, pageNumber)/*, false*/);
	}

    private static final JsonResponse getCatalogItemInternal(String userName, String catalogId, Paging paging/*, boolean isSysCat*/) {
		User user = UserLoggedInManager.instance.getUser(userName);
		if (user == null) {
			return new JsonResponse(false, "User not logged in!");
		}
		try {
			JsonResponse result = new JsonResponse(true);
			result.put("allItems", user.loadItemByPaging(catalogId, /*isSysCat,*/ paging));
			result.put("paging", paging);
			return result;
		} catch (SQLException e) {
			return new JsonResponse(false, "Cannot load catalog item info: " + e.getMessage());
		}
	}

}