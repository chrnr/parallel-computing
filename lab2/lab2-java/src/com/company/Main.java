package com.company;

public class Main {

    public static void main(String[] args) {
        int dim = 10000000;
        int threadNum = 2;
        ArrClass arrClass = new ArrClass(dim, threadNum);
        System.out.println(arrClass.threadMin());
    }
}