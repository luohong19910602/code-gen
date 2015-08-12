package com.skg.luohong.${system}.${module}.entity;

import java.util.Date;

/**
 *
 * @author ${author}
 * @date ${date}
 * */
public class ${entity}{
    
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