package site.iurysouza.cinefilo.presentation.medialist;

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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.domain.watchmedialist.MoviesWatchMediaListUseCase;
import site.iurysouza.cinefilo.domain.watchmedialist.SeriesWatchMediaListUseCase;
import site.iurysouza.cinefilo.presentation.base.BaseActivity;
import site.iurysouza.cinefilo.presentation.base.BaseFragment;
import site.iurysouza.cinefilo.presentation.main.FilterEvent;
import site.iurysouza.cinefilo.presentation.medialist.entity.WatchMediaValue;
import site.iurysouza.cinefilo.domain.watchmedialist.MediaFilter;
import site.iurysouza.cinefilo.util.Utils;
import timber.log.Timber;

import static com.ncapdevi.fragnav.FragNavController.TAB1;
import static site.iurysouza.cinefilo.presentation.medialist.pager.MediaPagerFragment.MEDIA_TYPE;
import static site.iurysouza.cinefilo.util.Constants.Media.POP_MEDIA;
import static site.iurysouza.cinefilo.util.Constants.Media.REC_MEDIA;
import static site.iurysouza.cinefilo.util.Constants.Media.TOP_MEDIA;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class MediaListFragment extends BaseFragment
    implements MediaView
{

  public static final int INVALID_PAGE = -1;
  private static final String LIST_TYPE = "LIST_TYPE";
  private static final int PAGE_SIZE = 20;
  private static final int MIN_ITEMS_THRESHOLD = 5;
  private static int currentPage = 1;

  private final MediaPresenter mediaPresenter = new MediaPresenter();

  @Inject MoviesWatchMediaListUseCase moviesUseCase;
  @Inject SeriesWatchMediaListUseCase seriesUseCase;

  @BindView(R.id.container_movie_list) FrameLayout container;
  @BindView(R.id.movie_list_progressImage) AVLoadingIndicatorView loadingPlaceHolder;
  @BindView(R.id.movie_list_recyclerview) SuperRecyclerView movieList;
  FloatingActionButton fabFilter;
  @BindView(R.id.empty_list_layout) RelativeLayout emptyListLayout;
  @BindView(R.id.empty_list_background_image) ImageView warningCharacterImage;
  @BindView(R.id.empty_list_background_text) TextView warningCharacterQuote;
  private LinearLayoutManager layoutManger;

  private int listType;
  private MediaAdapter mediaAdapter;
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
    View view = inflater.inflate(R.layout.media_list_fragment, container, false);
    ButterKnife.bind(this, view);
    Utils.safeRegisterEventBus(this);
    fabFilter = (FloatingActionButton) getActivity().findViewById(R.id.fabtoolbar_fab);

    listType = getArguments().getInt(LIST_TYPE);
    int mediaType = getArguments().getInt(MEDIA_TYPE);

    mediaPresenter.createPresenter(moviesUseCase, seriesUseCase, mediaType);
    mediaPresenter.attachView(this);

    setupRecyclerView();
    loadData(listType);

    return view;
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onFilterApplied(FilterEvent event) {
    filter = event.filter;
    if (isMenuVisible()) {
      if (filter == null) {
        disableFilter();
      } else {
        applyFilter();
      }
    }
  }

  private void disableFilter() {
    movieList.getRecyclerView().smoothScrollToPosition(0);
    mediaAdapter.clear();
    loadData(listType);
  }

  private void applyFilter() {
    List<WatchMediaValue> filteredList = mediaAdapter.filterAdapter(filter);
    mediaAdapter.replaceList(filteredList);
    currentPage = 0;
  }

  private void setupRecyclerView() {
    movieList.getRecyclerView().setHasFixedSize(false);
    layoutManger = new LinearLayoutManager(getContext());
    movieList.setLayoutManager(layoutManger);
    mediaAdapter = new MediaAdapter(Picasso.with(getContext()));
    movieList.setAdapter(mediaAdapter);
    movieList.setupMoreListener(createOnMoreListener(), MIN_ITEMS_THRESHOLD);
    movieList.getRecyclerView().addOnScrollListener(createFabScrollBehavior());
  }

  @NonNull private RecyclerView.OnScrollListener createFabScrollBehavior() {
    return new RecyclerView.OnScrollListener() {

      @Override
      public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

        //Tocheck if  recycler is on bottom
        boolean isNotAtBottom =
            layoutManger
                .findLastCompletelyVisibleItemPosition() != mediaAdapter.getItemCount() - 1;
        if (newState == RecyclerView.SCROLL_STATE_IDLE && isNotAtBottom) {
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
    currentPage = 1;
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
    appInstance.releaseMediaListComponent();
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
    if (watchMediaValuesList.isEmpty() && mediaAdapter.getItemCount() == 0) {
      showEmptyListWarning();
      movieList.setVisibility(View.GONE);
    } else {
      emptyListLayout.setVisibility(View.GONE);
      movieList.setVisibility(View.VISIBLE);
    }
  }

  private void showEmptyListWarning() {
    EmptyListCharacter randomCharacter = EmptyListCharacter.getRandomCharacter();
    warningCharacterImage
        .setImageDrawable(
            getResources().getDrawable(randomCharacter.getCharacterArt()));
    warningCharacterQuote
        .setText(
            getResources().getString(randomCharacter.getCharacterQuote()));
    emptyListLayout.setVisibility(View.VISIBLE);
  }

  @Override protected void setupFragmentComponent() {
    appInstance.createMediaListComponent((BaseActivity) getActivity()).inject(this);
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onItemReselected(ItemReselectedEvent event) {
    if (event.itemIndex == TAB1) {
      layoutManger.scrollToPosition(0);
    }
  }
}
