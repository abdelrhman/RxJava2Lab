package chapter3;

import io.reactivex.Observable;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Chapter3.
 */
public class Playground {

    public static void main(String[] args) {

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
