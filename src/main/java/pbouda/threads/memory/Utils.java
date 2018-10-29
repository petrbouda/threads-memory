package pbouda.threads.memory;

import java.lang.management.MemoryUsage;

public class Utils {

    public static void printHeapMemoryUsage(MemoryUsage memory) {
        System.out.println("INIT: " + toMB(memory.getInit()) + " | USED: " + toMB(memory.getUsed()) +
                " | COMMITTED: " + toMB(memory.getCommitted()) + " | MAX: " + toMB(memory.getMax()));
    }

    private static String toMB(long bytes) {
        return (bytes >> 20) + " MB";
    }
}
