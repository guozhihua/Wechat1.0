package com.weixin.services.service;

import com.weixin.entity.chat.Order;

import java.util.List;
import java.util.Map;


public interface OrderService {

	public boolean insertSelective(Order record);
	
	/**
     * 根据订单编号更新状态
     * @param order
     * @return
     */
    public boolean updateByOrderNo(Order order);
    
    /**
     * 根据userUniqueId获取订单，判断是否第一次购买
     * @param userUniqueId
     * @return
     */
    public List<Order> getByUserId(Order order);

    List<Order> selectBought(Map<String, Object> queryMap);
}
