package site.iurysouza.cinefilo.domain.watchmedialist;

import java.util.List;
import rx.Observable;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public interface WatchMediaRepository {

  Observable<List<WatchMedia>> getMostPopular(int page, boolean forceRemote);

  Observable<List<WatchMedia>> getByGenre(int genreId);

  Observable<List<WatchMedia>> getTopRated(int page, boolean forceRemote);

  Observable<List<WatchMedia>> getNowPlaying(int page, boolean forceRemote);


  Observable<List<WatchMedia>> getFilteredBy(int page, MediaFilter mediaFilter);
}
