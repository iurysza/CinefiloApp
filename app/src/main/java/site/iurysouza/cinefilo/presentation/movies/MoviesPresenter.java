package site.iurysouza.cinefilo.presentation.movies;

import com.trello.rxlifecycle.components.support.RxFragment;
import javax.inject.Inject;
import rx.Subscription;
import site.iurysouza.cinefilo.domain.MoviesUseCase;
import site.iurysouza.cinefilo.presentation.base.mvp.BasePresenter;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class MoviesPresenter extends BasePresenter<MoviesView> {

  private Subscription subscription;
  private RxFragment rxLifecycle;
  private MoviesUseCase moviesUseCase;

  @Inject
  public MoviesPresenter(MoviesUseCase moviesUseCase) {
    this.moviesUseCase = moviesUseCase;
  }

  @Override public void attachView(MoviesView view) {
    super.attachView(view);
    rxLifecycle = ((MoviesFragment) view);
  }

  void loadMovies() {
    getBaseView().showLoadingIndicator();
    subscription = moviesUseCase
        .buildUseCaseObservable()
        .compose(rxLifecycle.bindToLifecycle())
        .subscribe(popularMoviesRealm -> {
          getBaseView().hideLoadingIndicator();
          getBaseView().showPopularMovieList(popularMoviesRealm);
        }, (throwable) -> {
          getBaseView().showErrorIndicator();
          throwable.printStackTrace();
        });
  }
}
