 package site.iurysouza.cinefilo.presentation.series;

import javax.inject.Inject;
import rx.Subscription;
import site.iurysouza.cinefilo.model.data.movies.MoviesRepository;
import site.iurysouza.cinefilo.presentation.base.mvp.BasePresenter;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class SeriesPresenter extends BasePresenter<SeriesView> {

  private Subscription subscription;
  private MoviesRepository movieRepository;

  @Inject
  public SeriesPresenter(MoviesRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public void getMovieById(int movieId) {
    //subscription = movieRepository
    //    .getById(movieId)
    //    .subscribe(movieRealm ->
    //        {
    //          Timber.i("movie fetched: %s", movieRealm.getTitle());
    //          //getBaseView().showRetrievedMovie(movieRealm);
    //        },
    //        throwable -> {
    //          Timber.e("Failed to fetch movie: %s", throwable.getMessage());
    //        });
  }

  @Override public void dettachView() {
    super.dettachView();
  }
}
