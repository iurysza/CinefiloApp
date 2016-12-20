package site.iurysouza.cinefilo.model.data.storage;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Func3;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class LocalMovieDataStore {

  private Realm realm;

  @Inject
  public LocalMovieDataStore(Realm realm) {
    this.realm = realm;
  }

  @UiThread
  public Observable<RealmMovie> movieById(int movieId) {

    RealmMovie realmMovie = realm
        .where(RealmMovie.class)
        .equalTo(RealmMovie.ID, movieId)
        .findFirstAsync();

    return RealmObject
        .asObservable(realmMovie)
        .filter(RealmObject::isLoaded)
        .filter(RealmObject::isValid);
  }

  public Observable<RealmResults<RealmMovie>> getNowPlayingMovies() {
    RealmResults<RealmMovie> movieRealm = realm
        .where(RealmMovie.class)
        .findAllSorted(RealmMovie.RELEASE_DATE, Sort.DESCENDING);

    return movieRealm.asObservable()
        .filter(RealmResults::isLoaded)
        .filter(RealmResults::isValid);
  }

  public Observable<RealmResults<RealmMovie>> getTopRatedMovies() {

    RealmResults<RealmMovie> movieRealm = realm
        .where(RealmMovie.class)
        .findAllSorted(RealmMovie.RATING, Sort.DESCENDING);

    return movieRealm.asObservable()
        .filter(RealmResults::isLoaded)
        .filter(RealmResults::isValid);
  }

  public Observable<RealmResults<RealmMovie>> getMostPopularMovies() {

    RealmResults<RealmMovie> movieRealm = realm
        .where(RealmMovie.class)
        .findAllSorted(RealmMovie.POPULARITY, Sort.DESCENDING);

    return movieRealm.asObservable()
        .filter(RealmResults::isLoaded)
        .filter(RealmResults::isValid);
  }

  public Observable<List<RealmMovie>> getShowCaseMovies() {
    return Observable.zip(getNowPlayingMovies(), getTopRatedMovies(), getMostPopularMovies(),
        (Func3<RealmResults<RealmMovie>, RealmResults<RealmMovie>, RealmResults<RealmMovie>, List<RealmMovie>>)
            (nowMovies, topMovies, popMovies) -> {
              if (!topMovies.isEmpty() && !topMovies.isEmpty() && !topMovies.isEmpty()) {
                return getShowCaseMovieList(nowMovies, topMovies, popMovies);
              }
              return null;
            });
  }

  @SuppressWarnings("unchecked") @NonNull
  private List getShowCaseMovieList(RealmResults<RealmMovie> nowMovies,
      RealmResults<RealmMovie> topMovies, RealmResults<RealmMovie> popMovies) {
    List results = new ArrayList<RealmMovie>();
    results.add(topMovies.get(getRandomItemPosition(topMovies)));
    results.add(nowMovies.get(getRandomItemPosition(nowMovies)));
    results.add(popMovies.get(getRandomItemPosition(popMovies)));
    return results;
  }

  private int getRandomItemPosition(RealmResults<RealmMovie> movieList) {
    int min = 0;
    int max = movieList.size() - 1;
    Random r = new Random();
    return r.nextInt(max - min + 1) + min;
  }
}
