package com.company;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        int storageSize = 7;
        int itemNumbers = 7;
        main.starter(storageSize, itemNumbers);
    }

    private void starter(int storageSize, int itemNumbers) {
        Manager manager = new Manager(storageSize);
        new Consumer(itemNumbers, manager);
        new Consumer(itemNumbers, manager);
        new Consumer(itemNumbers, manager);

        new Producer(itemNumbers, manager);
        new Producer(itemNumbers, manager);
        new Producer(itemNumbers, manager);
    }
}
