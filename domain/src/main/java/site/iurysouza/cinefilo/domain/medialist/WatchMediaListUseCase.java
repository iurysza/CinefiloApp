package site.iurysouza.cinefilo.domain.medialist;

import java.util.List;
import rx.Observable;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public interface WatchMediaListUseCase {

   Observable<List<WatchMedia>> getMostPopular();

   Observable<List<WatchMedia>> getNextPopular(int nextPage);

   Observable<List<WatchMedia>> getTopRated();



   Observable<List<WatchMedia>> getNextTopRated(int nextPage);

   Observable<List<WatchMedia>> getFilteredMedia(int page, MediaFilter mediaFilter);

   Observable<List<WatchMedia>> getNowPlaying();

   Observable<List<WatchMedia>> getNextNowPlaying(int nowPage);

   Observable<List<WatchMedia>> getMediaByGender(int gender);
}
