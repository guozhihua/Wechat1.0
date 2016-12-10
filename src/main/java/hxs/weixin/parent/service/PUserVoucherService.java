package hxs.weixin.parent.service;

import com.github.pagehelper.PageInfo;
import hxs.weixin.parent.entity.PUserVoucher;
import hxs.weixin.parent.entity.vo.UserVoucherVo;
import hxs.weixin.parent.sys.enums.VoucherWayEnum;

import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>PUserVoucherService<br>
 */
public interface PUserVoucherService extends BaseService<PUserVoucher> {

    public Map<String ,Object> insertUserVoucher(PUserVoucher userVoucher,VoucherWayEnum voucherWay) throws Exception;

    public PageInfo<PUserVoucher> getPageList(int pageNum, int pageSize, Map<String,Object> paramMap);


    public int checkUserVoucher(Map<String,Object> paramMap);

    public List<UserVoucherVo> getUserVoucherCount(Map<String,Object> paramMap);

    String updateVoucherByUserIdAndAmount(String userId,int  amount) throws Exception;

    String updateVUserVoucherInner(String userId,int amount) throws Exception;

    void insertBatch(List<PUserVoucher> userVouchers);

}
