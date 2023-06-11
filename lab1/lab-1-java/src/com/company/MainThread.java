package com.company;

public class MainThread extends Thread{
    private final int id;
    private final BreakThread breakThread;
    private final int step;

    public MainThread(int id, BreakThread breakThread, int step) {
        this.id = id;
        this.breakThread = breakThread;
        this.step = step;
    }

    @Override
    public void run() {
        long sum = 0;
        long count = 0;
        boolean isStop = false;
        do {
            sum += count * step;
            count++;
            isStop = breakThread.isCanBreak(id);
        } while (!isStop);
        System.out.println(id + " - " + sum + " - " + count);
    }
}
