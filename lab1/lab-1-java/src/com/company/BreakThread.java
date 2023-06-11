package com.company;

public class BreakThread implements Runnable {
    private final boolean[] canBreak;

    public BreakThread(int numberOfThreads) {
        canBreak = new boolean[numberOfThreads];
    }

    @Override
    public void run() {
        for (int i = 0; i < canBreak.length; i++) {
            try {
                Thread.sleep((i + 1) * 10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            canBreak[i] = true;
        }
    }

    synchronized public boolean isCanBreak(int id) {
        return canBreak[id - 1];
    }
}
