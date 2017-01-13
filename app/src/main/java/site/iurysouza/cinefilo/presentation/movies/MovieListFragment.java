package site.iurysouza.cinefilo.presentation.movies;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.domain.MoviesUseCase;
import site.iurysouza.cinefilo.domain.SeriesUseCase;
import site.iurysouza.cinefilo.domain.entity.WatchMediaValue;
import site.iurysouza.cinefilo.presentation.CineApplication;
import site.iurysouza.cinefilo.presentation.base.BaseFragment;
import site.iurysouza.cinefilo.util.Utils;
import timber.log.Timber;

import static com.ncapdevi.fragnav.FragNavController.TAB1;
import static site.iurysouza.cinefilo.presentation.movies.pager.MoviesPagerFragment.MEDIA_TYPE;
import static site.iurysouza.cinefilo.util.Constants.Media.POP_MEDIA;
import static site.iurysouza.cinefilo.util.Constants.Media.REC_MEDIA;
import static site.iurysouza.cinefilo.util.Constants.Media.TOP_MEDIA;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class MovieListFragment extends BaseFragment
    implements MoviesView,
    MovieAdapter.OnAdapterClickListener,
    OnMoreListener {


  public static final int INVALID_PAGE = -1;
  private static final int PAGE_SIZE = 20;
  private static final int MIN_ITEMS_THRESHOLD = 5;
  private static int currentPage = 1;

  private int listType;
  private static final String LIST_TYPE = "LIST_TYPE";

  @Inject MoviesUseCase moviesUseCase;
  @Inject SeriesUseCase seriesUseCase;

  @BindView(R.id.container_movie_list) FrameLayout container;
  @BindView(R.id.movie_list_progressImage) AVLoadingIndicatorView loadingPlaceHolder;
  @BindView(R.id.movie_list_recyclerview) SuperRecyclerView movieList;

  private MovieAdapter movieAdapter;
  private final MoviesPresenter moviesPresenter = new MoviesPresenter();

  public static MovieListFragment newInstance(int movieListType, int mediaType) {
    MovieListFragment moviesFragment = new MovieListFragment();
    Bundle args = new Bundle();
    args.putInt(LIST_TYPE, movieListType);
    args.putInt(MEDIA_TYPE, mediaType);
    moviesFragment.setArguments(args);
    return moviesFragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.movie_list_fragment, container, false);
    ButterKnife.bind(this, view);
    Utils.safeRegisterEventBus(this);

    listType = getArguments().getInt(LIST_TYPE);
    int mediaType = getArguments().getInt(MEDIA_TYPE);

    moviesPresenter.createPresenter(moviesUseCase, seriesUseCase, mediaType);
    moviesPresenter.attachView(this);

    setupRecyclerView();
    loadData(listType);

    return view;
  }

  private void setupRecyclerView() {
    movieList.getRecyclerView().setHasFixedSize(true);
    LinearLayoutManager layoutManger = new LinearLayoutManager(getContext());
    movieList.setLayoutManager(layoutManger);
    movieList.addItemDecoration(new MaterialViewPagerHeaderDecorator());
    movieAdapter = new MovieAdapter(Picasso.with(getContext()), this);
    movieList.setAdapter(movieAdapter);
    movieList.setupMoreListener(this, MIN_ITEMS_THRESHOLD);
    movieList.setRefreshListener(this::loadFeaturedMovieOnRefresh);
  }

  private void loadFeaturedMovieOnRefresh() {
    new Handler().postDelayed(() -> {
      WatchMediaValue featuredMovie = movieAdapter.getFeauturedMovie();
      EventBus.getDefault().post(new BackDropChangedEvent(featuredMovie));
      movieList.setRefreshing(false);
    }, 50);
  }

  private void loadData(int lisType) {
    switch (lisType) {
      case REC_MEDIA:
        moviesPresenter.loadNowPlaying();
        break;
      case POP_MEDIA:
        moviesPresenter.loadMostPopular();
        break;
      case TOP_MEDIA:
        moviesPresenter.loadTopRated();
        break;
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
    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
    movieList.setLoadingMore(false);
    Timber.e("Error");
  }

  @Override public void sendToListView(List<WatchMediaValue> watchMediaValuesList) {
    movieAdapter.addAllMedia(watchMediaValuesList);
    movieList.hideMoreProgress();
  }

  @Override protected void setupFragmentComponent() {
    ((CineApplication) getContext().getApplicationContext()).getRepositoryComponent().inject(this);
  }

  @Override public void onRealmMovieClick(WatchMediaValue mediaValue) {
    Timber.e("Clicked %s item", mediaValue.name());
    Toast.makeText(getContext(), mediaValue.name(), Toast.LENGTH_SHORT).show();
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onItemReselected(ItemReselectedEvent event) {
    if (event.itemIndex == TAB1) {
      movieList.scrollTo(0, 0);
    }
  }

  @SuppressLint("BinaryOperationInTimber")
  @Override
  public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
    movieList.showMoreProgress();
    currentPage++;
    if (overallItemsCount > currentPage * PAGE_SIZE) {
      currentPage = INVALID_PAGE;
    }
    switch (this.listType) {
      case REC_MEDIA:
        moviesPresenter.loadNextNowPlaying(currentPage);
        break;
      case POP_MEDIA:
        moviesPresenter.loadNextMostPopularPlaying(currentPage);
        break;
      case TOP_MEDIA:
        moviesPresenter.loadNextTopRated(currentPage);
        break;
    }
  }
}
