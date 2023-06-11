package com.company;

import java.util.concurrent.*;

public class DinnerPhilosophers {
    private static final int NUM_PHILOSOPHERS = 5;
    private static final Semaphore[] forks = new Semaphore[NUM_PHILOSOPHERS];

    public static void main(String[] args) {
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new Semaphore(1);
        }

        Thread[] philosophers = new Thread[NUM_PHILOSOPHERS];
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            philosophers[i] = new Thread(new Philosopher(i));
            philosophers[i].start();
        }
    }

    static class Philosopher implements Runnable {
        private final int id;
        private final int leftFork;
        private final int rightFork;

        public Philosopher(int id) {
            this.id = id;
            if (id == 0) {
                this.rightFork = id;
                this.leftFork = (id + 1) % NUM_PHILOSOPHERS;
            } else {
                this.leftFork = id;
                this.rightFork = (id + 1) % NUM_PHILOSOPHERS;
            }
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                think(i);

                try {
                    forks[leftFork].acquire();
                    System.out.println("Philosopher " + id + " took left fork");

                    forks[rightFork].acquire();
                    System.out.println("Philosopher " + id + " took right fork");

                    eat(i);

                    forks[rightFork].release();
                    System.out.println("Philosopher " + id + " put down right fork");

                    forks[leftFork].release();
                    System.out.println("Philosopher " + id + " put down left fork");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }
        }

        private void think(int i) {
            System.out.println("Philosopher " + id + " is thinking for the " + (i + 1) + " time.");
        }

        private void eat(int i) {
            System.out.println("Philosopher " + id + " is eating for the " + (i + 1) + " time.");
        }
    }
}
