package com.company;

public class Main {
    public static void main(String[] args) {
        int numberOfThreads = 3;
        BreakThread breakThread = new BreakThread(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            new MainThread(i + 1, breakThread, i + 1).start();
        }

        new Thread(breakThread).start();
    }
}
