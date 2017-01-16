package site.iurysouza.cinefilo.domain;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public interface WatchMediaRepository {

  void getMostPopular(int page, boolean forceRemote);


  void getByGenre(int genreId, int filteredList);

  void getTopRated(int page, boolean forceRemote);

  void getNowPlaying(int page, boolean forceRemote);

  void getGenreList();
}
