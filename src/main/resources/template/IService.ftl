package com.skg.luohong.biz.${system}.${module}.service;

import java.util.List;

import com.skg.luohong.biz.${system}.${module}.entity.${entity};

/**
 * @Author ${author}
 * @Date ${date}
 */
public interface ${serviceName} {
	public void add(${entity} entity);
    public void update(${entity} entity);
    public void delete(String id);
    public List<${entity}> findAll();
}