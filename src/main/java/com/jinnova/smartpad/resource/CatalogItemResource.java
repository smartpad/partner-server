package com.jinnova.smartpad.resource;

import java.sql.SQLException;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jinnova.smartpad.UserLoggedInManager;
import com.jinnova.smartpad.domain.Catalog;
import com.jinnova.smartpad.domain.CatalogItem;
import com.jinnova.smartpad.domain.User;
import com.jinnova.smartpad.partner.ICatalogField;
import com.jinnova.smartpad.util.JsonResponse;

@Path("/catalogItem")
@Produces(MediaType.APPLICATION_JSON)
public class CatalogItemResource {

	@POST
	@Path("/{user}/{catalogId}/{sysCatId}/{isSysCat}")
	public JsonResponse saveSysCatalogItem(@PathParam("user") String userName, final CatalogItem updateCatalogItem, 
			@PathParam("catalogId") String catalogId, @PathParam("sysCatId") String sysCatId, @PathParam("isSysCat") Boolean isSysCat) {
		return saveCatalogItemInternal(userName, updateCatalogItem, catalogId, sysCatId, isSysCat);
	}

	@POST
	@Path("/{user}/{catalogId}/{isSysCat}")
	public JsonResponse saveCatalogItem(@PathParam("user") String userName, final CatalogItem updateCatalogItem, 
			@PathParam("catalogId") String catalogId, @PathParam("isSysCat") Boolean isSysCat) {
		return saveCatalogItemInternal(userName, updateCatalogItem, catalogId, null, isSysCat);
	}

	/*@POST
	@Path("/{user}/{catalogId}/{sysCatId}/{isSysCat}")
	public JsonResponse saveCatalogItem(@PathParam("user") String userName, final CatalogItem updateCatalogItem, 
			@PathParam("catalogId") String catalogId, @PathParam("sysCatId") String sysCatId) {
		return saveCatalogItemInternal(userName, updateCatalogItem, catalogId, sysCatId, false);
	}*/

	private static final JsonResponse saveCatalogItemInternal(String userName, final CatalogItem updateCatalogItem, String catalogId, 
			String sysCatId, boolean isSys) {
		if (updateCatalogItem == null) {
			// TODO validate
		}
		// TODO validate security or logic... 
		User user = UserLoggedInManager.instance.getUser(userName);
		if (user == null) {
			return new JsonResponse(false, "User not logged in!");
		}
		// Update catalog
		try {
			Catalog updatedCatalogResult = user.updateCatalogItem(updateCatalogItem, catalogId, sysCatId, isSys);
			if (updatedCatalogResult == null) {
				return new JsonResponse(false, "Cannot find or update catalog item: " + updateCatalogItem.getValuesSingle().get(ICatalogField.F_NAME));
			}
			return new JsonResponse(true, updatedCatalogResult);
		} catch (SQLException e) {
			return new JsonResponse(false, "Cannot save catalog info: " + e.getMessage());
		}
	}

	@DELETE
	@Path("/{user}/{catalogItemId}/{catalogId}/{isSysCat}")
	public JsonResponse deleteCatalog(@PathParam("user") String userName, @PathParam("catalogItemId") String catalogItemId, @PathParam("catalogId") String catalogId
			, @PathParam("isSysCat") Boolean isSysCat) {
		User user = UserLoggedInManager.instance.getUser(userName);
		if (user == null) {
			return new JsonResponse(false, "User not logged in!");
		}
		try {
			Catalog deletedOneItemCatalog = user.deleteCatItem(catalogItemId, catalogId, isSysCat, user.toUserDB());
			if (deletedOneItemCatalog == null) {
				return new JsonResponse(false, "Cannot delete catalog item info");
			}
			return new JsonResponse(true, deletedOneItemCatalog);
		} catch (SQLException e) {
			return new JsonResponse(false, "Cannot delete catalog item info: " + e.getMessage());
		}
	}
}