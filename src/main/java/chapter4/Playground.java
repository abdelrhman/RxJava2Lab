package chapter4;

import io.reactivex.Flowable;
import io.reactivex.Observable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        subscribePrint(
                listFolder(Paths.get("src", "main", "resources"), "{lorem.txt,letters.txt}")
                        .flatMap(path -> from(path)), "FS"
        );

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
