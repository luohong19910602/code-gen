package com.skg.luohong.biz.${system}.${module}.entity;

import java.util.List;
import com.skg.luohong.base.db.dao.IMapper;

/**
 *
 * @author ${author}
 * @date ${date}
 * @author 846705189@qq.com
 * @author 15013336884
 * @blog http://blog.csdn.net/u010469003
 * */
public interface ${mapperName} extends IMapper<${idType}, ${poName}>{
    public List<${poName}> findAll();
    public void update(${poName} ${firstLowwerMapperName});
    public void insert(${poName} ${firstLowwerMapperName});
    public void delete(String id);
}