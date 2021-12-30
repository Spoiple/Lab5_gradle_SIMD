package he1027_lab5;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

    private static ThreadPoolExecutor tp;
    private static final List<ThreadPoolExecutor> tps = new LinkedList<>();

    private ThreadPool() {
    }

    public static void addToPool(ThreadPoolExecutor t){
        if (!tps.contains(t))
            tps.add(t);
    }

    // TODO : ge inte ut threadpoolexecutor.
    //  extend threadpoolexecutor istället och override execute
    //  eller på annat vis se till att man kan via statisk metod
    //  direkt executa tasks
    public static ThreadPoolExecutor getThreadPool() {
        if (tp == null)
            tp = new ThreadPoolExecutor(1,1,120, TimeUnit.SECONDS, new ArrayBlockingQueue<>(200));
        return tp;
    }

    public static void shutDown() {
        if (tp != null)
            tp.shutdown();
        for (ThreadPoolExecutor tpe : tps) {
            tpe.shutdown();
        }
    }
}
