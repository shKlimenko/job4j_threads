package ru.job4j.io;

import java.io.*;

public final class SaveContent {
    private final File file;

    public SaveContent(File file) {
        this.file = file;
    }

    public void saveToFile(String content, File file) {
        try (BufferedWriter out = new BufferedWriter(
                new FileWriter(file))) {
            out.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
