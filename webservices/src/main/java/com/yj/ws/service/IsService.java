package com.yj.ws.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by :Guozhihua
 * Date： 2017/4/20.
 */
@WebService
public interface IsService {
    @Path("/hi")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_XML)
    @GET
    @WebMethod(operationName = "sayHi")
    public String sayHi(@WebParam(name="text") String text);


    @GET
    @Path("/app")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_XML)
    @WebMethod(operationName = "getAppById")
    public App getAppById(@WebParam(name="appId")  String appId);
}
