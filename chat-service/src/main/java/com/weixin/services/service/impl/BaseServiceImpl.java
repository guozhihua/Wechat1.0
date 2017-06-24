package com.weixin.services.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.weixin.entity.chat.SuperVO;
import com.weixin.services.dao.BaseDao;
import com.weixin.services.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by :Guozhihua
 * Date： 2016/11/24.
 */
public abstract class BaseServiceImpl<T extends SuperVO> implements BaseService<T> {

    protected abstract BaseDao<T> getBaseDao();

    protected Gson gson=new Gson();

    //默认的缓存有效期
    public int DEFAULT_TIME_OUT=60*60*2;
    public int DEFAULT_MAX_TIME_OUT=60*60*24;

    @Override
    public List<T> selectList(Map<String, Object> paramMap) {
        return getBaseDao().selectList(paramMap);
    }

    @Override
    public long update(T entity) {
        return getBaseDao().update(entity);
    }

    @Override
    public T get(Serializable id) {
        return getBaseDao().get(id);
    }

    @Override
    public void insertSelective(T entity) {
        getBaseDao().insert(entity);
    }

    @Override
    public void delete(T entiy) {
        getBaseDao().delete(entiy);
    }

    @Override
    public void delete(Serializable id) {
        getBaseDao().delete(id);

    }


    @Override
    public T queryById(String id) {
        return getBaseDao().selectByPrimaryKey(id);
    }

    @Override
    public void add(T entity) throws Exception {
        getBaseDao().insert(entity);
    }

    @Override
    public int deleteById(String id) throws Exception {
        return getBaseDao().deleteByPrimaryKey(id);
    }

    @Override
    public int deleteFalse(String id) throws Exception {
        return getBaseDao().deleteByIdFalse(id);
    }

    @Override
    public int updateByIdSelective(T entity) throws Exception {
        return getBaseDao().updateByPrimaryKeySelective(entity);
    }

    @Override
    public int updateById(T entity) throws Exception {
        return getBaseDao().updateByPrimaryKey(entity);
    }

    @Override
    public List<T> queryList(Map<String, Object> map, int pageNum, int pageSize) throws Exception {
        PageHelper.startPage(pageNum, pageSize);
        return getBaseDao().selectList(map);
    }

    @Override
    public T queryOne(Map<String, Object> map) {
        return getBaseDao().selectOne(map);
    }

    @Override
    public void addSelective(T entity) throws Exception {
        getBaseDao().insertSelective(entity);

    }





}
