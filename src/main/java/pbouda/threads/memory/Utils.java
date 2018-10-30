package pbouda.threads.memory;

import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class Utils {

    public static void printMemoryUsage(MemoryMXBean memoryMXBean, int count) {
        MemoryUsage heap = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeap = memoryMXBean.getNonHeapMemoryUsage();

        System.out.println(
                "INIT: " + toMB(heap.getInit()) + " | " + toMB(nonHeap.getInit()) +
                        " | USED: " + toMB(heap.getUsed()) + " | " + toMB(nonHeap.getUsed()) +
                        " | COMMITTED: " + toMB(heap.getCommitted()) + " | " + toMB(nonHeap.getCommitted()) +
                        " | MAX: " + toMB(heap.getMax()) + " | " + toMB(nonHeap.getMax()) +
                        " | THREADS: " + count);
    }

    private static String toMB(long bytes) {
        return (bytes >> 20) + " MB";
    }
}
