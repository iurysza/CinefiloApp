package site.iurysouza.cinefilo.presentation.movies;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;
import io.realm.RealmList;
import io.realm.RealmResults;
import javax.inject.Inject;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.presentation.CineApplication;
import site.iurysouza.cinefilo.presentation.base.BaseFragment;
import site.iurysouza.cinefilo.presentation.movies.pager.MoviesPagerFragment;
import site.iurysouza.cinefilo.util.Constants;
import timber.log.Timber;

import static site.iurysouza.cinefilo.util.Constants.Movies.POP_MOVIES;
import static site.iurysouza.cinefilo.util.Constants.Movies.REC_MOVIES;
import static site.iurysouza.cinefilo.util.Constants.Movies.TOP_MOVIES;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class MovieListFragment extends BaseFragment
    implements MoviesView,
    MovieAdapter.RealmMovieClickListener,
    OnMoreListener {

  public static final String LIST_TYPE = "LIST_TYPE";

  @Inject MoviesPresenter moviesPresenter;
  @BindView(R.id.container_movie_list) FrameLayout container;
  @BindView(R.id.movie_list_progressImage) AVLoadingIndicatorView loadingPlaceHolder;
  @BindView(R.id.movie_list_recyclerview) SuperRecyclerView movieList;

  private int listType;
  private MovieAdapter movieAdapter;
  private MoviesPagerFragment parentFragment;

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
    parentFragment = ((MoviesPagerFragment) getFragmentManager().getFragments().get(1));

    setupRecyclerView();

    loadData(listType);
    return view;
  }

  private void setupRecyclerView() {
    movieList.getRecyclerView().setHasFixedSize(true);
    movieList.setLayoutManager(new LinearLayoutManager(getContext()));
    movieList.addItemDecoration(new MaterialViewPagerHeaderDecorator());
    movieAdapter = new MovieAdapter(Picasso.with(getContext()), this);
    movieList.setAdapter(movieAdapter);
    movieList.setupMoreListener(this, 10);
    movieList.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        parentFragment.refreshHeader();
      }
    });
  }

  private void loadData(int lisType) {
    switch (lisType) {
      case REC_MOVIES:
        moviesPresenter.LoadNowPlayingMovies();
        break;
      case POP_MOVIES:
        moviesPresenter.loadMostPopularMovies();
        break;
      case TOP_MOVIES:
        moviesPresenter.loadTopRatedMovies();
        break;
      default:
        moviesPresenter.LoadNowPlayingMovies();
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    moviesPresenter.dettachView();
  }

  @Override public void showLoadingIndicator() {
    movieList.setVisibility(View.GONE);
    loadingPlaceHolder.show();
    Timber.e("Loading");
  }

  @Override public void hideLoadingIndicator() {
    movieList.setVisibility(View.VISIBLE);
    loadingPlaceHolder.hide();
    Timber.e("Finished Loading");
  }

  @Override public void showErrorIndicator() {
    Timber.e("Error");
  }

  @Override public void showMoviesOnAdapter(RealmResults<RealmMovie> topMoviesRealm) {
    RealmList<RealmMovie> movieList = new RealmList<>();
    movieList.addAll(topMoviesRealm.subList(0, topMoviesRealm.size()));
    movieAdapter.addMovies(movieList);
    Timber.e("Added %s movies to list", movieList.size());
  }

  @Override protected void setupFragmentComponent() {
    ((CineApplication) getContext().getApplicationContext()).getRepositoryComponent().inject(this);
  }

  @Override public void onRealmMovieClick(RealmMovie movie) {
    Timber.e("Clicked %s item", movie.getOriginalTitle());
    Toast.makeText(getContext(), movie.getOriginalTitle(), Toast.LENGTH_SHORT).show();
  }

  @Override
  public void addMoreMoviesOnAdapter(RealmResults<RealmMovie> topMoviesRealm,
      int page) {
    movieAdapter.addAllAt(topMoviesRealm, page);
    movieList.hideMoreProgress();
    //movieAdapter.onMovieListChanged(topMoviesRealm);
  }

  @SuppressLint("BinaryOperationInTimber") @Override
  public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {

    if (maxLastVisiblePosition == overallItemsCount - 10) {
      Timber.e(
          "Loading more: \noverallItemsCount = %s "
              + "\nitemsBeforeMore = %s "
              + "\nmaxLastVisiblePosition = %s",
          overallItemsCount, itemsBeforeMore, maxLastVisiblePosition);

      int page = (overallItemsCount / Constants.Movies.PAGE_SIZE) + 1;
      switch (this.listType) {
        case REC_MOVIES:
          moviesPresenter.moreNowPlayingMovies(page);
          break;
        case POP_MOVIES:
          moviesPresenter.moreMostPopularMovies(page);
          break;
        case TOP_MOVIES:
          moviesPresenter.moreTopRatedMovies(page);
          break;
        default:
          moviesPresenter.moreNowPlayingMovies(page);
      }
    } else {
      movieList.hideMoreProgress();
    }
  }
}
