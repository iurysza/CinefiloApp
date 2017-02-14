package site.iurysouza.cinefilo.model.data.moviedetail;

import android.support.annotation.NonNull;
import java.util.List;
import rx.Observable;
import site.iurysouza.cinefilo.model.data.entity.MovieDetailValue;
import site.iurysouza.cinefilo.model.data.entity.WatchMedia;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;

/**
 * Created by Iury Souza on 14/02/2017.
 */
public interface IMovieDetailRepository {
  Observable<MovieDetailValue> getMovieById(int movieId);

  @NonNull Observable<RealmMovie> getNowPlayingFromApi(int movieId);

  @NonNull Observable<List<WatchMedia>> getMoviesSimilarTo(int movieId, int page);
}
