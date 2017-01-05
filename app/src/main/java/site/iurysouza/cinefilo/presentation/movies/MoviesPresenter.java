package site.iurysouza.cinefilo.presentation.movies;

import android.support.annotation.NonNull;
import com.trello.rxlifecycle.components.support.RxFragment;
import io.realm.RealmResults;
import javax.inject.Inject;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;
import site.iurysouza.cinefilo.domain.MoviesUseCase;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
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
        .subscribe(handleLoadedMovies(), handleLoadedMoviesError()));
  }

  void loadTopRatedMovies() {
    getBaseView().showLoadingIndicator();
    subscription.add(moviesUseCase
        .getTopRatedMoviesObservable()
        .compose(rxLifecycle.bindToLifecycle())
        .subscribe(handleLoadedMovies(), handleLoadedMoviesError()));
  }

  void LoadNowPlayingMovies() {
    getBaseView().showLoadingIndicator();
    subscription.add(moviesUseCase
        .getNowPlayingMovies()
        .compose(rxLifecycle.bindToLifecycle())
        .subscribe(handleLoadedMovies(), handleLoadedMoviesError()));
  }

  void moreNowPlayingMovies(int itemsBeforeMore) {
    subscription.add(moviesUseCase
        .getNextRecentPage(itemsBeforeMore)
        .compose(rxLifecycle.bindToLifecycle())
        .subscribe(handleOnMoreMovies(itemsBeforeMore), handleLoadedMoviesError()));
  }

  void moreMostPopularMovies(int itemsBeforeMore) {
    subscription.add(moviesUseCase
        .getNextPopularPage(itemsBeforeMore)
        .compose(rxLifecycle.bindToLifecycle())
        .subscribe(handleOnMoreMovies(itemsBeforeMore), handleLoadedMoviesError()));
  }

  void moreTopRatedMovies(int itemsBeforeMore) {
    subscription.add(moviesUseCase
        .getNextTopPage(itemsBeforeMore)
        .compose(rxLifecycle.bindToLifecycle())
        .subscribe(handleOnMoreMovies(itemsBeforeMore), handleLoadedMoviesError()));
  }

  @NonNull private Action1<Throwable> handleLoadedMoviesError() {
    return throwable -> {
      MoviesPresenter.this.getBaseView().showErrorIndicator();
      throwable.printStackTrace();
    };
  }

  @NonNull private Action1<RealmResults<RealmMovie>> handleLoadedMovies() {
    return realmMoviesList -> {
      if (!realmMoviesList.isEmpty()) {
        MoviesPresenter.this.getBaseView().hideLoadingIndicator();
      }
      MoviesPresenter.this.getBaseView().showMoviesOnAdapter(realmMoviesList);
    };
  }

  @NonNull private Action1<RealmResults<RealmMovie>> handleOnMoreMovies(final int itemsBeforeMore) {
    return realmMoviesList -> {
      if (!realmMoviesList.isEmpty()) {
        MoviesPresenter.this.getBaseView().hideLoadingIndicator();
      }
      MoviesPresenter.this.getBaseView().addMoreMoviesOnAdapter(realmMoviesList, itemsBeforeMore);
    };
  }
}
