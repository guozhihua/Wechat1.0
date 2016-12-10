package hxs.weixin.parent.dao;

import java.util.List;
import java.util.Map;

import hxs.weixin.parent.entity.VGame;
import org.springframework.stereotype.Repository;


@Repository
public interface VGameMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VGame record);

    VGame selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VGame record);

    int updateByPrimaryKey(VGame record);
    
    /**
     * <pre>selectUserByUserCode(通过用户id查询用户信息)   
    	 * 创建人：王亮 wanglmir@163.com   
    	 * 创建时间：2016年11月26日 下午3:27:43    
    	 * 修改人：王亮 wanglmir@163.com     
    	 * 修改时间：2016年11月26日 下午3:27:43    
    	 * 修改备注： 
    	 * @param record
    	 * @return</pre>
     */
    public VGame selectUserByUserCode(VGame record);
    
    /**
     * <pre>saveVGame(添加信息)   
    	 * 创建人：王亮 wanglmir@163.com   
    	 * 创建时间：2016年11月26日 下午3:57:27    
    	 * 修改人：王亮 wanglmir@163.com     
    	 * 修改时间：2016年11月26日 下午3:57:27    
    	 * 修改备注： 
    	 * @param record</pre>
     */
    public void saveVGame(VGame record);
    /**
     * <pre>saveVGame(修改信息)   
     * 创建人：王亮 wanglmir@163.com   
     * 创建时间：2016年11月26日 下午3:57:27    
     * 修改人：王亮 wanglmir@163.com     
     * 修改时间：2016年11月26日 下午3:57:27    
     * 修改备注： 
     * @param record</pre>
     */
    public void updateVGameByUserId(VGame record);
    /**
     * <pre>saveVGame(查询用户当前排行榜)   
     * 创建人：王亮 wanglmir@163.com   
     * 创建时间：2016年11月26日 下午3:57:27    
     * 修改人：王亮 wanglmir@163.com     
     * 修改时间：2016年11月26日 下午3:57:27    
     * 修改备注： 
     * @param record</pre>
     */
    //public VGame selectVGameRankByUserId(VGame record);
    public Map selectVGameRankByUserId(VGame record);
    /**
     * <pre>saveVGame(查询全球排行榜)   
     * 创建人：王亮 wanglmir@163.com   
     * 创建时间：2016年11月26日 下午3:57:27    
     * 修改人：王亮 wanglmir@163.com     
     * 修改时间：2016年11月26日 下午3:57:27    
     * 修改备注： 
     * @param record</pre>
     */
    //public List<VGame> selectVGameRankList();
    public List<Map> selectVGameRankList();
}