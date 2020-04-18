package sn;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class Test {

    public static void main(String[] args) {
        Logger root = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.ERROR);

        try {
            HttpService http = new HttpService();
            while (true) {
                Thread.sleep(1000);
                String s = http.doGet("http://localhost:9001/consumer", null);
                root.error(s);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
