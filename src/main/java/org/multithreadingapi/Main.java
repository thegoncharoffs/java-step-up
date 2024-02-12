package org.multithreadingapi;

/**
 * Попробуйте реализовать собственный пул потоков. В качестве аргументов конструктора пулу передается его емкость
 * (количество рабочих потоков). Как только пул создан, он сразу инициализирует и запускает потоки.
 * Внутри пула очередь задач на исполнение организуется через LinkedList<Runnable>.
 * При выполнении у пула потоков метода execute(Runnable r), указанная задача должна попасть в очередь исполнения,
 * и как только появится свободный поток – должна быть выполнена. Также необходимо реализовать метод shutdown(),
 * после выполнения которого новые задачи больше не принимаются пулом (при попытке добавить задачу можно
 * бросать IllegalStateException), и все потоки для которых больше нет задач завершают свою работу. Дополнительно можно
 * добавить метод awaitTermination() без таймаута, работающий аналогично стандартным пулам потоков
 */
public class Main {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(4);

        for (int i = 0; i < 10; i++) {
            final int j = i + 1;
            threadPool.execute(() -> {
                System.out.println("Start task " + j);
                try {
                    Thread.sleep(1000 + (int) (2000 * Math.random()));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("End task " + j);
            });
        }

        threadPool.shutdown();
        System.out.println("End of all tasks");
    }
}