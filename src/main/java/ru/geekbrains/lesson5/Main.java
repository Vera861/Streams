package ru.geekbrains.lesson5;


public class Main {

    static final int SIZE = 10000000;
    static final int H = SIZE / 2;

    public static void main(String[] args) {

        firstMethod(create());
        secondMethod(create());

    }

    private static void firstMethod(float arr[]) {

        long timeStart = System.currentTimeMillis();
        math(arr, 0);
        System.out.println("Время выполнения первого метода:" + (System.currentTimeMillis() - timeStart));
    }

    private static void secondMethod(float arr[]) {

        float[] arr1 = new float[H];
        float[] arr2 = new float[SIZE - H];

        long timeStart = System.currentTimeMillis();

        Thread firstThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.arraycopy(arr, 0, arr1, 0, H);
                float[] a1 = math(arr1, 0);
                System.arraycopy(a1, 0, arr, 0, H);
            }

        });
        firstThread.start();

        Thread secondThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.arraycopy(arr, (SIZE - H), arr2, 0, H);
                float[] a2 = math(arr2, (SIZE - H));
                System.arraycopy(a2, 0, arr, (SIZE - H), H);
            }
        });
        secondThread.start();
        try {
            firstThread.join();
            secondThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Время выполнения второго метода:" + (System.currentTimeMillis() - timeStart));
    }

    private static float[] create() {
        float[] arr = new float[SIZE];
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1.0f;
        }
        return arr;
    }

    private static float[] math(float[] arr, int start) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + start / 5) * Math.cos(0.2f + start / 5)
                    * Math.cos(0.4f + start / 2));
            start++;
        }
        return arr;
    }
}

