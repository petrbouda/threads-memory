# threads-memory

```
java \
--add-exports=java.management/sun.management=threads.memory \
--module-path target/classes \
--module threads.memory/pbouda.threads.memory.SimpleThreadsGenerator
```

- Limit Heap Size
- Print # of threads
- Execute the threads (a stack is created?)
- Virtual Memory x MAX Heap x Used Heap