package site.iurysouza.cinefilo.presentation.medias;

import java.util.List;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import site.iurysouza.cinefilo.domain.MediaFilter;
import site.iurysouza.cinefilo.domain.MoviesUseCase;
import site.iurysouza.cinefilo.domain.SeriesUseCase;
import site.iurysouza.cinefilo.domain.entity.WatchMediaValue;
import site.iurysouza.cinefilo.presentation.UseCase;
import site.iurysouza.cinefilo.presentation.base.mvp.BasePresenter;
import site.iurysouza.cinefilo.util.CineSubscriber;
import site.iurysouza.cinefilo.util.Utils;

import static site.iurysouza.cinefilo.util.Utils.resetSubscription;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class MediaPresenter extends BasePresenter<MediaView> {
  private UseCase useCase;
  private Subscription nowPlayingSubscription;
  private Subscription topRatedSubscription;
  private Subscription mostPopularSubscription;
  private Subscription genderSubscription;

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
        .getNextTopRated(page)
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
    Utils.resetSubscription(genderSubscription);
    genderSubscription = useCase
        .getFilteredMedia(page, filter)
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
