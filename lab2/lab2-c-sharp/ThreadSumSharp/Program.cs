using System;
using System.Threading;

namespace ThreadMinSharp
{
    class Program
    {
        private static readonly int dim = 10000000;
        private static readonly int threadNum = 1;

        private readonly Thread[] thread = new Thread[threadNum];

        static void Main(string[] args)
        {
            Program main = new Program();
            main.InitArr();
            Console.WriteLine(main.PartMin(0, dim));

            Console.WriteLine(main.ParallelMin());
            Console.ReadKey();
        }

        private int threadCount = 0;

        private long ParallelMin()
        {
            for (int i = 0; i < threadNum; i++)
            {
                int startIndex = i * (dim / threadNum);
                int finishIndex = (i + 1) * (dim / threadNum);
                thread[i] = new Thread(StarterThread);
                thread[i].Start(new Bound(startIndex, finishIndex));
            }

            lock (lockerForCount)
            {
                while (threadCount < threadNum)
                {
                    Monitor.Wait(lockerForCount);
                }
            }
            return min;
        }

        private readonly int[] arr = new int[dim];

        private void InitArr()
        {
            for (int i = 0; i < dim; i++)
            {
                arr[i] = i;
            }
            arr[1234567] = -1;
        }

        class Bound
        {
            public Bound(int startIndex, int finishIndex)
            {
                StartIndex = startIndex;
                FinishIndex = finishIndex;
            }

            public int StartIndex { get; set; }
            public int FinishIndex { get; set; }
        }

        private readonly object lockerForMin = new object();
        private void StarterThread(object param)
        {
            if (param is Bound)
            {
                long min = PartMin((param as Bound).StartIndex, (param as Bound).FinishIndex);

                lock (lockerForMin)
                {
                    CollectMin(min);
                }
                IncThreadCount();
            }
        }

        private readonly object lockerForCount = new object();
        private void IncThreadCount()
        {
            lock (lockerForCount)
            {
                threadCount++;
                Monitor.Pulse(lockerForCount);
            }
        }

        private long min = long.MaxValue;
        public void CollectMin(long min)
        {
            if (min < this.min)
                this.min = min;
        }

        public long PartMin(int startIndex, int finishIndex)
        {
            long min = long.MaxValue;
            for (int i = startIndex; i < finishIndex; i++)
            {
                if (arr[i] < min)
                    min = arr[i];
            }
            return min;
        }
    }
}
