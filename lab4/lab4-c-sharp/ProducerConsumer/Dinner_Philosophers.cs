using System;
using System.Threading;

class Dinner_Philosophers
{
    static SemaphoreSlim[] forks = new SemaphoreSlim[5]
    {
        new SemaphoreSlim(1, 1),
        new SemaphoreSlim(1, 1),
        new SemaphoreSlim(1, 1),
        new SemaphoreSlim(1, 1),
        new SemaphoreSlim(1, 1)
    };

    static void Main()
    {
        Thread[] philosophers = new Thread[5];
        for (int i = 0; i < philosophers.Length; i++)
        {
            int id = i;
            philosophers[i] = new Thread(() => RunPhilosopher(id));
            philosophers[i].Start();
        }
        foreach (Thread philosopher in philosophers)
        {
            philosopher.Join();
        }
    }

    static void RunPhilosopher(int id)
    {
        int leftFork = id;
        int rightFork = (id + 1) % 5;

        // Philosopher 0 picks up the right fork first, the rest pick up the left fork first
        if (id == 0)
        {
            var temp = leftFork;
            leftFork = rightFork;
            rightFork = temp;
        }

        for (int i = 0; i < 10; i++)
        {
            Console.WriteLine($"Philosopher {id} is thinking for the {i + 1} time.");
            forks[leftFork].Wait();
            Console.WriteLine($"Philosopher {id} took the left fork.");
            forks[rightFork].Wait();
            Console.WriteLine($"Philosopher {id} took the right fork.");

            Console.WriteLine($"Philosopher {id} is eating for the {i + 1} time.");

            forks[rightFork].Release();
            Console.WriteLine($"Philosopher {id} put down the right fork.");
            forks[leftFork].Release();
            Console.WriteLine($"Philosopher {id} put down the left fork.");
        }
    }
}

