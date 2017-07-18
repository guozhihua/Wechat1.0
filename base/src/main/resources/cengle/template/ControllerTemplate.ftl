package ${bussPackage}.controller#if($!controllerEntityPackage).${controllerEntityPackage}#end;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhbc.framework.controller.ABaseController;
import com.zhbc.framework.service.IBaseService;
import ${bussPackage}.entity#if($!entityPackage).${entityPackage}#end.${className};
 import ${bussPackage}.service#if($!entityPackage).${entityPackage}#end.${className}Service;
/**
 * 
 * <br>
 * <b>功能：</b>${className}Controller<br>
 *   <br>
 */ 
@Controller
@RequestMapping("/${lowerName}")
public class ${className}Controller extends ABaseController<${className}>{
	
	private final static Logger log= Logger.getLogger(${className}Controller.class);
	@Autowired
	private ${className}Service ${lowerName}Service; 
	
	@Override
	protected BaseService<${className}> getBaseService() {
		// TODO Auto-generated method stub
		return ${lowerName}Service;
	}
	
	

}
