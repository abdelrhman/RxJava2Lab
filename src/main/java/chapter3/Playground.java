package chapter3;

import io.reactivex.Observable;
import utils.Helpers;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Chapter3.
 */
public class Playground {

    public static void main(String[] args) {

        //formAndJust();
        Helpers.subscribePrint(Observable.interval(500, TimeUnit.MILLISECONDS),"Interval Observable");
        Helpers.subscribePrint(Observable.interval(0L,1L, TimeUnit.SECONDS),"Timed Interval Observable");
        Helpers.subscribePrint(Observable.timer(1L, TimeUnit.SECONDS),"Timer Observable");
        Helpers.subscribePrint(Observable.error(Exception::new),"Error Observable");
        Helpers.subscribePrint(Observable.empty(),"Empty Observable");
        Helpers.subscribePrint(Observable.never(),"Never Observable");
        Helpers.subscribePrint(Observable.range(1,3),"Range Observable");
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private static void formAndJust() {
        List<String> colors = Arrays.asList("Red", "Green", "Blue");
        Observable.fromIterable(colors)
                .subscribe(System.out::println);

        Path javaFiles = Paths.get("src","main", "java");
        try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(javaFiles)){
            Observable.fromIterable(directoryStream)
                    .subscribe(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Observable.fromArray(new Integer [] {3,5,6,7})
                .subscribe(System.out::println);

        Observable.just("S")
                .subscribe(System.out::println);

        Observable.just("R","x","J","a","v","a")
                .subscribe(System.out::print);

        Observable.just(new User("Ahmad","Talat"))
                .map( u -> u.getFname() + " " + u.getLname())
                .subscribe(System.out::println);
    }


}
