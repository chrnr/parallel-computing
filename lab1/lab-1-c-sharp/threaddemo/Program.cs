using System.Threading;
using System;

namespace threaddemo
{
    class Program
    {
        static void Main(string[] args)
        {
            (new Program()).Start();
        }

        private int numberOfThreads = 4;
        private int step = 2;
        private int[] stopTimes = new int[] { 5, 10, 15, 20 };
        private bool[] canStop;

        void Start()
        {
            canStop = new bool[numberOfThreads];
            for (int i = 0; i < numberOfThreads; i++)
            {
                int threadNumber = i;
                (new Thread(() => Calcuator(threadNumber))).Start();
            }

            (new Thread(Stoper)).Start();
        }

        void Calcuator(int threadNumber)
        {
            long sum = 0;
            long count = 0;
            do
            {
                sum += count * step;
                count++;
            } while (!canStop[threadNumber]);
            Console.WriteLine($"Thread {threadNumber}: Sum: {sum}, Count: {count}");
        }

        public void Stoper()
        {
            for (int i = 0; i < numberOfThreads; i++)
            {
                Thread.Sleep(stopTimes[i] * 1000);
                canStop[i] = true;
            }
        }
    }
}
