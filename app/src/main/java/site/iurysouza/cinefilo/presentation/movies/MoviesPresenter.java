package site.iurysouza.cinefilo.presentation.movies;

import com.trello.rxlifecycle.components.support.RxFragment;
import javax.inject.Inject;
import rx.subscriptions.CompositeSubscription;
import site.iurysouza.cinefilo.domain.MoviesUseCase;
import site.iurysouza.cinefilo.presentation.base.mvp.BasePresenter;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class MoviesPresenter extends BasePresenter<MoviesView> {

  private CompositeSubscription subscription = new CompositeSubscription();
  private RxFragment rxLifecycle;
  private MoviesUseCase moviesUseCase;

  @Inject
  public MoviesPresenter(MoviesUseCase moviesUseCase) {
    this.moviesUseCase = moviesUseCase;
  }

  @Override public void attachView(MoviesView view) {
    super.attachView(view);
    rxLifecycle = ((MovieListFragment) view);
  }

  void loadMostPopularMovies() {
    getBaseView().showLoadingIndicator();
    subscription.add(moviesUseCase
        .getPopMoviesObservable()
        .compose(rxLifecycle.bindToLifecycle())
        .subscribe(popularMoviesRealm -> {
          getBaseView().hideLoadingIndicator();
          getBaseView().showMoviesOnAdapter(popularMoviesRealm);
        }, (throwable) -> {
          getBaseView().showErrorIndicator();
          throwable.printStackTrace();
        }));
  }

  @Override public void dettachView() {
    super.dettachView();
    subscription.clear();
  }

  void loadTopRatedMovies() {
    getBaseView().showLoadingIndicator();
    subscription.add(moviesUseCase
        .getTopRatedMoviesObservable()
        .compose(rxLifecycle.bindToLifecycle())
        .subscribe(topMoviesRealm -> {
          getBaseView().hideLoadingIndicator();
          getBaseView().showMoviesOnAdapter(topMoviesRealm);
        }, (throwable) -> {
          getBaseView().showErrorIndicator();
          throwable.printStackTrace();
        }));
  }
  void LoadNowPlayingMovies() {
    getBaseView().showLoadingIndicator();
    subscription.add(moviesUseCase
        .getNowPlayingMovies()
        .compose(rxLifecycle.bindToLifecycle())
        .subscribe(topMoviesRealm -> {
          getBaseView().hideLoadingIndicator();
          getBaseView().showMoviesOnAdapter(topMoviesRealm);
        }, (throwable) -> {
          getBaseView().showErrorIndicator();
          throwable.printStackTrace();
        }));
  }
}
