package com.skg.luohong.biz.${system}.${module}.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.skg.luohong.biz.${system}.${module}.dao.${daoName};
import com.skg.luohong.biz.${system}.${module}.entity.${entity};
import com.skg.luohong.biz.${system}.${module}.entity.${mapperName};

@Repository
public class ${daoImplName} implements IDaysUserDao {
	
	@Autowired
	private ${mapperName} mapper;
	
	public void add(${entity} entity) {
	    mapper.insert(entity);
	}

	public void update(${entity} entity) {
		mapper.update(entity);
	}

	public void delete(String id) {
		mapper.delete(id);
	}

	public List<${entity}> findAll() {
		return mapper.findAll();
	}
   
    
}