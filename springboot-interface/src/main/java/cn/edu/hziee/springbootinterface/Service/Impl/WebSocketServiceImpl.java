package cn.edu.hziee.springbootinterface.Service.Impl;

import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/ws")
@Service
public class WebSocketServiceImpl {
    //静态变量，用来记录当前在线连接数。 应该把它设计成线程安全的
    private static int onlineCount=0;
    //concurrent 包的线程安全 Set，用来存放每个客户端对应的 WebSocketServiceImpl 对象
    private static CopyOnWriteArraySet<WebSocketServiceImpl>
    webSocketSet=new CopyOnWriteArraySet<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session){
        this.session=session;
        webSocketSet.add(this);
        addOnlineCount();
        System.out.println("有新连接加入，当前在线人数为："+getOnlineCount());
        try {
            sendMessage("有新连接加入");
        }catch (IOException e){
            System.out.println("IO异常");
            //e.printStackTrace();
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        subOnlineCount();
        System.out.println("有一连接关闭，当前在线人数为："+getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送的消息
     */
    @OnMessage
    public void onMessage(String message){
        System.out.println("来自客户端的消息："+message);
        //群发消息
        for (WebSocketServiceImpl item:webSocketSet) {
           try {
              // String username=item.session.getUserPrincipal().getName();
              // System.out.println(username);
               item.sendMessage(message);
           }catch (IOException e){
              // System.out.println("IO异常");
               e.printStackTrace();
           }
        }
    }

    @OnError
    public void onError(Session session,Throwable throwable){
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

    /**
     *发送消息
     *@param message 客户端消息
     * @throws IOException
     */
    private void sendMessage(String message)throws IOException {
    this.session.getBasicRemote().sendText(message);
    }


    //返回在线数
  private static synchronized int getOnlineCount () {
        return onlineCount;
  }
   //当连接人数增加时
   private static synchronized void addOnlineCount () {
       WebSocketServiceImpl.onlineCount++;
   }
   //当连接人数减少时
   private static synchronized void subOnlineCount () {
       WebSocketServiceImpl.onlineCount--;
   }

            }
