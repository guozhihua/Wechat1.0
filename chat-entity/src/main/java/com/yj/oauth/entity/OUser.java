package com.yj.oauth.entity;

import com.weixin.entity.chat.SuperVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户资源
 * <br>
 * <b>功能：</b>OUserEntity<br>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OUser extends SuperVO {
	
	
	private String uuid;//
	private String userName;//   用户名
	private String password;//   密码
	private String salt;//   加密盐
}

