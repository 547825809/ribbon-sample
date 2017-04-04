package com.ribbon.sample;

import java.io.IOException;

import com.netflix.client.ClientException;
import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.config.ConfigurationManager;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.LoadBalancerBuilder;
import com.netflix.loadbalancer.NoOpPing;
import com.netflix.loadbalancer.RoundRobinRule;
import com.ribbon.sample.core.LBClient;
import com.ribbon.sample.core.LBRequest;
import com.ribbon.sample.core.LBResponse;
import com.ribbon.sample.exception.ConfigureException;
import com.ribbon.sample.exception.HttpInvokeException;

public class LBService {

    private LBClient client;  
    
    private ILoadBalancer getLoadBalance(IClientConfig config) {
        return LoadBalancerBuilder.newBuilder()
                .withClientConfig(config)
                .withPing(new NoOpPing())
                .withRule(new RoundRobinRule())
                //.withDynamicServerList(null)
                .withLoadBalancerExecutorRetryHandler(new DefaultLoadBalancerRetryHandler(0, 1, true))
                .buildDynamicServerListLoadBalancer();
    }
    
    public LBService(String clientKey) {
        try {
            ConfigurationManager.loadPropertiesFromResources(clientKey + ".properties");
        } catch (IOException e) {
            throw new ConfigureException(clientKey + ".properties is not found", e);
        }
        IClientConfig config = new DefaultClientConfigImpl();
        config.loadProperties(clientKey);
        client = new LBClient(getLoadBalance(config), config);
    }
    
    public LBResponse execute(LBRequest request) {
        try {
            return client.executeWithLoadBalancer(request);
        } catch (ClientException e) {
            throw new HttpInvokeException("fail to invoke uri='" + request.getUri() + "'", e);
        }
        
    }
    
}
