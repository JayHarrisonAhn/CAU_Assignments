package prob3;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ex2 {
    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static Data data = new Data();
    static int numOfReaders = 3;

    public static void main(String[] args) {
        Reader[] readers = new Reader[numOfReaders];
        for(int i=0; i<numOfReaders; i++) readers[i] = new Reader(i+1, data, readWriteLock);
        Writer writer = new Writer(1, data, readWriteLock);
    }
}

class Data {
    int data = 0;
}

class Reader extends Thread {
    int num;
    Data data;
    ReadWriteLock lock;
    Reader(int num, Data data, ReadWriteLock lock) {
        this.num = num;
        this.data = data;
        this.lock = lock;
        this.start();
    }

    @Override
    public void run() {
        while(true) {
            try {
                sleep((int)(Math.random() * 1000));
                System.out.println("Reader " + num + " try to read");
                lock.readLock().lock();
                System.out.println("Reader " + num + " : " + data.data);
                lock.readLock().unlock();
            } catch (InterruptedException e) {}
        }
    }
}

class Writer extends Thread {
    int num;
    Data data;
    ReadWriteLock lock;
    Writer(int num, Data data, ReadWriteLock lock) {
        this.num = num;
        this.data = data;
        this.lock = lock;
        this.start();
    }

    @Override
    public void run() {
        while(true) {
            try {
                sleep((int)(Math.random() * 1000));
                System.out.println("Writer " + num + " try to modify");
                lock.writeLock().lock();
                sleep((int)(Math.random() * 800));
                data.data = (int)(Math.random()*100)+1;
                System.out.println("Writer modified");
                lock.writeLock().unlock();
            } catch (InterruptedException e) {}
        }
    }
}