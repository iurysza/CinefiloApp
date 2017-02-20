package site.iurysouza.cinefilo.model.data.movies.storage;

import java.util.List;
import site.iurysouza.cinefilo.model.entities.realm.RealmGenre;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.model.entities.realm.RealmMoviesResults;

/**
 * Created by Iury Souza on 14/02/2017.
 */
public interface ILocalMovieDataSource {
  void storeMoviesAndCurrentPageInRealm(RealmMoviesResults realmMoviesResults, int listType);

  void storeCurrentPage(int page, int listType);

  int getCurrentPageFor(int listType);

  List<RealmMovie> getTopRatedMovies();

  List<RealmMovie> getMostPopularMovies();

  List<RealmMovie> getNowPlayingMovies();

  void storeGenresInRealm(List<RealmGenre> realmGenres);
}
