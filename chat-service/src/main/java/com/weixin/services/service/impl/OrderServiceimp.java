package com.weixin.services.service.impl;

import java.util.List;
import java.util.Map;

import com.weixin.entity.chat.Order;
import org.springframework.stereotype.Service;

import com.weixin.services.service.OrderService;

@Service
public class OrderServiceimp implements OrderService {
	@Override
	public boolean insertSelective(Order record) {
		return false;
	}

	/**
	 * 根据订单编号更新状态
	 *
	 * @param order
	 * @return
	 */
	@Override
	public boolean updateByOrderNo(Order order) {
		return false;
	}

	/**
	 * 根据userUniqueId获取订单，判断是否第一次购买
	 *
	 * @param order@return
	 */
	@Override
	public List<Order> getByUserId(Order order) {
		return null;
	}

	@Override
	public List<Order> selectBought(Map<String, Object> queryMap) {
		return null;
	}
}