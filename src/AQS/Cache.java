package AQS;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cache {
    private static Map<String,Object> map = new HashMap<>();
    private static ReentrantReadWriteLock rw1 = new ReentrantReadWriteLock();
    private static Lock r = rw1.readLock();
    private static Lock w = rw1.writeLock();

    //返回一个key对应的value
    public static final Object get(String key){
        r.lock();
        try {
            return map.get(key);
        }finally {
            r.unlock();
        }
    }
    //设置values
    public static final Object set(String key , Object value){
        w.lock();
        try {
            return map.put(key,value);
        }finally {
            w.unlock();
        }
    }

    //清空
    public static final void clear(){
        w.lock();
        try {
            map.clear();
        }finally {
            w.unlock();
        }
    }
}
