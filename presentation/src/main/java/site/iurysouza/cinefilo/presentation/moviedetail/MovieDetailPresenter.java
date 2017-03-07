package site.iurysouza.cinefilo.presentation.moviedetail;

import java.util.List;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import site.iurysouza.cinefilo.domain.moviedetail.MovieDetailUseCase;
import site.iurysouza.cinefilo.presentation.base.mvp.BasePresenter;
import site.iurysouza.cinefilo.presentation.medialist.entity.WatchMediaValue;
import site.iurysouza.cinefilo.presentation.moviedetail.entity.MovieDetailValue;
import site.iurysouza.cinefilo.util.CineSubscriber;
import timber.log.Timber;

/**
 * Created by Iury Souza on 01/02/2017.
 */

public class MovieDetailPresenter extends BasePresenter<MovieDetailView> {
  private MovieDetailUseCase useCase;
  private CompositeSubscription subscription = new CompositeSubscription();

  @Inject MovieDetailPresenter(MovieDetailUseCase useCase) {
    this.useCase = useCase;
  }

  @Override public void dettachView() {
    super.dettachView();
    subscription.unsubscribe();
  }

  void getMovieDetailById(int movieId) {
    subscription.add(useCase.getMovieById(movieId)
        .map(MovieDetailValue::mapToValueMedia)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new CineSubscriber<MovieDetailValue>() {
          @Override public void onNext(MovieDetailValue movieDetailValue) {
            super.onNext(movieDetailValue);
            Timber.e("Loaded movie: %s", movieDetailValue);
            getBaseView().updateMovieData(movieDetailValue);
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            Timber.e("Something went wrong: %s", e.getMessage());
            getBaseView().showErrorWarning();
          }
        }));
  }

  void getMoviesSimilarTo(int movieId) {
    subscription.add(useCase.geMoviesSimilarTo(movieId)
        .map(WatchMediaValue::valueOf)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new CineSubscriber<List<WatchMediaValue>>() {
          @Override public void onNext(List<WatchMediaValue> mediaValueList) {
            super.onNext(mediaValueList);
            getBaseView().showSimilarMovies(mediaValueList);
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            getBaseView().showErrorWarning();
          }
        }));
  }
}
