public class Interrupted {

    public static void main(String[] args) {
        // sleepThread不停的尝试睡眠
        Thread sleepThread = new Thread(new sleepRunner(),"SleepThread");
        sleepThread.setDaemon(true);
        // busyThread不停的工作
        Thread busyThread = new Thread(new busyRunner(),"BusyThread");
        busyThread.setDaemon(true);

        sleepThread.start();
        busyThread.start();
        //休眠五秒 让sleepThread和busyThread充分运行
        SleepUtils.second(5);
        sleepThread.interrupt();
        busyThread.interrupt();
        System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted());
        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted());
        SleepUtils.second(2);

    }
    static class sleepRunner implements Runnable{

        @Override
        public void run() {
            while (true){
                //此时中断会抛出中断异常 清除标识位
            SleepUtils.second(10);
            }
        }
    }

    static class busyRunner implements Runnable{

        @Override
        public void run() {
            while (true){
            }
        }
    }

}
