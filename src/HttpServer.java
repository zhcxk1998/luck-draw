import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

public class HttpServer {

    /**
     * WEB_ROOT是HTML和其它文件存放的目录. 这里的WEB_ROOT为工作目录下的webroot目录
     */
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

    // 关闭服务命令
//    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
    private ArrayList<String> userInfos = new ArrayList<>();
    private ArrayList<String> userIps = new ArrayList<>();
    private static int index;

    public void setUserIp(String ip) {
        System.out.println("开始setUserIp");
        String[] prize = {"立刻脱单", "概率论满分", "猛瘦10斤",
                "C++满分", "马上有钱", "离散满分", "颜值爆表", "Java满分"};
        double deg = Math.floor(Math.random() * 360);
//        System.out.println("ip是" + ip.replace("/", ""));
        if (!this.userIps.contains(ip.replace("/", ""))) {
            userIps.add(ip.replace("/", ""));
            setUserInfos(ip.replace("/", ""), deg + "", prize[(int) Math.floor(deg / 45)]);
        }
//        System.out.println(userIps.size());
//        for (String item : userIps) {
////            System.out.println(item);
//            String[] prize = {"立刻脱单", "概率论满分", "猛瘦10斤",
//                    "C++满分", "马上有钱", "离散满分", "颜值爆表", "Java满分"};
//            double deg = Math.floor(Math.random() * 360);
//            setUserInfos(item, deg + "", prize[(int) Math.floor(deg / 45)]);
//        }
    }

    public void setUserInfos(String ip, String deg, String prize) {
//        if (!this.userInfos.contains(ip.replace("/", "")))
        this.userInfos.add("{\"ip\":\"" + ip + "\",\"deg\":\"" + deg + "\",\"prize\":\"" + prize + "\"}");

//        System.out.println(ip);
//        System.out.println(deg);
//        System.out.println(prize);
//        System.out.println(userInfos.size());

//        for (String item : userInfos)
//            System.out.println(item);
    }

    public ArrayList<String> getUserInfos() {
        return userInfos;
    }

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        //等待连接请求
        server.await();
    }

    public void await() {
        ServerSocket serverSocket = null;
        int port = 80;
        try {
            //服务器套接字对象
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        // 循环等待一个请求
        while (true) {
            Socket socket = null;
            InputStream input = null;
            OutputStream output = null;
            try {
                //等待连接，连接成功后，返回一个Socket对象
                socket = serverSocket.accept();
                input = socket.getInputStream();
                output = socket.getOutputStream();

                // 创建Request对象并解析
                Request request = new Request(input);
                request.parse();
                // 检查是否是关闭服务命令
//                if (request.getUri().equals(SHUTDOWN_COMMAND)) {
//                    break;
//                }
//                System.out.println(socket.getPort());
//                System.out.println(socket.getInetAddress());
//                System.out.println(socket.getLocalAddress());
//                System.out.println(socket.getLocalPort());

                // 创建 Response 对象
//                String[] prize = {"立刻脱单", "概率论满分", "猛瘦10斤",
//                        "C++满分", "马上有钱", "离散满分", "颜值爆表", "Java满分"};
//                double deg = Math.floor(Math.random() * 360);
                setUserIp(socket.getInetAddress() + "");
//                System.out.println("{\"ip\":\"" + socket.getInetAddress()+"" + "\",\"deg\":\"" + String.valueOf(deg) + "\",\"prize\":\""+prize[(int) Math.floor(deg / 45)]+"\"}");
//                setUserInfos(socket.getInetAddress()+"");
                Response response = new Response(output);
                response.setUserInfo(this.userInfos);
                response.setMyInfo(this.userInfos);
                response.setRequest(request);
                response.sendStaticResource();

                // 关闭 socket 对象
                socket.close();

            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}