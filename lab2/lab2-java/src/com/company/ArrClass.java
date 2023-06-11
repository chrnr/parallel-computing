package com.company;

public class ArrClass {
    private final int dim;
    private final int threadNum;
    public final int[] arr;

    public ArrClass(int dim, int threadNum) {
        this.dim = dim;
        arr = new int[dim];
        this.threadNum = threadNum;
        for (int i = 0; i < dim; i++) {
            arr[i] = i;
        }
        // Replace an element with -1
        arr[dim / 2] = -1;
    }

    public long partMin(int startIndex, int finishIndex) {
        long min = Long.MAX_VALUE;
        for (int i = startIndex; i < finishIndex; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }

    private long min = Long.MAX_VALUE;

    synchronized private long getMin() {
        while (getThreadCount() < threadNum) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return min;
    }

    synchronized public void collectMin(long min) {
        if (min < this.min) {
            this.min = min;
        }
    }

    private int threadCount = 0;

    synchronized public void incThreadCount() {
        threadCount++;
        notify();
    }

    private int getThreadCount() {
        return threadCount;
    }

    public long threadMin() {
        ThreadMin[] threadMins = new ThreadMin[threadNum];

        // Determine the index boundaries of the array parts for each thread
        int partSize = dim / threadNum;

        for (int i = 0; i < threadNum; i++) {
            int startIndex = i * partSize;
            int finishIndex = (i == threadNum - 1) ? dim : startIndex + partSize;
            threadMins[i] = new ThreadMin(startIndex, finishIndex, this);
            threadMins[i].start();
        }

        return getMin();
    }
}