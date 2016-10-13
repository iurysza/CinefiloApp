package site.iurysouza.cinefilo.presentation.home;

import javax.inject.Inject;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import site.iurysouza.cinefilo.data.Repos.MovieDataRepository;
import site.iurysouza.cinefilo.presentation.base.mvp.BasePresenter;
import timber.log.Timber;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class HomePresenter extends BasePresenter<HomeView> {

  Subscription subscription;
  private MovieDataRepository movieRepository;

  @Inject
  public HomePresenter(MovieDataRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public void getMovieById(String movieId) {
    subscription = movieRepository
        .getMovieById(movieId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(movieEntity ->
            {
              Timber.d("movie fetched: %s", movieEntity.getTitle());
              getBaseView().showRetrievedMovie(movieEntity);
            },
            throwable -> {
              Timber.e("Failed to fetch movie: %s", throwable.getMessage());
            });
  }

  @Override public void dettachView() {
    super.dettachView();
    if (subscription != null && !subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }
  }
}
