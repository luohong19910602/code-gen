package com.skg.luohong.biz.${system}.${module}.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skg.luohong.biz.${system}.${module}.dao.${daoName};
import com.skg.luohong.biz.${system}.${module}.entity.${poName};
import com.skg.luohong.biz.${system}.${module}.service.${serviceName};
import com.skg.luohong.base.db.dao.SqlParam;

/**
 *
 * @author ${author}
 * @date ${date}
 * @author 846705189@qq.com
 * @author 15013336884
 * @blog http://blog.csdn.net/u010469003
 * */
@Service
public class ${serviceImplName} implements ${serviceName} {
    
	@Autowired
	private ${daoName} dao;
	
	@Override
	public void add(${poName} entity) {
		dao.create(entity);
	}
    
    @Override
	public void update(${poName} entity) {
		dao.update(entity);
	}
    
    @Override
	public void delete(${idType} id) {
		dao.delete(id);
	}
	
	@Override
	public List<${poName}> findAll(){
	    return dao.findAll();
	}
	
	@Override
    public List<${poName}> findAll(SqlParam params){
        return dao.findAll(params);
    }
    
    @Override
    public Integer countAll(){
        return dao.countAll();
    }
    
    @Override
    public Integer countAll(SqlParam params){
        return dao.countAll(params);
    }
}
