package utils;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by m on 11/2/16.
 */
public class Helpers {

    public static <T> Disposable subscribePrint(Observable<T> observable, String name) {
        return observable.subscribe((v) -> {
            System.out.println(name + " " + v);
        }, (e) -> {
            System.err.println("Error from " + name);
            System.err.println(e.getLocalizedMessage());
        }, () -> {
            System.out.println(name + " Completed");
        });
    }

    /**
     * Subscribes to an observable, printing all its emissions.
     * Blocks until the observable calls onCompleted or onError.
     */
    public static <T> void blockingSubscribePrint(Observable<T> observable, String name) {
        CountDownLatch latch = new CountDownLatch(1);
        subscribePrint(observable.doAfterTerminate(() -> latch.countDown()), name);
        try {
            latch.await();
        } catch (InterruptedException e) {
        }
    }

    public static <T, R> T onlyFirstArg(T t, R r) {
        return t;
    }

    public static <T> Consumer<Notification<? super T>> debug(String description) {
        return debug(description, "");
    }

    public static <T> Consumer<Notification<? super T>> debug(String description, String offset) {
        AtomicReference<String> nextOffset = new AtomicReference<>(">");
        return notification -> {

            if (notification.isOnNext()) {
                System.out.println(
                        Thread.currentThread().getName() +
                                "|" + description + ": " + offset +
                                nextOffset.get() + notification.getValue()
                );
            } else if (notification.isOnComplete()) {
                        System.out.println(
                                Thread.currentThread().getName() +
                                        "|" + description + ": " + offset +
                                        nextOffset.get() + "|"
                );
            } else if (notification.isOnError()) {
                System.err.println(
                        Thread.currentThread().getName() +
                                "|" + description + ": " + offset +
                                nextOffset.get() + " X " + notification.getError()
                );
            }
            nextOffset.getAndUpdate(p -> "-" + p);

        };

    }

    public static void schedule(Scheduler scheduler, int numberOfTasks, boolean onTheSameWorker){
        List<Integer> list = new ArrayList<>();
        AtomicInteger current = new AtomicInteger(0);
        Random random = new Random();
        Scheduler.Worker worker = scheduler.createWorker();
        Runnable addWork = () -> {
            synchronized (current){
                System.out.println("Add: "+Thread.currentThread().getName()+" "+current.get());
                list.add(random.nextInt(current.get()));
                System.out.println("End Add: "+ Thread.currentThread().getName()+" "+current.get());
            }
        };
        Runnable removeWork = () -> {
            synchronized (current){
                if (!list.isEmpty()){
                    System.out.println("Remove: "+Thread.currentThread().getName());
                    list.remove(0);
                    System.out.println("Remove End: "+Thread.currentThread().getName());
                }
            }
        };

        Runnable work = () ->{
            System.out.println(Thread.currentThread().getName());
            for (int i = 1; i <= numberOfTasks ; i++){
                current.set(i);
                System.out.println("Begin add");
                if (onTheSameWorker){
                    worker.schedule(addWork);
                }else{
                    scheduler.createWorker().schedule(addWork);
                }
                while (!list.isEmpty()){
                    System.out.println("Begin Remove");
                    if (onTheSameWorker)
                        worker.schedule(removeWork);
                    else
                        scheduler.createWorker().schedule(removeWork);
                    System.out.println("Remove Ended");
                }

            }
//            Observable.range(1, numberOfTasks)
//                    .subscribe( i -> {
//                       current.set(i);
//                        System.out.println("Begin add");
//                        if (onTheSameWorker){
//                            worker.schedule(addWork);
//                        }else{
//                            scheduler.createWorker().schedule(addWork);
//                        }
//                        while (!list.isEmpty()){
//                            System.out.println("Begin Remove");
//                            if (onTheSameWorker)
//                                worker.schedule(removeWork);
//                            else
//                                scheduler.createWorker().schedule(removeWork);
//                            System.out.println("Remove Ended");
//                        }
//
//                    });
        };

        worker.schedule(work);



    }
}
