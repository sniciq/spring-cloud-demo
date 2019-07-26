package sn.iface;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import sn.common.bean.HelloBean;

@FeignClient(value = "CLOUD-SERVICE")
public interface HelloService {
    @RequestMapping(value = "/hello")
    HelloBean hello();
}
