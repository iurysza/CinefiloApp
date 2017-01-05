package site.iurysouza.cinefilo.model.data.storage;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import org.joda.time.DateTime;
import rx.Observable;
import rx.functions.Func3;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.util.Constants;

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
        .greaterThan(RealmMovie.QUERY_TYPE, RealmMovie.TOP_QUERY)
        .greaterThan(RealmMovie.VOTE_AVG, Constants.Movies.MIN_VOTE_AVG)
        .greaterThan(RealmMovie.RELEASE_DATE, getValidDate())
        .findAllSortedAsync(RealmMovie.RELEASE_DATE, Sort.DESCENDING);

    return movieRealm.asObservable()
        .filter(RealmResults::isLoaded)
        .filter(RealmResults::isValid);
  }

  public Observable<RealmResults<RealmMovie>> getTopRatedMovies() {
    RealmResults<RealmMovie> movieRealm = realm
        .where(RealmMovie.class)
        .equalTo(RealmMovie.QUERY_TYPE, RealmMovie.TOP_QUERY)
        .greaterThan(RealmMovie.VOTE_COUNT, Constants.Movies.MIN_VOTE_COUNT)
        .findAllSortedAsync(RealmMovie.VOTE_AVG, Sort.DESCENDING);

    return movieRealm.asObservable()
        .filter(RealmResults::isLoaded)
        .filter(RealmResults::isValid);
  }

  public Observable<RealmResults<RealmMovie>> getMostPopularMovies() {
    RealmResults<RealmMovie> movieRealm = realm
        .where(RealmMovie.class)
        .greaterThan(RealmMovie.QUERY_TYPE, RealmMovie.TOP_QUERY)
        .findAllSortedAsync(RealmMovie.POPULARITY, Sort.DESCENDING);

    return movieRealm.asObservable()
        .filter(RealmResults::isLoaded)
        .filter(RealmResults::isValid);
  }

  public Observable<List<RealmMovie>> getShowCaseMovies() {
    return Observable.zip(getNowPlayingMovies(), getMostPopularMovies(), getTopRatedMovies(),
        (Func3<RealmResults<RealmMovie>, RealmResults<RealmMovie>, RealmResults<RealmMovie>, List<RealmMovie>>)
            (nowMovies, popMovies, topMovies) -> {
              if (!nowMovies.isEmpty() && !popMovies.isEmpty() && !topMovies.isEmpty()) {
                return getShowCaseMovieList(nowMovies, popMovies, topMovies);
              }
              return null;
            });
  }

  @SuppressWarnings("unchecked") @NonNull
  private List getShowCaseMovieList(RealmResults<RealmMovie> nowMovies,
      RealmResults<RealmMovie> popMovies, RealmResults<RealmMovie> topMovies) {
    List results = new ArrayList<RealmMovie>();
    results = prepareList(results, nowMovies);
    results = prepareList(results, popMovies);
    results = prepareList(results, topMovies);
    return results;
  }

  private List<RealmMovie> prepareList(List<RealmMovie> showCaseList,
      RealmResults<RealmMovie> movieList) {
    RealmMovie movie = movieList.get(getRandomItemPosition(movieList));
    if (!movie.getBackdropPath().isEmpty()) {
      showCaseList.add(movie);
    }
    return showCaseList;
  }

  private int getRandomItemPosition(RealmResults<RealmMovie> movieList) {
    int max = movieList.size() - 1;
    Random r = new Random();
    int randomNumber;
    try {
      randomNumber = r.nextInt(max);
    } catch (IllegalArgumentException e) {
      randomNumber = 0;
    }
    return randomNumber;
  }

  private Date getValidDate() {
    return new DateTime().minusMonths(6).toDate();
  }
}
