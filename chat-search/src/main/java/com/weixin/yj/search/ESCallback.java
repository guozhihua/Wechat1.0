package com.weixin.yj.search;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.Client;

public interface ESCallback {

    public BulkRequestBuilder doInES(Client client);
}
