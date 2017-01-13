package site.iurysouza.cinefilo.presentation;

import java.util.List;
import rx.Observable;
import site.iurysouza.cinefilo.domain.entity.WatchMediaValue;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public interface UseCase {

   Observable<List<WatchMediaValue>> getMostPopular();

   Observable<List<WatchMediaValue>> getNextPopular(int nextPage);

   Observable<List<WatchMediaValue>> getTopRated();


   Observable<List<WatchMediaValue>> getNextTopRated(int nextPage);

   Observable<List<WatchMediaValue>> getNowPlaying();

   Observable<List<WatchMediaValue>> getNextNowPlaying(int nowPage);
}
