/**
  * @Author kana-cr
  * @Date  2020/9/2 16:32
  * @Param
  * @return
 * 用来阅读sync的monitorenter与monitorexit
  **/
public class synchronizer {
    public static void main(String[] args) {
        synchronized (synchronizer.class){
            m();
        }
    }
    public static synchronized void m(){

    }
}
