package sn.sv.ctrl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sn.common.bean.HelloBean;

@RestController
public class HelloController {
    private final Logger logger = Logger.getLogger(getClass());

    @Value("${server.port}")
    private String port;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public HelloBean hello() {
        ServiceInstance instance = discoveryClient.getLocalServiceInstance();
        String info = "/hello, host:" + instance.getHost() + ",service_id=" + instance.getServiceId() +",port=" + port;
        logger.info(info);
        return new HelloBean(1, info);
    }
}
