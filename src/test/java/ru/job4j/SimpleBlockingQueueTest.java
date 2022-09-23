package ru.job4j;

import org.junit.jupiter.api.Test;
import ru.job4j.synch.SingleLockList;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    public void add() throws InterruptedException {
        SimpleBlockingQueue<Integer> sbq = new SimpleBlockingQueue<>(2);
        List<Integer> rsl = new ArrayList<>();

        Thread first = new Thread(() -> {
            sbq.offer(1);
            sbq.offer(2);
            sbq.offer(3);
            sbq.offer(4);
        });
        Thread second = new Thread(() -> {
            rsl.add(sbq.poll());
            rsl.add(sbq.poll());
            rsl.add(sbq.poll());
        });
        first.start();
        second.start();
        first.join();
        second.join();

        assertThat(rsl).isEqualTo(List.of(1, 2, 3));
    }
}