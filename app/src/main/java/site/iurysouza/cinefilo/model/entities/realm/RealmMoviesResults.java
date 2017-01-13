package site.iurysouza.cinefilo.model.entities.realm;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;
import lombok.Data;
import site.iurysouza.cinefilo.model.entities.pojo.Movie;
import site.iurysouza.cinefilo.model.entities.pojo.MovieResults;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@Data
@RealmClass
public class RealmMoviesResults implements RealmModel {
  public static final String PAGE = "page";
  private int page;
  private Long totalPages;
  private Long totalResults;
  private RealmList<RealmMovie> movieList;

  public static RealmMoviesResults valueOf(MovieResults movieResults, int queryType) {
    RealmList<RealmMovie> realmMovieList = new RealmList<>();
    RealmMoviesResults realmMoviesResults = new RealmMoviesResults();

    if (movieResults.getMovieList().isEmpty()) {
      return realmMoviesResults;
    }

    for (Movie movie : movieResults.getMovieList()) {
      RealmMovie movieObject = RealmMovie.valueOf(movie, queryType);
      if (movieObject != null) {
        realmMovieList.add(movieObject);
      }
    }

    realmMoviesResults.setMovieList(realmMovieList);
    realmMoviesResults.setPage(movieResults.getPage());
    return realmMoviesResults;
  }
}
