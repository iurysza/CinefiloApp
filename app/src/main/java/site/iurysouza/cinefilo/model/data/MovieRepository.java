package site.iurysouza.cinefilo.model.data;

import io.realm.RealmResults;
import java.io.Closeable;
import java.util.List;
import rx.Observable;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public interface MovieRepository extends Closeable {
  Observable<RealmMovie> getMovieById(int id);

  Observable<RealmResults<RealmMovie>> getMoviesByPopulariy(int page, boolean forceRemote);

  Observable<RealmResults<RealmMovie>> getTopRatedMovies(int page, boolean forceRemote);

  Observable<RealmResults<RealmMovie>> getNowPlayingMovies(int page, boolean forceRemote);

  Observable<List<RealmMovie>> getShowCaseMovies();

  void getGenreList();
}
