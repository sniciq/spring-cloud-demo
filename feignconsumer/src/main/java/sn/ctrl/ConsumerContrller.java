package sn.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.common.bean.HelloBean;
import sn.iface.HelloService;

@RestController
public class ConsumerContrller {
    @Autowired
    HelloService helloService;

    @RequestMapping(value = "consumer")
    public String consumer() {
        HelloBean bean = helloService.hello();
        return "id=" + bean.getId() + ", name=" + bean.getName();
    }
}
