package site.iurysouza.cinefilo.presentation.medias;

import java.util.List;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import site.iurysouza.cinefilo.domain.watchmedialist.MoviesWatchMediaListUseCase;
import site.iurysouza.cinefilo.domain.watchmedialist.SeriesWatchMediaListUseCase;
import site.iurysouza.cinefilo.domain.watchmedialist.WatchMediaListUseCase;
import site.iurysouza.cinefilo.presentation.base.mvp.BasePresenter;
import site.iurysouza.cinefilo.presentation.medias.entity.WatchMediaValue;
import site.iurysouza.cinefilo.domain.watchmedialist.MediaFilter;
import site.iurysouza.cinefilo.util.CineSubscriber;
import site.iurysouza.cinefilo.util.Utils;

import static site.iurysouza.cinefilo.util.Utils.resetSubscription;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class MediaPresenter extends BasePresenter<MediaView> {
  private WatchMediaListUseCase watchMediaListUseCase;
  private Subscription nowPlayingSubscription;
  private Subscription topRatedSubscription;
  private Subscription mostPopularSubscription;
  private Subscription genderSubscription;

  MediaPresenter() {
  }

  void createPresenter(MoviesWatchMediaListUseCase moviesUseCase, SeriesWatchMediaListUseCase seriesUseCase,
      int mediaType) {
    if (mediaType == 0) {
      watchMediaListUseCase = moviesUseCase;
    } else {
      watchMediaListUseCase = seriesUseCase;
    }
  }

  void loadNowPlaying() {
    getBaseView().showLoadingIndicator();
    resetSubscription(nowPlayingSubscription);
    nowPlayingSubscription = watchMediaListUseCase
        .getNowPlaying()
        .map(WatchMediaValue::valueOf)
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
    mostPopularSubscription = watchMediaListUseCase
        .getMostPopular()
        .map(WatchMediaValue::valueOf)
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
    topRatedSubscription = watchMediaListUseCase
        .getTopRated()
        .map(WatchMediaValue::valueOf)
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
    getBaseView().showMoreProgress();
    resetSubscription(nowPlayingSubscription);
    nowPlayingSubscription = watchMediaListUseCase
        .getNextNowPlaying(page)
        .map(WatchMediaValue::valueOf)
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
    mostPopularSubscription = watchMediaListUseCase
        .getNextPopular(page)
        .map(WatchMediaValue::valueOf)
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
    topRatedSubscription = watchMediaListUseCase
        .getNextTopRated(page)
        .map(WatchMediaValue::valueOf)
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

  void loadFiltered(int page, MediaFilter filter) {
    if (page == 1) getBaseView().showLoadingIndicator();

    Utils.resetSubscription(genderSubscription);
    genderSubscription = watchMediaListUseCase
        .getFilteredMedia(page, filter)
        .map(WatchMediaValue::valueOf)
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
    getBaseView().sendToListView(watchMediaValues);
  }
}
