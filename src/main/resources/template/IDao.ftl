package com.skg.luohong.biz.${system}.${module}.dao;

import java.util.List;

import com.skg.luohong.biz.${system}.${module}.entity.${entity};

/**
 * @Author ${author}
 * @Date ${date}
 */
public interface ${daoName} {
	public void add(${entity} entity);
    public void update(${entity} entity);
    public void delete(String id);
    public List<${entity}> findAll();
}