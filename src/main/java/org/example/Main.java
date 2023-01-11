package org.example;

import java.util.Arrays;

public class Main {
    static final int SIZE = 10;
    static final int HALF = SIZE / 2;

    public static void main(String[] args) {
        float [] arr = new float [SIZE];
        for (int i = 0; i < SIZE; i++) {
            arr[i] = i;
        }
        long startNoThreads = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin( 0.2f + i / 5) * Math.cos( 0.2f + i / 5) *
            Math . cos ( 0.4f + i / 2));
        }
        long finishNoThreads = System.currentTimeMillis();
        System.out.println("Elspsed witnout threads: " + (finishNoThreads - startNoThreads));
        long startWithThreads = System.currentTimeMillis();
        System.out.println(Arrays.toString(arr));
        for (int i = 0; i < SIZE; i++) {
            arr[i] = i;
        }
        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];
        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < a1.length; i++) {
                a1[i] = (float) (a1[i] * Math.sin( 0.2f + i / 5) * Math.cos( 0.2f + i / 5) *
                        Math . cos ( 0.4f + i / 2));
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0, j = HALF; i < a2.length; i++, j++) {
                a2[i] = (float) (a2[i] * Math.sin( 0.2f + j / 5) * Math.cos( 0.2f + j / 5) *
                        Math . cos ( 0.4f + j / 2));
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(a1, 0, arr, 0, HALF);
        System.arraycopy(a2, 0, arr, HALF, HALF);
        System.out.println("Elapsed with 2 threads: " + (System.currentTimeMillis() - startWithThreads));
        System.out.println(Arrays.toString(arr));
    }
}
