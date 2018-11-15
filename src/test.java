import java.util.*;

public class test {
    public static void main(String[] args) {
        String sr = HttpRequest.sendPost("http://localhost:8081/index.html", "key=123&v=456");
        System.out.println(sr);
    }
}
