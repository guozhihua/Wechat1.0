package hxs.weixin.parent.plugins;




import hxs.weixin.parent.sys.exceptions.ProBaseException;

import java.util.Map;

/**
 * Created by :Guozhihua
 * Date： 2017/3/6.
 */

public class IdStrage implements Strategy {


    /**
     * 传入一个需要分表的表名，返回一个处理后的表名
     * Strategy必须包含一个无参构造器
     *
     * @param tableName  表名
     * @param cloumnCode
     * @param map        @return
     */
    @Override
    public String convert(String tableName,String strategy, String[] cloumnCode, Map map) throws ProBaseException {
       Integer resutl = getTableIndexForId(strategy,cloumnCode,map);
       if(resutl==null){
           throw  new ProBaseException("分表获取对应的表index失败：tableName ="+tableName+",strategy="+strategy+",cloumnCode="+cloumnCode.toString());
       }else{
           StringBuilder sb = new StringBuilder(tableName);
           sb.append("_").append(resutl);
           return  sb.toString();
       }
    }

    private  Integer  getTableIndexForId(String format ,String[] cloumCode ,Map map) throws  ProBaseException{
         Integer result = null;
         if(format.startsWith("%")&&cloumCode!=null&&cloumCode.length>0&&map!=null&&!map.isEmpty()){
             int tableNum=this.getFromatNum(format);
             int sumCode = 0 ;
             for(String column :cloumCode){
                sumCode+= Math.abs(map.get(column).toString().hashCode())*7;
             }
             result = sumCode%tableNum;
         }else{
             throw  new ProBaseException("请检查配置的分表规则是否正确:strategy is "+format+",columnCode is "+cloumCode.toString());
         }
        return result;
    }
    private int  getFromatNum(String format) throws  ProBaseException{
        String  tableNum=format.replace("%","");
        int result =Integer.parseInt(tableNum);
        if(result==0){
            throw  new ProBaseException("分表格式失败，必须是%d 且数字必须大于0:"+format);
        }
        return result;
    }


}
