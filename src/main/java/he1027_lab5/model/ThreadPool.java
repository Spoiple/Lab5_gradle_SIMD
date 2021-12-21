package he1027_lab5.model;

import java.util.concurrent.*;

public class ThreadPool extends ThreadPoolExecutor {

    private static ThreadPool tp;

    private ThreadPool() {
        super(1, 1, 120, java.util.concurrent.TimeUnit.SECONDS, new ArrayBlockingQueue<>(200));
    }

    public static ThreadPool getThreadPool() {
        if (tp == null)
            tp = new ThreadPool();
        return tp;
    }
}
