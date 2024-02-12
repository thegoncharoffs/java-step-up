package org.multithreadingapi;

import java.util.concurrent.*;

public class ThreadPool {

    private final Worker[] workers;

    private final LinkedBlockingQueue<Runnable> queue;

    private boolean isInterrupted = false;

    private class Worker extends Thread {
        public void run() {
            Runnable task;

            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            System.out.println("Queue waiting error");
                        }
                    }
                    task = queue.poll();
                    queue.notify();
                }

                try {
                    task.run();
                } catch (RuntimeException e) {
                    System.out.println("Thread pool is interrupted");
                }
            }
        }
    }

    public ThreadPool(int poolSize) {
        queue = new LinkedBlockingQueue<>();
        workers = new Worker[poolSize];

        for (int i = 0; i < poolSize; i++) {
            workers[i] = new Worker();
            workers[i].setDaemon(true);
            workers[i].start();
        }
    }

    public void execute(Runnable task) {
        if (isInterrupted) {
            throw new IllegalStateException("Can't execute because thread pool is interrupting");
        }

        synchronized (queue) {
            queue.add(task);
            queue.notify();
        }
    }

    public void shutdown() {
        isInterrupted = true;

        // Wait till queue is drained
        synchronized (queue) {
            while (!queue.isEmpty()) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    System.out.println("Queue waiting error");
                }
            }
        }

        // Interrupt workers
        for (Worker worker: workers) {
            worker.interrupt();
        }
    }
}
