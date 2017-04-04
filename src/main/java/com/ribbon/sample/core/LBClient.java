package com.ribbon.sample.core;

import com.netflix.client.AbstractLoadBalancerAwareClient;
import com.netflix.client.RequestSpecificRetryHandler;
import com.netflix.client.config.IClientConfig;
import com.netflix.client.config.IClientConfigKey;
import com.netflix.loadbalancer.ILoadBalancer;

public class LBClient extends AbstractLoadBalancerAwareClient<LBRequest, LBResponse> {


    protected HttpClient httpClient;

    public LBClient(ILoadBalancer loadbalance, IClientConfig config) {
        this(new DefaultHttpClient(), loadbalance, config);
    }

    public LBClient(HttpClient httpClient, ILoadBalancer loadbalance, IClientConfig config) {
        super(loadbalance, config);
        if (config != null) {
            httpClient.setConnectTimeout(config.getPropertyAsInteger(IClientConfigKey.Keys.ConnectTimeout, 3000));
            httpClient.setReadTimeout(config.getPropertyAsInteger(IClientConfigKey.Keys.ReadTimeout, 3000));
        }
        this.httpClient = httpClient;
    }

    @Override
    public LBResponse execute(LBRequest request, IClientConfig configOverride) throws Exception {
        if (configOverride != null) {
            httpClient.setConnectTimeout(configOverride.getPropertyAsInteger(IClientConfigKey.Keys.ConnectTimeout, httpClient.getConnectTimeout()));
            httpClient.setReadTimeout(configOverride.getPropertyAsInteger(IClientConfigKey.Keys.ReadTimeout, httpClient.getReadTimeout()));            
        }
        RpcResult result = httpClient.execute(request.getUri().toString(), request.getMethod(), request.getHeaders(), request.getArgs());
        return new LBResponse(request.getUri(), result);
    }

    @Override
    public RequestSpecificRetryHandler getRequestSpecificRetryHandler(LBRequest request, IClientConfig clientConfig) {
        if (this.okToRetryOnAllOperations) {
            return new RequestSpecificRetryHandler(true, true, this.getRetryHandler(), clientConfig);
        }

        if (request.getMethod() != HttpMethod.GET) {
            return new RequestSpecificRetryHandler(true, false, this.getRetryHandler(), clientConfig);
        } else {
            return new RequestSpecificRetryHandler(true, true, this.getRetryHandler(), clientConfig);
        }
    }

}
