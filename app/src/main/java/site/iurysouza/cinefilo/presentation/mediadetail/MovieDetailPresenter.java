package site.iurysouza.cinefilo.presentation.mediadetail;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import javax.inject.Inject;
import rx.Subscription;
import site.iurysouza.cinefilo.domain.MovieDetailUseCase;
import site.iurysouza.cinefilo.model.data.entity.MovieDetailValue;
import site.iurysouza.cinefilo.presentation.base.mvp.BasePresenter;
import site.iurysouza.cinefilo.util.CineSubscriber;
import site.iurysouza.cinefilo.util.Utils;
import timber.log.Timber;

/**
 * Created by Iury Souza on 01/02/2017.
 */

public class MovieDetailPresenter extends BasePresenter<MovieDetailView> {
  private MovieDetailUseCase useCase;
  private Subscription subscription;
  private RxAppCompatActivity rxLifecycle;

  @Inject
  public MovieDetailPresenter(MovieDetailUseCase useCase) {
    this.useCase = useCase;
  }

  @Override public void attachView(MovieDetailView view) {
    super.attachView(view);
    rxLifecycle = ((MediaDetailActivity) view);
  }

  void getMovieDetailById(int movieId) {
    Utils.resetSubscription(subscription);
    subscription = useCase.getMovieById(movieId)
        .compose(rxLifecycle.bindToLifecycle())
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
        });
  }
}
