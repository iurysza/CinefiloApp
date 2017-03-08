package site.iurysouza.cinefilo.model.data.medialist.movies.storage;

import rx.Observable;
import site.iurysouza.cinefilo.domain.medialist.MediaFilter;
import site.iurysouza.cinefilo.model.entities.pojo.MovieResults;
import site.iurysouza.cinefilo.model.entities.realm.RealmMoviesResults;

/**
 * Created by Iury Souza on 14/02/2017.
 */
public interface ICloudMovieDataSource {
  Observable<RealmMoviesResults> getNowPlayingMovies(int page, String apiKey);

  Observable<RealmMoviesResults> getTopRatedMovies(int page, String apiKey);

  Observable<RealmMoviesResults> getMostPopularMovies(int page, String apiKey);

  Observable<RealmMoviesResults> getByGenre(int genreId);

  Observable<MovieResults> getFilteredMovies(int page, MediaFilter mediaFilter, String apiKey);
}
