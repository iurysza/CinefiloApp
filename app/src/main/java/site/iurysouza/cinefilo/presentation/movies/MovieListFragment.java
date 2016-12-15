package site.iurysouza.cinefilo.presentation.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import javax.inject.Inject;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.model.entities.realm.RealmPopularMovies;
import site.iurysouza.cinefilo.presentation.CineApplication;
import site.iurysouza.cinefilo.presentation.base.BaseFragment;
import timber.log.Timber;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class MovieListFragment extends BaseFragment implements MoviesView {

  public static final String LIST_TYPE = "LIST_TYPE";
  @Inject MoviesPresenter moviesPresenter;
  @BindView(R.id.container_movie_list) LinearLayout container;
  private CharSequence pageTitle;
  private int lisType;

  public static MovieListFragment newInstance(int movieListType) {
    MovieListFragment moviesFragment = new MovieListFragment();
    Bundle args = new Bundle();
    args.putInt(LIST_TYPE, movieListType);
    moviesFragment.setArguments(args);
    return moviesFragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.movie_list_fragment, container, false);
    ButterKnife.bind(this, view);
    lisType = getArguments().getInt(LIST_TYPE);
    moviesPresenter.attachView(this);
    moviesPresenter.loadMovies();
    return view;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    moviesPresenter.dettachView();
  }

  @Override public void showLoadingIndicator() {

  }

  @Override public void hideLoadingIndicator() {

  }

  @Override public void showErrorIndicator() {

  }

  @Override public void showPopularMovieList(RealmPopularMovies popularMovieList) {
    int size = popularMovieList.getMovieList().size();
    Timber.e("GOT %s MOVIES", size);
  }

  @Override protected void setupFragmentComponent() {
    ((CineApplication) getContext().getApplicationContext()).getRepositoryComponent().inject(this);
  }


}
