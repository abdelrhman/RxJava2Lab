package chapter4;

import io.reactivex.Observable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static chapter3.Playground.subscribePrint;

/**
 * Chapter 4
 */
public class Playground {

    public static void main(String[] args) {
//        subscribePrint(
//                Observable.just(1, 2, 3, 4, 5)
//                        .map(v -> v * 3)
//                        .map(v -> (v % 2 == 0) ? "even" : "odd"), "Map: ");

//        subscribePrint(
//                listFolder(Paths.get("src", "main", "resources"), "{lorem.txt,letters.txt}")
//                        .flatMap(path -> from(path)), "FS"
//        );

//        subscribePrint(
//        Observable.just(-1,0,1)
//                .map(v -> 2/v )
//                .flatMap((v) -> Observable.just(v),
//                        e -> Observable.just(0),
//                        () -> Observable.just(42)),
//                "flatMap");

//        subscribePrint(
//                Observable.just(5,411)
//                .flatMap(v ->Observable.range(v, 2),
//                        (x,y) -> x+y),
//                "Combine"
//        );

        List<String> albums = Arrays.asList("The Piper at the Gates of Dawn",
                "A Saucerful of Secrets",
                "More", "Ummagumma", "Atom Heart Mother",
                "Meddle", "Obscured by Clouds",
                "The Dark Side of the Moon",
                "Wish You Were Here", "Animals", "The Wall");
//        Observable.fromIterable(albums)
//                .groupBy(album -> album.split(" ").length)
//                .subscribe(obs -> subscribePrint(obs, obs.getKey()+" Words."));

//        Observable.fromIterable(albums)
//                .groupBy(album -> album.replaceAll("[^mM]", "").length(), album -> album.replaceAll("[mM]", "*"))
//                .subscribe( obs -> subscribePrint(obs, obs.getKey()+" by occurrences of (m)"));

        List<Number> numbers = Arrays.asList(1, 2, 3);
        subscribePrint(Observable.fromIterable(numbers)
                .cast(Integer.class)
                .timestamp()
                .timeInterval(), " Numbers");


    }

    private static Observable<Path> listFolder(Path dir, String glop) {
        return Observable.using(
                () -> Files.newDirectoryStream(dir, glop),
                stream -> Observable.fromIterable(stream),
                stream -> stream.close()
        );
    }

    private static Observable<String> from(Path path) {
        return Observable.using(
                () -> new Scanner(path),
                scanner -> Observable.create(emitter -> {
                    while (scanner.hasNext()) {
                        if (!emitter.isDisposed())
                            emitter.onNext(scanner.nextLine());
                    }
                    if (!emitter.isDisposed())
                        emitter.onComplete();
                }),
                scanner -> scanner.close()
        );
    }
}
