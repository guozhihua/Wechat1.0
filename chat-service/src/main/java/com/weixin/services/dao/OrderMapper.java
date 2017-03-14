package com.weixin.services.dao;

import java.util.List;
import java.util.Map;

import com.weixin.entity.chat.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    Order selectByTradeNo(String  orderNo);
    
    /**
     * 根据订单编号更新支付状态
     * @param order
     * @return
     */
    public int updateByOrderNo(Order order);
    
    /**
     * 根据userUniqueId获取订单，判断是否第一次购买
     * @param userUniqueId
     * @return
     */
    public List<Order> getByUserId(Order order);

    List<Order> selectBought(Map<String, Object> queryMap);
    
}