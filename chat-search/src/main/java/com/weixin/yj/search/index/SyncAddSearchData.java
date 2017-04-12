package com.weixin.yj.search.index;


import com.weixin.yj.search.ESHelper;
import com.weixin.yj.search.setting.IndexData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.SoftReference;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by LDB on 15-11-27.
 */
public class SyncAddSearchData {
    private static final Logger logger = LoggerFactory.getLogger(SyncAddSearchData.class);
    BlockingQueue<SoftReference<IndexData>> records = new LinkedBlockingQueue<SoftReference<IndexData>>(1000);
    private Thread storeThread = null;
    static class SingletonHolder {
        static SyncAddSearchData instance = new SyncAddSearchData();
    }

    public static SyncAddSearchData getInstance() {
        return SingletonHolder.instance;
    }

    private SyncAddSearchData() {
        startStoreThread();
    }
    public void addQueue(IndexData item) {
        SoftReference<IndexData> softReference = new SoftReference<IndexData>(item);
        records.offer(softReference);
    }
    private void startStoreThread() {

        storeThread = new Thread(new Runnable() {


            public final void run() {
                try {
                    while (true) {
                        try {
                            SoftReference<? extends IndexData> ref = records.poll(2, TimeUnit.SECONDS);
                            if (ref != null) {
                                IndexData data = ref.get();
                                try {
                                    ESHelper.getYunInstance().addDocDirect(data.getIndex(),data.getType(),data.getFields(),data.isRefresh());
                                } catch (Exception e) {
                                    logger.error("存索引错误" + e);
                                    continue;
                                }
                            }
                        } catch (Exception ex) {
                            logger.error("error", ex);
                            Thread.sleep(1000L);
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("error", e);
                } finally {
                }
            }
        });
        storeThread.setPriority(Thread.MIN_PRIORITY);

        storeThread.start();
    }
}
