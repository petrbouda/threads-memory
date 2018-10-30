package pbouda.threads.memory;

import java.lang.management.MemoryUsage;

public class Utils {

    public static void printMemoryUsage(MemoryUsage memory, int count) {
        System.out.println("INIT: " + toMB(memory.getInit()) + " | USED: " + toMB(memory.getUsed()) +
                " | COMMITTED: " + toMB(memory.getCommitted()) + " | MAX: " + toMB(memory.getMax()) +
                " | THREADS: " + count);
    }

    private static String toMB(long bytes) {
        return (bytes >> 20) + " MB";
    }
}
