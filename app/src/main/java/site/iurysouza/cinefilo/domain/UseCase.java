package site.iurysouza.cinefilo.domain;

import rx.Observable;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public interface UseCase {
  Observable buildUseCaseObservable();
}
