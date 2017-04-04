package com.ribbon.sample;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ribbon.sample.LBService;
import com.ribbon.sample.core.HttpMethod;
import com.ribbon.sample.core.LBRequest;
import com.ribbon.sample.core.LBResponse;

import static junit.framework.Assert.*;

public class LBServiceTest {

    public LBServiceTest() {
    }
    

    @Test
    public void execute()  {
        
        Map<String, Collection<String>> headers = new HashMap<>();
        headers.put("User-Agent", Arrays.asList("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.101 Safari/537.36"));;

        LBService lbService = new LBService("sample-client");
        LBRequest request = new LBRequest("/data/cityinfo/101010100.html", HttpMethod.GET, headers, null);
        for (int i=0; i<10; i++) {
            LBResponse response = lbService.execute(request);
            assertEquals(200, response.getStatus());
            assertNotNull(response.getPayload());
            assertTrue(new String(response.getPayload(), Charset.forName("utf-8")).contains("weatherinfo"));
        } 
    }
}