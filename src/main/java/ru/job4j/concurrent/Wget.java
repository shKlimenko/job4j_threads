package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    public static void main(String[] args) throws InterruptedException {
        checkArgs(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }

    private static void checkArgs(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Missing or extra arguments. "
                    + "Arguments template is: URL SPEED_LIMIT (byte / sec)");
        }
        var regex = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
        var matcher = regex.matcher(args[0]);
        if (!matcher.find()) {
            throw new IllegalArgumentException("URL is not valid. "
                    + "Please use valid URL");
        }
        if (Integer.parseInt(args[1]) <= 0) {
            throw new IllegalArgumentException("Speed can't be zero or negative.");
        }
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            var timeBeforeDownload = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                var timeAfterDownload = System.currentTimeMillis();
                var timeInterval = timeAfterDownload - timeBeforeDownload;
                downloadData += bytesRead;
                if (downloadData > speed && timeInterval < 1000) {
                    Thread.sleep(1000 - timeInterval);
                    downloadData = 0;
                }
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                timeBeforeDownload = System.currentTimeMillis();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}