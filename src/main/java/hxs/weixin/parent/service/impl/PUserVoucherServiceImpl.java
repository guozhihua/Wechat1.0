package hxs.weixin.parent.service.impl;

import com.github.pagehelper.PageHelper;

import com.github.pagehelper.PageInfo;
import com.google.gson.reflect.TypeToken;
import hxs.weixin.parent.dao.BaseDao;
import hxs.weixin.parent.dao.PUserVoucherDao;
import hxs.weixin.parent.dao.UserMapper;
import hxs.weixin.parent.dao.VoucherDao;
import hxs.weixin.parent.entity.PUserVoucher;
import hxs.weixin.parent.entity.User;
import hxs.weixin.parent.entity.Voucher;
import hxs.weixin.parent.entity.vo.UserVoucherVo;
import hxs.weixin.parent.service.GameService;
import hxs.weixin.parent.service.PUserVoucherService;
import hxs.weixin.parent.service.VGameService;
import hxs.weixin.parent.service.WGameService;
import hxs.weixin.parent.sys.CacheKey;
import hxs.weixin.parent.sys.enums.VoucherWayEnum;
import hxs.weixin.parent.sys.exceptions.ProBaseException;
import hxs.weixin.parent.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 
 * <br>
 * <b>功能：</b>PUserVoucherService<br>
 */
@Service("PUserVoucherServiceImpl")
public class PUserVoucherServiceImpl extends BaseServiceImpl<PUserVoucher> implements PUserVoucherService {

  private final static Logger log= Logger.getLogger(PUserVoucherServiceImpl.class);
	@Autowired
    private PUserVoucherDao pUserVoucherDao;

	@Autowired
	private VoucherDao voucherDao;

	@Autowired
	private GameService gameService;

	@Autowired
	private UserMapper userMapper;


	@Override
	protected BaseDao<PUserVoucher> getBaseDao() {
		return pUserVoucherDao;
	}

	@Override
	public Map<String, Object> insertUserVoucher(PUserVoucher userVoucher, VoucherWayEnum voucherWay) throws Exception {
		Map<String,Object> resuletMap = new HashMap<>();
		boolean isFX=false;
		//获取到所有可用的券信息
		List<Voucher> list =getVoucherListByRedis();
		Voucher vouch = null;
	    if(CollectionUtils.isNotEmpty(list)){
           for(Voucher voucher :list){
			   if(voucherWay.getAmount()==voucher.getAmount()){
				   vouch=voucher;
				   break;
			   }
		   }
		}
		//每次分享只能是获取到一张券
		userVoucher.setVoucherWay(voucherWay.getWayCode()+"");
		userVoucher.setVoucherId(vouch.getVoucherId());
		switch (voucherWay){
			case VOUCHER_WAY_FX:
				//朋友圈分享
				if(checkVoucherHave(userVoucher.getUserId(),vouch.getVoucherId(),voucherWay.getWayCode()+"")){
					insertInto(userVoucher,vouch);
					isFX =true;
				}else{
					throw  new ProBaseException("您已经获取了分享朋友圈的赠券");
				}
				break;

			case VOUCHER_WAY_SS_YX:
				//游戏分享
				User user =userMapper.selectByPrimaryKey(userVoucher.getUserId());
				if(user!=null){
					gameService.shareGetVGameTimes(user.getOpenId());
				}
				if(checkVoucherHave(userVoucher.getUserId(),vouch.getVoucherId(),voucherWay.getWayCode()+"")){
					insertInto(userVoucher,vouch);
					isFX =true;
				}else{
					throw  new ProBaseException("您已经获取了分享速算赢家的赠券");
				}
				break;
			case VOUCHER_WAY_DC_YX:
				User user1 =userMapper.selectByPrimaryKey(userVoucher.getUserId());
				if(user1!=null){
					gameService.shareGetWGameTimes(user1.getOpenId());
				}
				if(checkVoucherHave(userVoucher.getUserId(),vouch.getVoucherId(),voucherWay.getWayCode()+"")){
					insertInto(userVoucher,vouch);
					isFX =true;
				}else{
					throw  new ProBaseException("您已经获取了分享单词速拼的赠券");
				}
				break;
			case VOUCHER_WAY_FIRST_LOGIN:
				//登陆分享只有一次
                    List<PUserVoucher> userVouchers1 = new ArrayList<>();
					for(int i= 0 ;i<2;i++){
						if(i==0){
							PUserVoucher userVoucher1 = new PUserVoucher();
							userVoucher1.setCreateTime(new Date());
							userVoucher1.setVoucherId(vouch.getVoucherId());
							userVoucher1.setExpireTime(DateUtil.addDays(vouch.getEffectiveDay()));
							userVoucher1.setVoucherWay(voucherWay.getWayCode()+"");
							userVoucher1.setUserId(userVoucher.getUserId());
							userVouchers1.add(userVoucher1);
						}else {
							Voucher voucher = null;
							for(Voucher v:list){
								if(v.getAmount()==VoucherWayEnum.VOUCHER_WAY_FIRST_LOGIN_1.getAmount()){
									voucher=v;
								}
							}
							if(voucher!=null){
								PUserVoucher userVoucher2 = new PUserVoucher();
								userVoucher2.setCreateTime(new Date());
								userVoucher2.setVoucherId(voucher.getVoucherId());
								userVoucher2.setExpireTime(DateUtil.addDays(voucher.getEffectiveDay()));
								userVoucher2.setVoucherWay(VoucherWayEnum.VOUCHER_WAY_FIRST_LOGIN_1.getWayCode()+"");
								userVoucher2.setUserId(userVoucher.getUserId());
								userVouchers1.add(userVoucher2);
							}
						}


					}
					this.pUserVoucherDao.insertInBatch(userVouchers1);
					isFX =true;

				    break;
			case VOUCHER_WAY_PH:
				//排行版分享只能一次
				if(checkVoucherHave(userVoucher.getUserId(),vouch.getVoucherId(),voucherWay.getWayCode()+"")){
					insertInto(userVoucher,vouch);
					isFX =true;
				}else{
					throw  new ProBaseException("已经获取过本榜分享的赠券");
				}
				break;
			case VOUCHER_WAY_INVITED:
				//排行版分享只能一次
				if(checkVoucherHave(userVoucher.getUserId(),vouch.getVoucherId(),voucherWay.getWayCode()+"")){
					insertInto(userVoucher,vouch);
					isFX =true;
				}else{
					throw  new ProBaseException("已经获取邀请赠券");
				}
				break;
			case VOUCHER_WAY_GM :
				//购买
				Map<String,Object> paramMap=new HashMap<>();
				paramMap.put("userId",userVoucher.getUserId());
				paramMap.put("outTradeNo",userVoucher.getOutTradeNo());
				List<PUserVoucher> userVouchers=this.pUserVoucherDao.selectList(paramMap);
				if(CollectionUtils.isEmpty(userVouchers)){
					insertInto(userVoucher,vouch);
					isFX =true;
				}
			default: break;
		}
		if(!isFX){
			throw  new ProBaseException("获取赠券失败！");
		}
		resuletMap.put("voucherDesc",vouch.getDescinfo());
		resuletMap.put("userId",userVoucher.getUserId());
		resuletMap.put("expireTime",userVoucher.getExpireTime());
		resuletMap.put("createTime",userVoucher.getCreateTime());
		resuletMap.put("wayType",userVoucher.getVoucherWay());
		resuletMap.put("wayTypeInfo",voucherWay.getWayInfo());
		resuletMap.put("amount",vouch.getAmount());
		return resuletMap;
	}

	private  void insertInto(PUserVoucher  userVoucher,Voucher voucher){
		userVoucher.setCreateTime(new Date());
		userVoucher.setVoucherId(voucher.getVoucherId());
		userVoucher.setExpireTime(DateUtil.addDays(voucher.getEffectiveDay()));
		pUserVoucherDao.insert(userVoucher);
	}

    /**
	 * 对于某些券，只能获取一次
	 * @return
     */
	private boolean checkVoucherHave(String userId,int voucherId,String voucherWay){
		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("userId",userId);
		paramMap.put("voucherId",voucherId);
		paramMap.put("voucherWay",voucherWay);
        int num=this.pUserVoucherDao.selectVoucherUserCount(paramMap);
		if(num>0){
			return  false;
		}else{
			return true ;
		}
	}


	@Override
	public PageInfo<PUserVoucher> getPageList(int pageNum, int pageSize, Map<String, Object> paramMap) {
		PageHelper.startPage(pageNum,pageSize);
		List<PUserVoucher> list =this.selectList(paramMap);
		PageInfo<PUserVoucher> pageInfo =new PageInfo<PUserVoucher>(list);
		return pageInfo;
	}

	@Override
	public int checkUserVoucher(Map<String, Object> paramMap) {
		 Voucher voucher =voucherDao.selectOne(paramMap);
		 paramMap.put("voucherId",voucher.getVoucherId());
		 int num  = pUserVoucherDao.selectVoucherUserCount(paramMap);
		return num;
	}

	@Override
	public List<UserVoucherVo> getUserVoucherCount(Map<String, Object> paramMap) {
		List<UserVoucherVo> resultVo = new ArrayList<>();
		List<Map<String,Object>> userVouchers = pUserVoucherDao.getUserVoucherListStatics(paramMap);
		List<Voucher> list =getVoucherListByRedis();
			for(Voucher v:list){
				boolean flag =false ;
				if(!CollectionUtils.isEmpty(userVouchers)) {
					for (Map<String, Object> o : userVouchers) {
						if (v.getVoucherId() == Integer.parseInt(o.get("voucher_id").toString())) {
							UserVoucherVo vo = new UserVoucherVo();
							vo.setNum(Integer.parseInt(o.get("countNum").toString()));
							vo.setVoucherAoumt(v.getAmount());
							vo.setVoucherId(v.getVoucherId());
							resultVo.add(vo);
							flag = true;
							break;
						}
					}
				}
			    if(!flag){
					UserVoucherVo vo =new UserVoucherVo();
					vo.setVoucherAoumt(v.getAmount());
					vo.setVoucherId(v.getVoucherId());
					resultVo.add(vo);
				}

			}
		return resultVo;


	}

	/**
	 * 购买时修改  p_user_voucher   中快过期的券状态
	 * @param userId
	 * @param amount
	 * @return
	 */
	public String updateVoucherByUserIdAndAmount(String userId,int amount) throws Exception{
		String result= null;
		if(StringUtils.isBlank(userId) || amount<=0){
			throw new ProBaseException("userId或者amount为0");
		}
		//获取到所有可用的券信息
		List<Voucher> list1 =getVoucherListByRedis();
		Voucher vouch = null;
		if(CollectionUtils.isNotEmpty(list1)){
			for(Voucher voucher :list1){
				if(amount==voucher.getAmount()){
					vouch=voucher;
					break;
				}
			}
		}
		if(vouch==null){
			return  null;
		}
		Map<String,Object> vouMap = new HashMap<String,Object>();
		vouMap.put("userId",userId);
		vouMap.put("voucherId",vouch.getVoucherId());
		vouMap.put("isUsed","0");
		PUserVoucher pUserVou = pUserVoucherDao.selectByUserIdAndVoucherId(vouMap);
		if(pUserVou != null){
			List<PUserVoucher> list = pUserVoucherDao.selectList(vouMap);
			if(list.size()<=0){
				return  null;
			}
			PUserVoucher pUserVoucher  = list.get(0);
			 result = pUserVoucher.getId();
//			pUserVoucher.setUsed(true);
//			pUserVoucherDao.updateByPrimaryKeySelective(pUserVoucher);
		}
		return   result;
	}




	/**
	 * 购买时修改  p_user_voucher   中快过期的券状态
	 * @param userId
	 * @param amount
	 * @return
	 */
	public String updateVUserVoucherInner(String userId,int amount) throws Exception{
		String result= null;
		if(StringUtils.isBlank(userId) || amount<=0){
			throw new ProBaseException("userId或者amount为0");
		}
		//获取到所有可用的券信息
		List<Voucher> list1 =getVoucherListByRedis();
		Voucher vouch = null;
		if(CollectionUtils.isNotEmpty(list1)){
			for(Voucher voucher :list1){
				if(amount==voucher.getAmount()){
					vouch=voucher;
					break;
				}
			}
		}
		if(vouch==null){
			return  null;
		}
		Map<String,Object> vouMap = new HashMap<String,Object>();
		vouMap.put("userId",userId);
		vouMap.put("voucherId",vouch.getVoucherId());
		vouMap.put("isUsed","0");
		PUserVoucher pUserVou = pUserVoucherDao.selectByUserIdAndVoucherId(vouMap);
		if(pUserVou != null){
			List<PUserVoucher> list = pUserVoucherDao.selectList(vouMap);
			if(list.size()<=0){
				return  null;
			}
			PUserVoucher pUserVoucher  = list.get(0);
			result = pUserVoucher.getId();
			pUserVoucher.setUsed(true);
			pUserVoucherDao.updateByPrimaryKeySelective(pUserVoucher);
		}
		return   result;
	}

	@Override
	public void insertBatch(List<PUserVoucher> userVouchers) {
		pUserVoucherDao.insertInBatch(userVouchers);
	}



	protected List<Voucher> getVoucherListByRedis(){
		//获取到所有可用的券信息
		List<Voucher> list =gson.fromJson(this.getRedisClientTemplate().get(CacheKey.VOUCHER_ALL),new TypeToken<List<Voucher>>() {}.getType());
		if(CollectionUtils.isEmpty(list)){
			Map<String,Object> param=new HashMap<>();
			param.put("isDel",0+"");
			list = voucherDao.selectList(param);
			this.getRedisClientTemplate().set(CacheKey.VOUCHER_ALL,gson.toJson(list));
			this.getRedisClientTemplate().expire(CacheKey.VOUCHER_ALL,3600);
		}
		return  list;

	}

}
