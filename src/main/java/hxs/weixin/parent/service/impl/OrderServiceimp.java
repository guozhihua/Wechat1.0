package hxs.weixin.parent.service.impl;

import java.util.List;
import java.util.Map;

import hxs.weixin.parent.dao.PUserVoucherDao;
import hxs.weixin.parent.entity.PUserVoucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hxs.weixin.parent.dao.OrderMapper;
import hxs.weixin.parent.entity.Order;
import hxs.weixin.parent.service.OrderService;
import org.springframework.util.StringUtils;

@Service
public class OrderServiceimp implements OrderService{

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private PUserVoucherDao pUserVoucherDao;
	
	@Override
	public boolean insertSelective(Order record) {
		
		return orderMapper.insertSelective(record)>0?true:false;
	}
	@Override
	public boolean updateByOrderNo(Order order) {
		Order oldOrder = this.orderMapper.selectByTradeNo(order.getOutTradeNo());
		if(oldOrder!=null&&!StringUtils.isEmpty(oldOrder.getPuserVoucherId())){
		PUserVoucher userVoucher= pUserVoucherDao.get(oldOrder.getPuserVoucherId());
			if(userVoucher!=null){
				userVoucher.setUsed(true);
				pUserVoucherDao.updateByPrimaryKeySelective(userVoucher);
			}
		}
		
		return orderMapper.updateByOrderNo(order)>0?true:false;
	}
	@Override
	public List<Order> getByUserId(Order order) {
		
		return orderMapper.getByUserId(order);
	}

	@Override
	public List<Order> selectBought(Map<String, Object> queryMap) {
		return orderMapper.selectBought(queryMap);
	}

}
