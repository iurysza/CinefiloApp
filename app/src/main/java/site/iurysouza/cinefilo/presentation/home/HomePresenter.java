package site.iurysouza.cinefilo.presentation.home;

import javax.inject.Inject;
import rx.Subscription;
import site.iurysouza.cinefilo.model.data.MovieDataRepository;
import site.iurysouza.cinefilo.presentation.base.mvp.BasePresenter;
import timber.log.Timber;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class HomePresenter extends BasePresenter<HomeView> {

  private Subscription subscription;
  private MovieDataRepository movieRepository;

  @Inject
  public HomePresenter(MovieDataRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public void getMovieById(int movieId) {
    subscription = movieRepository
        .getMovieById(movieId)
        .subscribe(movieRealm ->
            {
              Timber.i("movie fetched: %s", movieRealm.getTitle());
              //getBaseView().showRetrievedMovie(movieRealm);
            },
            throwable -> {
              Timber.e("Failed to fetch movie: %s", throwable.getMessage());
            });
  }

  @Override public void dettachView() {
    super.dettachView();
  }
}
