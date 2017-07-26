package ${bussPackage}.entity#if($!entityPackage).${entityPackage}#end;

import ${superVO};
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ${codeName}
 * <br>
 * <b>功能：</b>${className}Entity<br>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ${className} extends SuperVO {
	
	${feilds}
}

