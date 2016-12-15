package site.iurysouza.cinefilo.presentation.movies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.squareup.picasso.Picasso;
import io.realm.RealmList;
import io.realm.RealmModel;
import javax.inject.Inject;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.model.entities.realm.RealmPopularMovies;
import site.iurysouza.cinefilo.model.entities.realm.RealmTopMovies;
import site.iurysouza.cinefilo.presentation.CineApplication;
import site.iurysouza.cinefilo.presentation.base.BaseFragment;
import timber.log.Timber;

import static site.iurysouza.cinefilo.util.Constants.Movies.POP_MOVIES;
import static site.iurysouza.cinefilo.util.Constants.Movies.REC_MOVIES;
import static site.iurysouza.cinefilo.util.Constants.Movies.TOP_MOVIES;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class MovieListFragment extends BaseFragment
    implements MoviesView,
    MovieAdapter.RealmMovieClickListener {

  public static final String LIST_TYPE = "LIST_TYPE";

  @Inject MoviesPresenter moviesPresenter;
  @BindView(R.id.container_movie_list) LinearLayout container;
  @BindView(R.id.movie_list_recyclerview) SuperRecyclerView movieList;

  private int listType;
  private MovieAdapter movieAdapter;

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

    listType = getArguments().getInt(LIST_TYPE);
    moviesPresenter.attachView(this);

    loadData(listType);
    setupRecyclerView();
    return view;
  }

  private void setupRecyclerView() {
    movieList.getRecyclerView().setHasFixedSize(true);
    movieList.setLayoutManager(new LinearLayoutManager(getContext()));
    movieAdapter = new MovieAdapter(Picasso.with(getContext()), this);
    movieList.setAdapter(movieAdapter);
  }

  private void loadData(int lisType) {
    switch (lisType) {
      case REC_MOVIES:
        break;
      case POP_MOVIES:
        moviesPresenter.loadMostPopularMovies();
        break;
      case TOP_MOVIES:
        moviesPresenter.loadTopMovies();
        break;
      default:
        moviesPresenter.loadTopMovies();
    }
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

  @Override public void showPopularMovieList(RealmModel popularMovieList) {
    RealmList<RealmMovie> movieList = null;
    if (popularMovieList instanceof RealmTopMovies) {
      movieList = ((RealmTopMovies) popularMovieList).getMovieList();
    }
    if (popularMovieList instanceof RealmPopularMovies) {
      movieList = ((RealmPopularMovies) popularMovieList).getMovieList();
    }

    movieAdapter.addMovies(movieList);
    Timber.e("GOT %s MOVIES", movieList.size());

  }

  @Override protected void setupFragmentComponent() {
    ((CineApplication) getContext().getApplicationContext()).getRepositoryComponent().inject(this);
  }

  @Override public void onRealmMovieClick(RealmMovie movie) {
    Timber.e("GOT %s MOVIES", movie.getOriginalTitle());
    Toast.makeText(getContext(), movie.getOriginalTitle(), Toast.LENGTH_SHORT).show();
  }
}
