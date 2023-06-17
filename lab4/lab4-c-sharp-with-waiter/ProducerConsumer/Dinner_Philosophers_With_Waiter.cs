using System;
using System.Threading;

class Dinner_Philosophers_With_Waiter
{
    static Semaphore[] forks = new Semaphore[5];
    static Semaphore waiter = new Semaphore(2, 2);

    static void Main()
    {
        for (int i = 0; i < 5; i++)
        {
            forks[i] = new Semaphore(1, 1);
        }

        for (int i = 0; i < 5; i++)
        {
            int iCopy = i; // to capture the loop variable correctly
            new Thread(() => Philosopher(iCopy)).Start();
        }
    }

    static void Philosopher(int id)
    {
        int idLeftFork, idRightFork;

        if (id == 0)
        {
            idRightFork = id;
            idLeftFork = (id + 1) % 5;
        }
        else
        {
            idLeftFork = id;
            idRightFork = (id + 1) % 5;
        }

        for (int i = 0; i < 10; i++)
        {
            Console.WriteLine($"Philosopher {id} thinking {i} time");

            waiter.WaitOne();

            forks[idRightFork].WaitOne();
            Console.WriteLine($"Philosopher {id} took right fork");

            forks[idLeftFork].WaitOne();
            Console.WriteLine($"Philosopher {id} took left fork");

            Console.WriteLine($"Philosopher {id} eating {i} time");

            forks[idLeftFork].Release();
            Console.WriteLine($"Philosopher {id} put left fork");

            forks[idRightFork].Release();
            Console.WriteLine($"Philosopher {id} put right fork");

            waiter.Release();
        }
    }
}
