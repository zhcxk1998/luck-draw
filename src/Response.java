import java.io.OutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;
import java.util.ArrayList;

public class Response {
    private static final int BUFFER_SIZE = 4096;
    Request request;
    OutputStream output;
    private String userInfo;
    private int index;
    ArrayList<String> userInfos = new ArrayList<>();

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void setUserInfo(ArrayList<String> userInfos) {
        this.userInfos = userInfos;
    }

    public void setMyInfo(ArrayList<String> userInfos) {
        this.index = userInfos.size();
    }

    public void sendStaticResource() throws IOException {
        System.out.println("index是："+index);
        for (String item : userInfos)
            System.out.println("userinfos是: " + item);
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        String json = "";
        String sep = "";
        String[] prize = {"立刻脱单", "概率论满分", "猛瘦10斤",
                "C++满分", "马上有钱", "离散满分", "颜值爆表", "Java满分"};
        try {
            if (request.getUri().equals("/getData")) {
                for (String item : userInfos) {
//                    System.out.println(item);
//                    output.write(item.getBytes());
//                    output.write(String.format(" " + String.valueOf(Math.floor(Math.random() * 360))+"\n").getBytes());
                    double deg = Math.floor(Math.random() * 360);
                    json += sep + item;
                    sep = ",";
                }
                output.write(String.format("[" + json + "]").getBytes());
//                output.write(this.userInfo.getBytes());
//                output.write(String.format(" " + String.valueOf(Math.floor(Math.random() * 360))).getBytes());
            } else if (!request.getUri().equals("/")) {
                // 这里跳转404
                output.write("emmm".getBytes());
            } else {
                File file = new File(HttpServer.WEB_ROOT, "/luckDraw.html");
                fis = new FileInputStream(file);
                output.write("HTTP/1.1 200 emmm\n".getBytes());
                output.write("Content-Type: text/html; charset=UTF-8\n\n".getBytes());
                while ((fis.read(bytes, 0, BUFFER_SIZE)) != -1) {
                    output.write(bytes, 0, BUFFER_SIZE);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            if (fis != null)
                fis.close();
        }
    }
}