package sn.sv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import sn.sv.config.AppShutdownSignalHandler;
import sun.misc.Signal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EnableDiscoveryClient
@SpringBootApplication
public class ServerApplication {
    private static Logger logger = LoggerFactory.getLogger(ServerApplication.class);

    public static void main(String[] args) {
        logger.warn("Application start");

        ConfigurableApplicationContext context = SpringApplication.run(ServerApplication.class, args);

        AppShutdownSignalHandler shutdownHandler = context.getBean(AppShutdownSignalHandler.class);
        Signal.handle(new Signal("TERM") ,shutdownHandler);
        Signal.handle(new Signal("INT") ,shutdownHandler);
        logger.warn("Application started");
    }
}
