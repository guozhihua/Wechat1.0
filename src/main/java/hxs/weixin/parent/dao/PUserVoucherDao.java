package hxs.weixin.parent.dao;


import hxs.weixin.parent.entity.PUserVoucher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>功能：</b>PUserVoucherDao<br>
 */
@Repository
public interface PUserVoucherDao extends BaseDao<PUserVoucher> {

    int selectVoucherUserCount(Map<String, Object> paramMap);

    /**
     * 获取用户所有可用的优惠券
     * @return
     */
    List<Map<String,Object>> getUserVoucherListStatics(Map<String, Object> paramMap);

    PUserVoucher selectByUserIdAndVoucherId(Map<String,Object> paramMap) throws Exception;
}
