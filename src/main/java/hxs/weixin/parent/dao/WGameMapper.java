package hxs.weixin.parent.dao;

import java.util.List;
import java.util.Map;

import hxs.weixin.parent.entity.WGame;
import hxs.weixin.parent.entity.WGame;
import org.springframework.stereotype.Repository;

@Repository
public interface WGameMapper {
	int deleteByPrimaryKey(Long id);

    int insert(WGame record);

    WGame selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WGame record);

    int updateByPrimaryKey(WGame record);
    
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
    public WGame selectUserByUserCode(WGame record);
    
    /**
     * <pre>saveWGame(添加信息)   
    	 * 创建人：王亮 wanglmir@163.com   
    	 * 创建时间：2016年11月26日 下午3:57:27    
    	 * 修改人：王亮 wanglmir@163.com     
    	 * 修改时间：2016年11月26日 下午3:57:27    
    	 * 修改备注： 
    	 * @param record</pre>
     */
    public void saveWGame(WGame record);
    /**
     * <pre>saveWGame(修改信息)   
     * 创建人：王亮 wanglmir@163.com   
     * 创建时间：2016年11月26日 下午3:57:27    
     * 修改人：王亮 wanglmir@163.com     
     * 修改时间：2016年11月26日 下午3:57:27    
     * 修改备注： 
     * @param record</pre>
     */
    public void updateWGameByUserId(WGame record);
    /**
     * <pre>saveWGame(查询用户当前排行榜)   
     * 创建人：王亮 wanglmir@163.com   
     * 创建时间：2016年11月26日 下午3:57:27    
     * 修改人：王亮 wanglmir@163.com     
     * 修改时间：2016年11月26日 下午3:57:27    
     * 修改备注： 
     * @param record</pre>
     */
    //public WGame selectWGameRankByUserId(WGame record);
    public Map selectWGameRankByUserId(WGame record);
    /**
     * <pre>saveWGame(查询全球排行榜)   
     * 创建人：王亮 wanglmir@163.com   
     * 创建时间：2016年11月26日 下午3:57:27    
     * 修改人：王亮 wanglmir@163.com     
     * 修改时间：2016年11月26日 下午3:57:27    
     * 修改备注： 
     * @param record</pre>
     */
    //public List<WGame> selectWGameRankList();
    public List<Map> selectWGameRankList();
}