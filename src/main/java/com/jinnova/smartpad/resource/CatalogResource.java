package com.jinnova.smartpad.resource;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.jinnova.smartpad.UserLoggedInManager;
import com.jinnova.smartpad.domain.Catalog;
import com.jinnova.smartpad.domain.CatalogItem;
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
		user.getAllStore();
		List<Catalog> allCatalog = new LinkedList<>();
		
		return new JsonResponse(true, allCatalog);
	}

	/*private class CatalogSample extends Catalog {

		private String name;
		private String des;
		
		public CatalogSample(List<Catalog> allSubcatalog, String name, String des) {
			super(null, allSubcatalog);
			this.name = name;
			this.des = des;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getDes() {
			return des;
		}
	}

	private class CatalogItemSample extends CatalogItem {

		private String name;
		private String des;
		
		public CatalogItemSample(String name, String des) {
			super(name, des);
			this.name = name;
			this.des = des;
		}
		
		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getDes() {
			return des;
		}
	}

	private static final Catalog createSampleCatalog(String name, String des) {
		return null;
	}*/
}
