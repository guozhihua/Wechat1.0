package com.weixin.utils.sys;

import net.sf.ehcache.Element;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCache;

import java.io.Serializable;


/**
 * Created by :Guozhihua
 * Date： 2016/10/31.
 */
public class MyEhcache implements Cache,Serializable{
    private EhCacheCache ehCacheCache =null;
    public MyEhcache(EhCacheCache ehCache) {
        this.ehCacheCache=ehCache;
    }
    @Override
    public void put(Object key, Object value) {
        ehCacheCache.put(key, value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object o, Object o1) {
        return ehCacheCache.putIfAbsent(o,o1);
    }

    @Override
    public void evict(Object o) {
        ehCacheCache.evict(o);
    }

    @Override
    public void clear() {
        ehCacheCache.clear();
    }

    @Override
    public String getName() {
        return ehCacheCache.getName();
    }

    @Override
    public Object getNativeCache() {
        return ehCacheCache.getNativeCache();
    }

    @Override
    public ValueWrapper get(Object o) {
        return  ehCacheCache.get(o);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return ehCacheCache.get(key, type);
    }

    public Object getObj(Object key){
        if(ehCacheCache.get(key)!=null){
          return   ehCacheCache.get(key).get();
        }
        return  null;
    }


    /**
     * 移除缓存
     * @param key
     */
    public void removeObj(Object key) {
        ehCacheCache.evict(key);
    }



    public void putObjHasExpireTime(Object key ,Object val ,int timeOutSenconds){
        Element element = new Element(key,val);
        element.setTimeToLive(timeOutSenconds);
        ehCacheCache.getNativeCache().put(element);
    }
    public void updateObjHasExpireTime(Object key ,Object val ,int timeOutSenconds){
        this.removeObj(key);
        this.putObjHasExpireTime(key,val,timeOutSenconds);
    }
}
