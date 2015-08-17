package com.skg.luohong.biz.${system}.${module}.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skg.luohong.biz.${system}.${module}.dao.${daoName};
import com.skg.luohong.biz.${system}.${module}.entity.${entity};
import com.skg.luohong.biz.${system}.${module}.service.${serviceName};

@Service
public class ${serviceImplName} implements ${serviceName} {
    
	@Autowired
	private ${daoName} dao;
	
	public void add(${entity} entity) {
		dao.add(entity);
	}

	public void update(${entity} entity) {
		dao.update(entity);
	}

	public void delete(String id) {
		dao.delete(id);
	}

	public List<${entity}> findAll() {
		return dao.findAll();
	}
   
    
}
