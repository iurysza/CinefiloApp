package site.iurysouza.cinefilo.model.data.movies.storage;

import rx.Observable;
import site.iurysouza.cinefilo.domain.watchmedialist.MediaFilter;
import site.iurysouza.cinefilo.model.entities.pojo.MovieResults;
import site.iurysouza.cinefilo.model.entities.realm.RealmMoviesResults;

/**
 * Created by Iury Souza on 14/02/2017.
 */
public interface ICloudMovieDataSource {
  Observable<RealmMoviesResults> getNowPlayingMovies(int page);

  Observable<RealmMoviesResults> getTopRatedMovies(int page);

  Observable<RealmMoviesResults> getMostPopularMovies(int page);

  Observable<RealmMoviesResults> getByGenre(int genreId);

  Observable<MovieResults> getFilteredMovies(int page, MediaFilter mediaFilter);
}
