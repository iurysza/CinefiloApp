package site.iurysouza.cinefilo.presentation.medias;

import com.trello.rxlifecycle.components.support.RxFragment;
import java.util.List;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import site.iurysouza.cinefilo.domain.MoviesUseCase;
import site.iurysouza.cinefilo.domain.SeriesUseCase;
import site.iurysouza.cinefilo.domain.entity.WatchMediaValue;
import site.iurysouza.cinefilo.presentation.UseCase;
import site.iurysouza.cinefilo.presentation.base.mvp.BasePresenter;
import site.iurysouza.cinefilo.presentation.medias.filter.GenderEnum;
import site.iurysouza.cinefilo.util.CineSubscriber;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class MediaPresenter extends BasePresenter<MediaView> {
  private UseCase useCase;
  private Subscription nowPlayingSubscription;
  private Subscription topRatedSubscription;
  private Subscription mostPopularSubscription;
  private RxFragment rxLifecycle;

  private int FILTER = MoviesUseCase.NO_FILTER;

  MediaPresenter() {
  }

  void createPresenter(MoviesUseCase moviesUseCase, SeriesUseCase seriesUseCase,
      int mediaType) {
    if (mediaType == 0) {
      useCase = moviesUseCase;
    } else {
      useCase = seriesUseCase;
    }
  }

  @Override public void attachView(MediaView view) {
    super.attachView(view);
    rxLifecycle = ((MediaListFragment) view);
  }

  void loadNowPlaying() {
    getBaseView().showLoadingIndicator();
    resetSubscription(nowPlayingSubscription);
    nowPlayingSubscription = useCase
        .getNowPlaying()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new CineSubscriber<List<WatchMediaValue>>() {
          @Override public void onNext(List<WatchMediaValue> watchMediaValues) {
            super.onNext(watchMediaValues);
            sendMediaToView(watchMediaValues);
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            getBaseView().showErrorIndicator();
          }
        });
  }

  void loadMostPopular() {
    getBaseView().showLoadingIndicator();
    resetSubscription(mostPopularSubscription);
    mostPopularSubscription = useCase
        .getMostPopular()
        .compose(rxLifecycle.bindToLifecycle())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new CineSubscriber<List<WatchMediaValue>>() {
          @Override public void onNext(List<WatchMediaValue> watchMediaValues) {
            super.onNext(watchMediaValues);
            sendMediaToView(watchMediaValues);
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            getBaseView().showErrorIndicator();
          }
        });
  }

  void loadTopRated() {
    getBaseView().showLoadingIndicator();
    resetSubscription(topRatedSubscription);
    topRatedSubscription = useCase
        .getTopRated()
        .compose(rxLifecycle.bindToLifecycle())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new CineSubscriber<List<WatchMediaValue>>() {
          @Override public void onNext(List<WatchMediaValue> watchMediaValues) {
            super.onNext(watchMediaValues);
            sendMediaToView(watchMediaValues);
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            getBaseView().showErrorIndicator();
          }
        });
  }

  void loadNextNowPlaying(int page) {
    resetSubscription(nowPlayingSubscription);
    nowPlayingSubscription = useCase
        .getNextNowPlaying(page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new CineSubscriber<List<WatchMediaValue>>() {
          @Override public void onNext(List<WatchMediaValue> watchMediaValues) {
            super.onNext(watchMediaValues);
            sendMediaToView(watchMediaValues);
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            getBaseView().showErrorIndicator();
          }
        });
  }

  void loadNextMostPopularPlaying(int page) {
    resetSubscription(mostPopularSubscription);
    mostPopularSubscription = useCase
        .getNextPopular(page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new CineSubscriber<List<WatchMediaValue>>() {
          @Override public void onNext(List<WatchMediaValue> watchMediaValues) {
            super.onNext(watchMediaValues);
            sendMediaToView(watchMediaValues);
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            getBaseView().showErrorIndicator();
          }
        });
  }

  void loadNextTopRated(int page) {
    resetSubscription(topRatedSubscription);
    topRatedSubscription = useCase
        .getNextTopRated(page, FILTER)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new CineSubscriber<List<WatchMediaValue>>() {
          @Override public void onNext(List<WatchMediaValue> watchMediaValues) {
            super.onNext(watchMediaValues);
            sendMediaToView(watchMediaValues);
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            getBaseView().showErrorIndicator();
          }
        });
  }

  private void sendMediaToView(List<WatchMediaValue> watchMediaValues) {
    getBaseView().hideLoadingIndicator();
    if (watchMediaValues.isEmpty()) {
      return;
    }
    getBaseView().sendToListView(watchMediaValues);
  }

  private void resetSubscription(Subscription subscription) {
    if (subscription != null && !subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }
  }

  public void filterNextByGender(GenderEnum genderEnum) {
    FILTER = genderEnum.getGenreId();
  }
}
