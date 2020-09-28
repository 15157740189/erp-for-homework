package AQS;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundQueue<T> {

    private Object[] items;
    //添加的下标，删除的下标和数组当前数量
    private int addIndex,removeIndex,count;
    private Lock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    public BoundQueue(int size) {
        items = new Object[size];
    }
    //添加一个元素 如果队列满则进入等待直到出现空位
    public void  add(T t) throws InterruptedException{
        lock.lock();
        try {
            while (count == items.length)
                notEmpty.await();
                items[addIndex] = t;
                if (++addIndex == items.length)
                    addIndex = 0;
                ++count;
                notEmpty.signal();
        }finally {
            lock.unlock();
        }
    }
    //从头部删除一个元素，如果队列为空则进入等待状态，直到元素出现
    @SuppressWarnings("unchecked")
    public T remove() throws InterruptedException{
        lock.lock();
        try {
            while (count == 0)
                notFull.await();
            Object x = items[removeIndex];
            if (++removeIndex == items.length)
                removeIndex = 0;
            --count;
            notFull.signal();
            return (T)x;
        }finally {
            lock.unlock();
        }
    }


}
