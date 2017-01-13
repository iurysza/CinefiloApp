package site.iurysouza.cinefilo.presentation.movies.pager;

import com.trello.rxlifecycle.components.support.RxFragment;
import javax.inject.Inject;
import rx.subscriptions.CompositeSubscription;
import site.iurysouza.cinefilo.domain.MoviesUseCase;
import site.iurysouza.cinefilo.presentation.base.mvp.BasePresenter;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class MoviesPagerPresenter extends BasePresenter<MoviesPagerView> {

  private CompositeSubscription subscription = new CompositeSubscription();
  private RxFragment rxLifecycle;
  private MoviesUseCase moviesUseCase;

  @Inject
  public MoviesPagerPresenter(MoviesUseCase moviesUseCase) {
    this.moviesUseCase = moviesUseCase;
  }

  @Override public void attachView(MoviesPagerView view) {
    super.attachView(view);
    rxLifecycle = ((MoviesPagerFragment) view);
  }


}
