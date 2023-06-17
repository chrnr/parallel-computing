package com.company;

import java.util.concurrent.Semaphore;

public class DinnerPhilosophersWithWaiter {

    static Semaphore[] forks = new Semaphore[5];
    static Semaphore waiter = new Semaphore(2);

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            forks[i] = new Semaphore(1);
        }

        for (int i = 0; i < 5; i++) {
            final int id = i; // to correctly capture the loop variable
            new Thread(() -> philosopher(id)).start();
        }
    }

    public static void philosopher(int id) {
        int idLeftFork, idRightFork;

        if (id == 0) {
            idRightFork = id;
            idLeftFork = (id + 1) % 5;
        } else {
            idLeftFork = id;
            idRightFork = (id + 1) % 5;
        }

        for (int i = 0; i < 10; i++) {
            System.out.println("Philosopher " + id + " thinking " + i + " time");

            try {
                waiter.acquire();

                forks[idRightFork].acquire();
                System.out.println("Philosopher " + id + " took right fork");

                forks[idLeftFork].acquire();
                System.out.println("Philosopher " + id + " took left fork");

                System.out.println("Philosopher " + id + " eating " + i + " time");

                forks[idLeftFork].release();
                System.out.println("Philosopher " + id + " put left fork");

                forks[idRightFork].release();
                System.out.println("Philosopher " + id + " put right fork");

                waiter.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
