package sn;

public class Test2 {

    public static void main(String[] args) {
        try {
            Runtime.getRuntime().exec("curl -X POST http://localhost:8081/pause");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
