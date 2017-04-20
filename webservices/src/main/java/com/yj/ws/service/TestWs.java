package com.yj.ws.service;

import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * 中间件安全接口做法。
 *
 * 1.接口验证签名：webServive安全认证接口方案，可以采用微信做法 提供公用的appId，appKey验证接口，验证成功，则返回对应的
 * access_key,使用对应的access_key调用接口。access_key使用有效期的设置机制，由客户端决定是否获取新的access_key.
 *
 * 2.数据加密解密：或者可以使用非对称加密的方式 RSA 加密算法。提供对客户端自己的公钥，自己也拥有所有客户端的公钥，保存自己的私钥，
 * 这样，服务端通过客户端的公钥加密数据给客户端发消息，客户端收到消息用自己的私钥解密获取对应的数据。客户端可以通过服务端的
 * 公钥进行数据加密，服务端接受的数据，通过自己的私钥进行解密获取数据。
 *
 * 效率上，验证签名的方式肯定是比使用数据加密的方式快的，数据签名更加侧重的是数据的安全性。
 * @author zz
 *	Date 2016-07-11
 *	Descirbe 调用资源库基本接口
 */
@Component
@Path(value = "/test")
@Consumes({MediaType.APPLICATION_JSON})
//@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
@CrossOriginResourceSharing(allowAllOrigins = true)
public class TestWs {

	private final Logger logger = LoggerFactory.getLogger(TestWs.class);


    /**
   	 * 根据学科从视图获取全部年级
   	 * */
   	@GET
   	@Path("/t1")
	@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
   	public Response allUsefulGrade(@QueryParam("requestId") String requestId, @QueryParam("subjectCode") String subjectCode,
   			@Context HttpServletRequest request,@Context HttpServletResponse response){
   		try {
			System.out.println("2312321");
		}finally {

		}

		return Response.ok("成功").build();
   	}




}
