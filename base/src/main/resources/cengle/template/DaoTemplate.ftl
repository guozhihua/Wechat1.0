package ${bussPackage}.dao#if($!entityPackage).${entityPackage}#end;


import org.springframework.stereotype.Repository;

import ${bussPackage}.entity#if($!entityPackage).${entityPackage}#end.${className};
import  ${base_dao};
/**
 * 
 * <br>
 * <b>功能：</b>${className}Dao<br>
 */
 @Repository
public interface ${className}Dao extends BaseDao<${className}> {
	
	
}
