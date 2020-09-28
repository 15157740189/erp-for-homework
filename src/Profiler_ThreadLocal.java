public class Profiler_ThreadLocal {
    // 第一次get()调用会初始化(如果没有调用set() )，每个线程会调用一次
    private static final ThreadLocal<Long> TIME_THREADLOCAL =new ThreadLocal<>();

    protected Long initialValue(){
        return System.currentTimeMillis();
    }
    public static final void begin(){
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static final long end(){
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }

    public static void main(String[] args) {
        Profiler_ThreadLocal.begin();
        SleepUtils.second(1);
        System.out.println("Cost：" + Profiler_ThreadLocal.end() + "mills");
    }
}
