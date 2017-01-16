package site.iurysouza.cinefilo.presentation.medias;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.github.mmin18.widget.RealtimeBlurView;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import rx.Subscription;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.domain.MoviesUseCase;
import site.iurysouza.cinefilo.domain.SeriesUseCase;
import site.iurysouza.cinefilo.domain.entity.WatchMediaValue;
import site.iurysouza.cinefilo.presentation.CineApplication;
import site.iurysouza.cinefilo.presentation.base.BaseFragment;
import site.iurysouza.cinefilo.presentation.medias.filter.FilterViewManager;
import site.iurysouza.cinefilo.presentation.medias.filter.GenderEnum;
import site.iurysouza.cinefilo.util.CineSubscriber;
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
    MediaAdapter.OnAdapterClickListener,
    OnMoreListener {

  public static final int INVALID_PAGE = -1;
  private static final int PAGE_SIZE = 20;
  private static final int MIN_ITEMS_THRESHOLD = 5;
  private static final String LIST_TYPE = "LIST_TYPE";
  private static int currentPage = 1;
  private final MediaPresenter mediaPresenter = new MediaPresenter();
  @Inject MoviesUseCase moviesUseCase;
  @Inject SeriesUseCase seriesUseCase;

  @BindView(R.id.container_movie_list) FrameLayout container;
  @BindView(R.id.movie_list_progressImage) AVLoadingIndicatorView loadingPlaceHolder;
  @BindView(R.id.movie_list_recyclerview) SuperRecyclerView movieList;
  FloatingActionButton fabFilter;
  FABToolbarLayout fabToolbarLayout;
  RealtimeBlurView blurView;
  SpaceNavigationView navigationView;
  private int listType;
  private MediaAdapter mediaAdapter;
  private View view;
  private Subscription filterObserver;

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
    fabToolbarLayout = (FABToolbarLayout) getActivity().findViewById(R.id.fabtoolbar);
    blurView = (RealtimeBlurView) getActivity().findViewById(R.id.main_blurred_view);
    navigationView = (SpaceNavigationView) getActivity().findViewById(R.id.space_bottom_bar);

    setupRecyclerView();
    loadData(listType);

    fabFilter.setOnClickListener(v -> showFilterView());
    blurView.setOnClickListener(v -> hideFilterView());

    return view;
  }

  private void hideFilterView() {
    fabToolbarLayout.hide();
    blurView.postDelayed(() -> blurView.setVisibility(View.GONE), 150);
  }

  private void showFilterView() {
    fabToolbarLayout.show();
    blurView.postDelayed(() -> blurView.setVisibility(View.VISIBLE), 150);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    this.view = view;
    super.onViewCreated(view, savedInstanceState);

    FilterViewManager filterViewManager = new FilterViewManager(getActivity());

    if (filterObserver != null && filterObserver.isUnsubscribed()) {
      filterObserver.unsubscribe();
    }
    filterObserver = filterViewManager.getFilterSubjectAsObservable().subscribe(
        new CineSubscriber<List<GenderEnum>>() {
          @Override public void onNext(List<GenderEnum> genderEnumList) {
            super.onNext(genderEnumList);
            hideFilterView();
            if (genderEnumList != null) {
              Timber.e("Got Genders from filter: %s", genderEnumList);
              ColorStateList colorStateList =
                  getResources().getColorStateList(R.color.filter_fab_on);
              fabFilter.setBackgroundTintList(
                  colorStateList);
              ViewCompat.setBackgroundTintList(fabFilter,
                  colorStateList);
              fabFilter.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.filter_fab_on));
              if (genderEnumList.isEmpty()) {
                fabFilter.setBackgroundTintList(
                    getResources().getColorStateList(R.color.filter_fab_off));
              }
            }
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            hideFilterView();
          }
        });
  }

  private void setupRecyclerView() {
    movieList.getRecyclerView().setHasFixedSize(true);
    LinearLayoutManager layoutManger = new LinearLayoutManager(getContext());
    movieList.setLayoutManager(layoutManger);
    movieList.addItemDecoration(new MaterialViewPagerHeaderDecorator());
    mediaAdapter = new MediaAdapter(Picasso.with(getContext()), this);
    movieList.setAdapter(mediaAdapter);
    movieList.setupMoreListener(this, MIN_ITEMS_THRESHOLD);
    movieList.setRefreshListener(this::refreshFeaturedMedia);
    movieList.getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (dy > 0 || dy < 0 && fabFilter.isShown()) {
          fabFilter.hide();
        }
      }

      @Override
      public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

        if (newState == RecyclerView.SCROLL_STATE_IDLE && navigationView.getTranslationY() != 0) {
          fabFilter.show();
        }

        super.onScrollStateChanged(recyclerView, newState);
      }
    });
  }

  private void refreshFeaturedMedia() {
    new Handler().postDelayed(() -> {
      WatchMediaValue featuredMovie = mediaAdapter.getFeauturedMovie();
      EventBus.getDefault().post(new BackDropChangedEvent(featuredMovie));
      movieList.setRefreshing(false);
    }, 50);
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
    refreshFeaturedMedia();
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
}
