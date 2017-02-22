package site.iurysouza.cinefilo.model.data.medialist.movies.storage;

import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.domain.watchmedialist.MediaFilter;
import site.iurysouza.cinefilo.domain.watchmedialist.SortingMethod;
import site.iurysouza.cinefilo.model.entities.pojo.MovieResults;
import site.iurysouza.cinefilo.model.entities.realm.RealmMoviesResults;
import site.iurysouza.cinefilo.model.data.medialist.services.MovieService;
import site.iurysouza.cinefilo.util.Constants;

import static site.iurysouza.cinefilo.model.entities.realm.RealmMovie.GENRE_QUERY;
import static site.iurysouza.cinefilo.model.entities.realm.RealmMovie.NOW_QUERY;
import static site.iurysouza.cinefilo.model.entities.realm.RealmMovie.POP_QUERY;
import static site.iurysouza.cinefilo.model.entities.realm.RealmMovie.TOP_QUERY;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class CloudMovieDataSource implements ICloudMovieDataSource {
  private final MovieService movieService;

  @Inject
  public CloudMovieDataSource(MovieService movieService) {
    this.movieService = movieService;
  }
  @Override
  public Observable<RealmMoviesResults> getNowPlayingMovies(int page) {
    return movieService
        .getNowPlayingMovies(Constants.MOVIE_DB_API.API_KEY, page)
        .map(movieResults -> RealmMoviesResults.valueOf(movieResults, NOW_QUERY));
  }

@Override  public Observable<RealmMoviesResults> getTopRatedMovies(int page) {
    return movieService
        .getTopRatedMovies(Constants.MOVIE_DB_API.API_KEY, page)
        .map(movieResults -> RealmMoviesResults.valueOf(movieResults, TOP_QUERY));
  }
  @Override
  public Observable<RealmMoviesResults> getMostPopularMovies(int page) {
    return movieService
        .getMostPopularMovies(Constants.MOVIE_DB_API.API_KEY, page)
        .map(movieResults -> RealmMoviesResults.valueOf(movieResults, POP_QUERY));
  }
  @Override
  public Observable<RealmMoviesResults> getByGenre(int genreId) {
    return movieService.getMoviesByGenre(genreId, Constants.MOVIE_DB_API.API_KEY)
        .map(movieResults -> RealmMoviesResults.valueOf(movieResults, GENRE_QUERY));
  }
  @Override
  public Observable<MovieResults> getFilteredMovies(int page, MediaFilter mediaFilter) {
    int endDate = mediaFilter.getEndDate();
    int startDate = mediaFilter.getStartDate();
    List<Integer> genderList = mediaFilter.getGenderList();
    String genreList = getGenreListAsString(genderList);
    int minScore = mediaFilter.getMinScore();
    String sortMethod = getSortingMethod(mediaFilter);

    return movieService.getFilteredMovies(
        Constants.MOVIE_DB_API.API_KEY,
        page,
        startDate,
        endDate,
        genreList,
        minScore,
        sortMethod);
  }

  private String getSortingMethod(MediaFilter mediaFilter) {
    SortingMethod sortBy = mediaFilter.getSortBy();
    return sortBy == null ? null : sortBy.getSortMethod();
  }

  private String getGenreListAsString(List<Integer> genderList) {
    if (genderList == null) {
      return null;
    }
    StringBuilder stringBuilder = new StringBuilder();
    for (Integer gender : genderList) {
      stringBuilder
          .append(",")
          .append(String.valueOf(gender));
    }
    return stringBuilder.toString();
  }

}
