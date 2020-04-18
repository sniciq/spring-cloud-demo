package sn.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.HttpService;
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

    @RequestMapping(value = "test")
    public String test() {
        new Thread(
            new Runnable() {
                @Override
                public void run() {
                    try {
                        HttpService http = new HttpService();
                        while (true) {
                            Thread.sleep(1000);
                            String s = http.doGet("http://localhost:9001/consumer", null);
                            System.out.println(s);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        ).run();
        return "OK";
    }
}
