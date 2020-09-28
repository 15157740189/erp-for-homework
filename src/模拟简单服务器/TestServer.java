package 模拟简单服务器;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


public class TestServer {
    public static void main(String[] args) throws Exception{
        SimpleHttpServer.setPort(8080);
        SimpleHttpServer.setBasePath("C:\\Users\\lenovo\\Desktop\\gitstore\\test");
        InetAddress host = InetAddress.getLocalHost();
        String ip = host.getHostAddress();
        Socket s = new Socket("localhost",8080);
        //构建IO

        OutputStream os = s.getOutputStream();
        DataOutputStream toServer = new DataOutputStream(os);
        toServer.writeUTF("Index.html");
        InputStream is = s.getInputStream();
       // BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
        //向服务器端发送消息
       // bw.write("Index.html");
       // bw.flush();
        //读取服务器返回的消息
       // BufferedReader br = new BufferedReader(new InputStreamReader(is));
      //  String mess = br.readLine();
       DataInputStream inputStream = new DataInputStream(is);
        String header = inputStream.readUTF();
        System.out.println("服务器："+ header);
    }
}
