package com.skg.luohong.biz.${system}.${module}.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skg.luohong.biz.${system}.${module}.dao.${daoName};
import com.skg.luohong.biz.${system}.${module}.entity.${poName};
import com.skg.luohong.biz.${system}.${module}.service.${serviceName};

@Service
public class ${serviceImplName} implements ${serviceName} {
    
	@Autowired
	private ${daoName} dao;
	
	public void add(${poName} entity) {
		dao.create(entity);
	}

	public void update(${poName} entity) {
		dao.update(entity);
	}

	public void delete(${idType} id) {
		dao.delete(id);
	}

	public List<${poName}> findAll() {
		return dao.findAll();
	}
   
    
}
