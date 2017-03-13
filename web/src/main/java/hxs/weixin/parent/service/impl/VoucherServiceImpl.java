package hxs.weixin.parent.service.impl;

import hxs.weixin.parent.dao.BaseDao;
import hxs.weixin.parent.dao.VoucherDao;
import hxs.weixin.parent.entity.Voucher;
import hxs.weixin.parent.service.VoucherService;
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
