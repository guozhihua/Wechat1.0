package hxs.weixin.parent.plugins;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by :Guozhihua
 * Date： 2017/3/6.
 */
@Component
public class StrategyManager{
    /**
     * 传入一个需要分表的表名，返回一个处理后的表名
     * Strategy必须包含一个无参构造器
     *
     * @param tableName
     * @return
     */
    private   Map<String,Strategy> strategies;

    public Strategy getStrategy(String strageVal){
        if(strategies==null||strategies.isEmpty()){
            return  null;
        }
        if(strageVal.startsWith("%")){
            strageVal="%d";
        }
        return  strategies.get(strageVal);

    }

    public void setStrategies(Map<String, Strategy> strategies) {
        this.strategies = strategies;
    }

    public Map<String, Strategy> getStrategies() {
        return strategies;
    }
}
