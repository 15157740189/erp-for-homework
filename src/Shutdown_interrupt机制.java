public class Shutdown_interrupt机制 {
    public static void main(String[] args) {
        Runner one =new Runner();
        Thread countThread = new Thread(one , "countThread");
        countThread.start();
        //睡眠一秒,main线程对countThread进行中断,使countThread能够感知中断而结束
        SleepUtils.second(1);
        countThread.interrupt();
        Runner two =new Runner();
        countThread = new Thread(two , "countThread");
        countThread.start();
        //睡眠一秒 main线程对Runner two 进行取消，使countThread能够感知on为false而结束
        SleepUtils.second(1);
        two.cancel();
    }

  private static class Runner implements Runnable{
        private long i;
        private volatile boolean on = true;
      @Override
      public void run() {
          while (on && !Thread.currentThread().isInterrupted()){
              i++;
          }
          System.out.println("Count i = " + i);
      }
      public void cancel(){
          on = false;
      }
  }
}
