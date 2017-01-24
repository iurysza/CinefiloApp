package site.iurysouza.cinefilo.presentation.medias;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import rx.Subscription;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.domain.MediaFilter;
import site.iurysouza.cinefilo.domain.MoviesUseCase;
import site.iurysouza.cinefilo.domain.SeriesUseCase;
import site.iurysouza.cinefilo.domain.entity.WatchMediaValue;
import site.iurysouza.cinefilo.presentation.CineApplication;
import site.iurysouza.cinefilo.presentation.base.BaseFragment;
import site.iurysouza.cinefilo.presentation.main.FilterEvent;
import site.iurysouza.cinefilo.presentation.medias.filter.GenderEnum;
import site.iurysouza.cinefilo.util.Utils;
import timber.log.Timber;

import static com.ncapdevi.fragnav.FragNavController.TAB1;
import static site.iurysouza.cinefilo.presentation.medias.pager.MediaPagerFragment.MEDIA_TYPE;
import static site.iurysouza.cinefilo.util.Constants.Media.POP_MEDIA;
import static site.iurysouza.cinefilo.util.Constants.Media.REC_MEDIA;
import static site.iurysouza.cinefilo.util.Constants.Media.TOP_MEDIA;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class MediaListFragment extends BaseFragment
    implements MediaView,
    MediaAdapter.OnAdapterClickListener {

  public static final int INVALID_PAGE = -1;
  private static final String LIST_TYPE = "LIST_TYPE";
  private static final int PAGE_SIZE = 20;
  private static final int MIN_ITEMS_THRESHOLD = 5;
  private static int currentPage = 1;

  private final MediaPresenter mediaPresenter = new MediaPresenter();

  @Inject MoviesUseCase moviesUseCase;
  @Inject SeriesUseCase seriesUseCase;

  @BindView(R.id.container_movie_list) FrameLayout container;
  @BindView(R.id.movie_list_progressImage) AVLoadingIndicatorView loadingPlaceHolder;
  @BindView(R.id.movie_list_recyclerview) SuperRecyclerView movieList;
  FloatingActionButton fabFilter;
  SpaceNavigationView navigationView;
  private int listType;
  private MediaAdapter mediaAdapter;
  private Subscription filterObserver;
  private LinearLayoutManager layoutManger;
  private MediaFilter filter = null;

  public static MediaListFragment newInstance(int movieListType, int mediaType) {
    MediaListFragment moviesFragment = new MediaListFragment();
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

    mediaPresenter.createPresenter(moviesUseCase, seriesUseCase, mediaType);
    mediaPresenter.attachView(this);

    fabFilter = (FloatingActionButton) getActivity().findViewById(R.id.fabtoolbar_fab);
    navigationView = (SpaceNavigationView) getActivity().findViewById(R.id.space_bottom_bar);

    setupRecyclerView();
    loadData(listType);

    return view;
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onFilterApplied(FilterEvent event) {
    MediaFilter filter = event.filter;
    if (isMenuVisible()) {
      if (filter == null) {
        disableFilter();
      } else {
        applyFilter(event);
      }
    }
  }

  private void disableFilter() {
    movieList.getRecyclerView().smoothScrollToPosition(0);
    mediaAdapter.clear();
    loadData(listType);
  }

  private void applyFilter(FilterEvent event) {
    filter = event.filter;
    List<GenderEnum> genderList = filter.getGenderList();
    List<WatchMediaValue> filteredList = mediaAdapter.getAdapterListFilteredBy(genderList);
    mediaAdapter.replaceList(filteredList);
    currentPage = 0;
  }

  private void setupRecyclerView() {
    movieList.getRecyclerView().setHasFixedSize(true);
    layoutManger = new LinearLayoutManager(getContext());
    movieList.setLayoutManager(layoutManger);
    mediaAdapter = new MediaAdapter(Picasso.with(getContext()), this);
    movieList.setAdapter(mediaAdapter);
    movieList.setupMoreListener(createOnMoreListener(), MIN_ITEMS_THRESHOLD);
    movieList.getRecyclerView().addOnScrollListener(createFabScrollBehavior());
  }

  @NonNull private RecyclerView.OnScrollListener createFabScrollBehavior() {
    return new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
          fabFilter.show();
        } else {
          fabFilter.hide();
        }

        super.onScrollStateChanged(recyclerView, newState);
      }
    };
  }

  @NonNull private OnMoreListener createOnMoreListener() {
    return (overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> {
      currentPage++;
      if (overallItemsCount > currentPage * PAGE_SIZE && filter == null) {
        currentPage = INVALID_PAGE;
      }
      if (filter != null) {
        mediaPresenter.loadFiltered(currentPage, filter);
      } else {
        switch (listType) {
          case REC_MEDIA:
            mediaPresenter.loadNextNowPlaying(currentPage);
            break;
          case POP_MEDIA:
            mediaPresenter.loadNextMostPopularPlaying(currentPage);
            break;
          case TOP_MEDIA:
            mediaPresenter.loadNextTopRated(currentPage);
            break;
        }
      }
    };
  }

  @Override public void showMoreProgress() {
    movieList.showMoreProgress();
  }

  private void loadData(int lisType) {
    switch (lisType) {
      case REC_MEDIA:
        mediaPresenter.loadNowPlaying();
        break;
      case POP_MEDIA:
        mediaPresenter.loadMostPopular();
        break;
      case TOP_MEDIA:
        mediaPresenter.loadTopRated();
        break;
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    mediaPresenter.dettachView();
  }

  @Override public void showLoadingIndicator() {
    movieList.setVisibility(View.GONE);
    loadingPlaceHolder.show();
    Timber.e("Loading");
  }

  @Override public void hideLoadingIndicator() {
    movieList.setVisibility(View.VISIBLE);
    movieList.hideMoreProgress();
    loadingPlaceHolder.hide();
    Timber.e("Finished Loading");
  }

  @Override public void showErrorIndicator() {
    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
    movieList.setLoadingMore(false);
    Timber.e("Error");
  }

  @Override public void sendToListView(List<WatchMediaValue> watchMediaValuesList) {
    mediaAdapter.addAllMedia(watchMediaValuesList);
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
      layoutManger.scrollToPosition(0);
    }
  }
}
