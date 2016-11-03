package chapter5;

import com.google.gson.Gson;
import io.reactivex.Observable;
import org.apache.http.nio.client.HttpAsyncClient;
import rx.apache.http.ObservableHttp;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.nio.charset.StandardCharsets.UTF_8;
import static utils.Helpers.subscribePrint;

/**
 * Created by m on 11/3/16.
 */
public class HttpClientExample {

//    private static Map<String, Set<Map<String, Object>>> cache = new ConcurrentHashMap<>();
//
//    public static void main(String[] args) {
//        final String username = "abdelrhman";
//        Observable<Map> resp = githubUserInfoRequest(client, username);
//        subscribePrint(resp
//                .map(json -> json.get("name") + " " + json.get("language")), "Json");
//
//    }
//
//    private static Observable<Map> githubUserInfoRequest(HttpAsyncClient client, String githubUser) {
//        if (githubUser == null)
//            return Observable.error(new NullPointerException("Github user must not be null"));
//        String url = "https://api.github.com/users/" + githubUser +
//                "/repos";
//        return requestJson(client, url)
//                .filter(json -> json.containsKey("git_url"))
//                .filter(json -> json.get("fork").equals(false));
//
//
//    }
//
//    private static Observable<Map> requestJson(HttpAsyncClient client, String url) {
//        Observable<String> rawResponse = ObservableHttp.createGet(url, client)
//                .toObservable()
//                .flatMap(resp -> resp.getContent()
//                        .map(bytes -> new String(bytes, UTF_8)))
//                .retry(5)
//                .cast(String.class)
//                .map(String::trim)
//                .doOnNext(resp -> getCache(url).clear());
//        Observable<String> objects = rawResponse.
//                filter(data -> data.startsWith("{"))
//                .map(data -> "[" + data + "]");
//        Observable<String> arrays = rawResponse
//                .filter(data -> data.startsWith("["));
//        Observable<Map> response = arrays
//                .ambWith(objects)
//                .map(data -> new Gson().fromJson(data, List.class))
//                .flatMapIterable(list -> list)
//                .cast(Map.class)
//                .doOnNext(json -> getCache(url).add(json));
//        return Observable.amb(fromCache(url), response);
//
//    }
//
//
//
//    public static Observable<Map<String, Object>> fromCache(String url) {
//        return Observable.fromIterable(getCache(url)).defaultIfEmpty(null)
//                .flatMap(json -> (json == null) ? Observable.never() : Observable.just(json))
//                .doOnNext(json -> json.put("json_cached", true));
//    }
//
//    public static Set<Map<String, Object>> getCache(String url) {
//        if (!cache.containsKey(url)) {
//            cache.put(url, new HashSet<Map<String, Object>>());
//        }
//        return cache.get(url);
//    }


}
