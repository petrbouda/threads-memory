# Java Threads and Memory

```
java -Xmx512m \
--add-exports=java.management/sun.management=threads.memory \
--module-path target/classes \
--module threads.memory/pbouda.threads.memory.SimpleThreadsGenerator
```

### Changes of Stack Trace size
```
ThreadStackSize	        Thread Stack Size (in Kbytes)
VMThreadStackSize	    Non-Java Thread Stack Size (in Kbytes)
CompilerThreadStackSize	Compiler Thread Stack Size (in Kbytes)
```

`-Xss and -XX:ThreadStackSize`

- Same functionality
- The former one is default for the majority of JVMs
- The latter one is flag only for Hotspot

```
jcmd <pid> VM.native_memory [summary | detail | baseline | summary.diff | detail.diff | shutdown] [scale= KB | MB | GB]

summary	        Print a summary aggregated by category.
detail	        Print memory usage aggregated by category
                Print virtual memory map
                Print memory usage aggregated by call site
baseline	    Create a new memory usage snapshot to diff against.
summary.diff	Print a new summary report against the last baseline.
detail.diff	    Print a new detail report against the last baseline.
shutdown	    Shutdown NMT.
```

### Logging info about Threads

```
Thread (reserved=15427KB, committed=15427KB)
       (thread #15)
       (stack: reserved=15360KB, committed=15360KB)
       (malloc=50KB #84)
       (arena=18KB #28)
```

- 15 threads == 15MB COMMITTED Memory
- `malloc=50KB` the real memory?
- https://docs.oracle.com/javase/9/docs/api/java/lang/management/MemoryUsage.html

```
represents the amount of memory (in bytes) that is guaranteed to be available 
for use by the Java virtual machine. The amount of committed memory may change 
over time (increase or decrease). The Java virtual machine may release memory 
to the system and committed could be less than init. committed will always be 
greater than or equal to used.
``` 

```
-XX:NativeMemoryTracking=[off | summary | detail]

off	    NMT is turned off by default.
summary	Only collect memory usage aggregated by subsystem.
detail	Collect memory usage by individual call sites.
```

```
$ java -XX:+UnlockDiagnosticVMOptions -XX:NativeMemoryTracking=summary -XX:+PrintNMTStatistics -version

Native Memory Tracking:

Total: reserved=5724944KB, committed=354436KB
-                 Java Heap (reserved=4194304KB, committed=262144KB)
                            (mmap: reserved=4194304KB, committed=262144KB)

-                     Class (reserved=1056864KB, committed=4576KB)
                            (classes #426)
                            (  instance classes #364, array classes #62)
                            (malloc=96KB #454)
                            (mmap: reserved=1056768KB, committed=4480KB)
                            (  Metadata:   )
                            (    reserved=8192KB, committed=4096KB)
                            (    used=2839KB)
                            (    free=1257KB)
                            (    waste=0KB =0.00%)
                            (  Class space:)
                            (    reserved=1048576KB, committed=384KB)
                            (    used=270KB)
                            (    free=114KB)
                            (    waste=0KB =0.00%)

-                    Thread (reserved=15427KB, committed=15427KB)
                            (thread #15)
                            (stack: reserved=15360KB, committed=15360KB)
                            (malloc=50KB #84)
                            (arena=18KB #28)

-                      Code (reserved=247722KB, committed=7582KB)
                            (malloc=34KB #374)
                            (mmap: reserved=247688KB, committed=7548KB)

-                        GC (reserved=207299KB, committed=61379KB)
                            (malloc=17823KB #2090)
                            (mmap: reserved=189476KB, committed=43556KB)

-                  Compiler (reserved=306KB, committed=306KB)
                            (malloc=3KB #43)
                            (arena=303KB #11)

-                  Internal (reserved=545KB, committed=545KB)
                            (malloc=505KB #1063)
                            (mmap: reserved=40KB, committed=40KB)

-                    Symbol (reserved=1592KB, committed=1592KB)
                            (malloc=1072KB #2143)
                            (arena=520KB #1)

-    Native Memory Tracking (reserved=130KB, committed=130KB)
                            (malloc=4KB #50)
                            (tracking overhead=126KB)

-               Arena Chunk (reserved=675KB, committed=675KB)
                            (malloc=675KB)

-                   Logging (reserved=4KB, committed=4KB)
                            (malloc=4KB #177)

-                 Arguments (reserved=17KB, committed=17KB)
                            (malloc=17KB #463)

-                    Module (reserved=59KB, committed=59KB)
                            (malloc=59KB #1026)
```

```
$ java -Xlog:thread+os -version

[0.026s][info][os,thread] Thread attached (tid: 8963, pthread id: 4349997056).
[0.037s][info][os,thread] Thread started (pthread id: 123145494302720, attributes: stacksize: 1024k, guardsize: 4k, detached).
[0.037s][info][os,thread] Thread is alive (tid: 20227, pthread id: 123145494302720).
[0.037s][info][os,thread] Thread started (pthread id: 123145495363584, attributes: stacksize: 1024k, guardsize: 4k, detached).
.
.
[0.124s][info][os,thread] Thread started (pthread id: 123145508093952, attributes: stacksize: 1024k, guardsize: 4k, detached).
[0.124s][info][os,thread] Thread is alive (tid: 22275, pthread id: 123145508093952).
openjdk version "11" 2018-09-25
OpenJDK Runtime Environment 18.9 (build 11+28)
OpenJDK 64-Bit Server VM 18.9 (build 11+28, mixed mode)
[0.126s][info][os,thread] JavaThread detaching (tid: 8963).
[0.126s][info][os,thread] Thread attached (tid: 8963, pthread id: 4349997056).
[0.126s][info][os,thread] Thread finished (tid: 22275, pthread id: 123145508093952).
[0.126s][info][os,thread] Thread finished (tid: 12035, pthread id: 123145497485312).
[0.126s][info][os,thread] Thread finished (tid: 18947, pthread id: 123145498546176).
[0.126s][info][os,thread] Thread finished (tid: 11011, pthread id: 123145495363584).
[0.126s][info][os,thread] JavaThread exiting (tid: 17159).
[0.126s][info][os,thread] Thread finished (tid: 17159, pthread id: 123145502789632).
[0.126s][info][os,thread] JavaThread exiting (tid: 8963).
[0.139s][info][os,thread] Thread finished (tid: 12547, pthread id: 123145499607040).

```

### How many threads can I spawn?
RAM = 2BM
=> Heap 512MB
=> Off-Heap 300MB other processes
=> 1.2GB for spawning threads => 1200 Threads? no more

#### VSZ (virtual memory size)
- address space which is accessible by the process
- does not mean that there is enough physical memory

#### RES (resident set size)
- memory actually used by the process
- shows real memory usage 

![htop example](htop.png)

### Cost of threads
- Stack Memory - 1MB by default, 
- Context-Switches
- Safe-pointing
- GC Roots

- `pstree` program
- processes and threads (from Java 10 Java Threads are properly named) 

### TODOs
- How it works in docker?
- How to limit non-heap size for stacks? (-XX:MaxRAM ?)
- Create a small program and compare outputs from:
    - htop
    - -XX:+PrintNMTStatistics
    - JCMD

### Links
- https://shipilev.net/jvm-anatomy-park/12-native-memory-tracking/

### Interesting Snippets

```
-XX:VMThreadStackSize=256:

-Total: reserved=1165843KB, committed=29571KB
+Total: reserved=1163539KB, committed=27267KB

--                    Thread (reserved=4419KB, committed=4419KB)
+-                    Thread (reserved=2115KB, committed=2115KB)
                             (thread #8)
-                            (stack: reserved=4384KB, committed=4384KB)
+                            (stack: reserved=2080KB, committed=2080KB)
                             (malloc=26KB #42)
                             (arena=9KB #16)
```

```
-XX:MaxRAM=n	

Sets the maximum amount of memory used by the JVM to n, where n may be expressed
in terms of megabytes 100m or gigabytes 2g.
```