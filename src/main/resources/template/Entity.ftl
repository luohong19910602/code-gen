package com.skg.luohong.biz.${system}.${module}.entity;

import java.util.Date;
import com.skg.luohong.base.db.dao.AbstractPo;

/**
 *
 * @author ${author}
 * @date ${date}
 * @author 846705189@qq.com
 * @author 15013336884
 * @blog http://blog.csdn.net/u010469003
 * */
public class ${entity} extends AbstractPo<${idType}>{
    
<#list fields as field>
    private ${field.type} ${field.name};
</#list>
    
<#list fields as field>
    public void set${field.firstUpperName}(${field.type} ${field.name}){
        this.${field.name} = ${field.name};
    }
        
    public ${field.type} get${field.firstUpperName}(){
        return ${field.name};
    }
</#list>   
}