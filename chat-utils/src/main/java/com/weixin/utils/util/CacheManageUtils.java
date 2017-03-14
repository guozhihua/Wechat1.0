package com.weixin.utils.util;

import com.weixin.utils.sys.CacheConfig;
import com.weixin.utils.sys.MyEhcache;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;

/**
 * Created by :Guozhihua
 * Date： 2016/10/31.
 */
    public class CacheManageUtils {
    public  static  final String CACHE_MANAGER = CacheConfig.CACHA_NAMAGER_ID;
    public static   MyEhcache myEhcache = null;
        private static EhCacheCacheManager cacheManager =null;
        private static synchronized EhCacheCacheManager getManagerInstance(){
            if(cacheManager==null){
                cacheManager=(EhCacheCacheManager)SpringBeanUtils.getBean(CACHE_MANAGER);
            }
            return cacheManager;
        }


    /**
     * 获取对应的缓存
     * @param cacheName
     * @return
     */
    public static MyEhcache getCache(String cacheName){
        if(myEhcache==null){
            EhCacheCache cacheCache= (EhCacheCache) getManagerInstance().getCache(cacheName);
            if(cacheCache!=null){
                myEhcache = new MyEhcache(cacheCache);
            }
        }
        return  myEhcache;
    }
}
