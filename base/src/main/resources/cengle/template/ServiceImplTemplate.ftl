package ${bussPackage}.service.impl#if($!entityPackage).${entityPackage}#end;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import  ${base_dao};
import  ${base_service}.impl.BaseServiceImpl;
import ${bussPackage}.entity#if($!entityPackage).${entityPackage}#end.${className};
import ${bussPackage}.dao#if($!entityPackage).${entityPackage}#end.${className}Dao;
import ${bussPackage}.service#if($!entityPackage).${entityPackage}#end.${className}Service;

/**
 * 
 * <br>
 * <b>功能：</b>${className}Service<br>
 */
@Service
@Transactional
public class  ${className}ServiceImpl  extends BaseServiceImpl<${className}> implements ${className}Service {
  private final static Logger log= Logger.getLogger(${className}ServiceImpl.class);
	

	@Autowired
    private ${className}Dao ${lowerName}Dao;
	

	@Override
	protected BaseDao<${className}> getBaseDao() {
		return ${lowerName}Dao;
	}

}
