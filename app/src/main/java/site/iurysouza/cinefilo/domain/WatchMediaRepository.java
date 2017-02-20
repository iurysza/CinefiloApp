package site.iurysouza.cinefilo.domain;

import java.util.List;
import rx.Observable;
import site.iurysouza.cinefilo.model.data.entity.WatchMedia;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public interface WatchMediaRepository {

  void getMostPopular(int page, boolean forceRemote);

  void getByGenre(int genreId);

  void getTopRated(int page, boolean forceRemote);

  void getNowPlaying(int page, boolean forceRemote);


  Observable<List<WatchMedia>> getFilteredBy(int page, MediaFilter mediaFilter);
}
