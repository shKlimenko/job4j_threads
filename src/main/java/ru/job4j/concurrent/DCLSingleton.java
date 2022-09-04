package ru.job4j.concurrent;

public final class DCLSingleton {

    private static DCLSingleton inst;

    public static DCLSingleton instOf() {
        if (inst == null) {
            synchronized (DCLSingleton.class) {
                if (inst == null) {
                    inst = new DCLSingleton();
                }
            }
        }
        return inst;
    }

    private DCLSingleton() {
    }

}
