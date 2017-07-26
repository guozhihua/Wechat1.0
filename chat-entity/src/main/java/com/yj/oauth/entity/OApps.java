package com.yj.oauth.entity;

import com.weixin.entity.chat.SuperVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户端
 * <br>
 * <b>功能：</b>OAppsEntity<br>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OApps extends SuperVO {
	
	
	private Integer id;//   主键
	private String appId;//   app 客户端Id
	private String appSecret;//   app安全密码
	private String appName;//   客户端名称
	private java.util.Date createTime;//   创建时间
	private Integer status;//   app状态控制  0审核不通过 1审核通过
}

