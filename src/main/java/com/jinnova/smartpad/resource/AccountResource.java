package com.jinnova.smartpad.resource;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.jinnova.smartpad.util.JsonResponse;

@Path("/acc")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

	@GET
    public JsonResponse getFeed(@QueryParam("userId")String userName, @QueryParam("passwordId")String password, 
    		@Context HttpServletResponse context) 
    		throws SQLException {
		//IUser userLogged = PartnerManager.instance.login(userName, password);
		//User user = new User(userName, password);
		//context.getHttpHeaders().add("Access-Control-Allow-Origin", "*");
		context.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
		return new JsonResponse(true, "success");
	}
}