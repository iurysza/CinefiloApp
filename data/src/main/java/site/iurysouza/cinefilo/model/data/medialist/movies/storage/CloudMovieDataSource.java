package site.iurysouza.cinefilo.model.data.medialist.movies.storage;

import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import site.iurysouza.cinefilo.domain.medialist.MediaFilter;
import site.iurysouza.cinefilo.domain.medialist.SortingMethod;
import site.iurysouza.cinefilo.model.data.medialist.movies.services.MovieService;
import site.iurysouza.cinefilo.model.entities.pojo.MovieResults;
import site.iurysouza.cinefilo.model.entities.realm.RealmMoviesResults;

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
  public Observable<RealmMoviesResults> getNowPlayingMovies(int page, String apiKey) {
    return movieService
        .getNowPlayingMovies(apiKey, page)
        .map(movieResults -> RealmMoviesResults.valueOf(movieResults, NOW_QUERY));
  }

@Override  public Observable<RealmMoviesResults> getTopRatedMovies(int page, String apiKey) {
    return movieService
        .getTopRatedMovies(apiKey, page)
        .map(movieResults -> RealmMoviesResults.valueOf(movieResults, TOP_QUERY));
  }
  @Override
  public Observable<RealmMoviesResults> getMostPopularMovies(int page, String apiKey) {
    return movieService
        .getMostPopularMovies(apiKey, page)
        .map(movieResults -> RealmMoviesResults.valueOf(movieResults, POP_QUERY));
  }
  @Override
  public Observable<RealmMoviesResults> getByGenre(int genreId) {
    return movieService.getMoviesByGenre(genreId, "")
        .map(movieResults -> RealmMoviesResults.valueOf(movieResults, GENRE_QUERY));
  }
  @Override
  public Observable<MovieResults> getFilteredMovies(int page, MediaFilter mediaFilter,
      String apiKey) {
    int endDate = mediaFilter.getEndDate();
    int startDate = mediaFilter.getStartDate();
    int minScore = mediaFilter.getMinScore();
    String sortMethod = getSortingMethod(mediaFilter);
    List<Integer> genderList = mediaFilter.getGenderList();
    String genreList = getGenreListAsString(genderList);

    return movieService.getFilteredMovies(
        apiKey,
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
    if (genderList == null || genderList.isEmpty()) {
      return null;
    }
    StringBuilder stringBuilder = new StringBuilder();
    for (Integer gender : genderList) {
      stringBuilder
          .append(String.valueOf(gender))
          .append(",");
    }
    String genderStrings = stringBuilder.toString();
    return genderStrings.substring(0,genderStrings.length()-1);
  }

}
