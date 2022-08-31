package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();

    }

    @Override
    public void run() {
        int i = 0;
        char[] processCharArray = {'-', '\\', '|', '/'};

        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\r Loading: " + processCharArray[i++]);
            if (i == processCharArray.length) {
                i = 0;
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
