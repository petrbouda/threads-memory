package pbouda.threads.memory;

import sun.management.ManagementFactoryHelper;
import java.lang.management.MemoryMXBean;
import java.util.ArrayList;
import java.util.List;

public class SimpleThreadsGenerator {

    public static void main(String[] args) {
        MemoryMXBean memoryMXBean = ManagementFactoryHelper.getMemoryMXBean();

        List<Thread> threads = new ArrayList<>();
        while (true) {
            threads.add(new Thread());
            Utils.printMemoryUsage(memoryMXBean.getHeapMemoryUsage(), threads.size());
            Utils.printMemoryUsage(memoryMXBean.getNonHeapMemoryUsage(), threads.size());
        }
    }
}
