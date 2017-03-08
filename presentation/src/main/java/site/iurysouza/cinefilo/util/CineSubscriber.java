package site.iurysouza.cinefilo.util;

import rx.Subscriber;
import timber.log.Timber;

/**
 * Created by Iury Souza on 07/01/2017.
 */

public abstract class CineSubscriber<T> extends Subscriber<T> {
  @Override
  public void onCompleted() {
    Timber.i("Subscriber Terminated");
  }

  @Override public void onNext(T t) {
    if (t != null) {
      Timber.i("Data: %s \n", t.toString());
    }
  }

  @Override public void onError(Throwable e) {
    Timber.e("Error: %s", e.getMessage());
  }
}

