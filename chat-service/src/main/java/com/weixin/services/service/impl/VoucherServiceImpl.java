package com.weixin.services.service.impl;

import com.weixin.entity.chat.Voucher;
import com.weixin.services.dao.BaseDao;
import com.weixin.services.dao.VoucherDao;
import com.weixin.services.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by :Guozhihua
 * Date： 2016/11/24.
 */
@Service("voucherServiceImpl")
public class VoucherServiceImpl extends BaseServiceImpl<Voucher> implements VoucherService{

    @Autowired
    private VoucherDao voucherDao;


    @Override
    protected BaseDao<Voucher> getBaseDao() {
        return this.voucherDao;
    }


}
