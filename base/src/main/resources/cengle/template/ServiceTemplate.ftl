package ${bussPackage}.service#if($!entityPackage).${entityPackage}#end;
import  ${base_service};
import ${bussPackage}.entity#if($!entityPackage).${entityPackage}#end.${className};

/**
 * 
 * <br>
 * <b>功能：</b>${className}Service<br>
 */
public interface ${className}Service extends BaseService<${className}> {

}
