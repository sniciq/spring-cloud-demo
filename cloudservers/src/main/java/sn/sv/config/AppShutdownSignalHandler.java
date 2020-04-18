package sn.sv.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import sun.misc.Signal;
import sun.misc.SignalHandler;

@Component
public class AppShutdownSignalHandler implements SignalHandler {

    @Autowired
    private ApplicationContext context;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${management.port}")
    private String port;

    @Override
    public void handle(Signal signal) {
        logger.warn("Application handle SIGTERM");
        try {
            logger.warn("feign instance pause...");
            Runtime.getRuntime().exec("curl -X POST http://localhost:" + port + "/pause");
            Thread.sleep(30000);// 2*du ~ 2*du + 3 + interval
            logger.warn("feign instance paused.");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        ExitCodeGenerator generator = new ExitCodeGenerator() {
            public int getExitCode() {
                return 0;
            }
        };
        int exitCode = SpringApplication.exit(context, generator);
        logger.warn("Application exit with:{}", exitCode);
        System.exit(exitCode);
    }
}
