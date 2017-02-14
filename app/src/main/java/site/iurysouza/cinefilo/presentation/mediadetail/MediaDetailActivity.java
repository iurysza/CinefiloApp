package site.iurysouza.cinefilo.presentation.mediadetail;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.squareup.picasso.Picasso;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.domain.entity.WatchMediaValue;
import site.iurysouza.cinefilo.model.data.entity.MovieDetailValue;
import site.iurysouza.cinefilo.presentation.base.BaseActivity;
import site.iurysouza.cinefilo.presentation.mediadetail.similarmovies.SimilarMoviesAdapter;
import site.iurysouza.cinefilo.util.ImageUtils;
import site.iurysouza.cinefilo.util.Utils;
import timber.log.Timber;

public class MediaDetailActivity extends BaseActivity implements MovieDetailView {

  public static final String WATCH_MEDIA_DATA = "WATCH_MEDIA_DATA";
  public static final String REMOVE_TRANSITIONS = "REMOVE_TRANSITIONS";
  private static final int MIN_ITEMS_THRESHOLD = 5;


  @BindView(R.id.image_backdrop_detail_media) KenBurnsView imageBackdropDetailMedia;
  @BindView(R.id.toolbar_detail_media) Toolbar toolbarDetailMedia;
  @BindView(R.id.collapsing_detail_media) CollapsingToolbarLayout collapsingDetailMedia;
  @BindView(R.id.appbar_detail_media) AppBarLayout appbarDetailMedia;
  @BindView(R.id.media_detail_title_text) TextView mediaDetailTitleText;
  @BindView(R.id.media_detail_rating) RatingBar mediaDetailRating;
  @BindView(R.id.media_detail_rating_text) TextView mediaDetailRatingText;
  @BindView(R.id.media_detail_date) TextView mediaDetailDate;
  @BindView(R.id.media_detail_playtime) TextView mediaDetailPlaytime;
  @BindView(R.id.media_detail_genre_text) TextView mediaDetailGenreText;
  @BindView(R.id.media_detail_genre) CardView mediaDetailGenre;
  @BindView(R.id.media_detail_tabs) TabLayout mediaDetailTabs;
  @BindView(R.id.media_detail_viewpager) WrappingViewPager mediaDetailViewpager;
  @BindView(R.id.container_fragment_movies) LinearLayout containerFragmentMovies;
  @BindView(R.id.media_detail_card) CardView mediaDetailCard;
  @BindView(R.id.media_detail_picture_imageview) ImageView mediaDetailPictureImageview;
  @BindView(R.id.media_detail_picture_card) CardView mediaDetailPictureCard;
  @BindView(R.id.media_detail_like_fab) FloatingActionButton likeFab;
  @BindView(R.id.media_detail_similar_movies) SuperRecyclerView similiarMoviesList;

  @Inject MovieDetailPresenter presenter;
  @BindView(R.id.detail_backdrop_play_btn) ImageView detailBackdropPlayBtn;
  @BindView(R.id.media_detail_similar_title) TextView mediaDetailSimilarTitle;
  @BindView(R.id.media_detail_like_overlay) FrameLayout mediaDetailLikeOverlay;
  @BindView(R.id.media_detail_coord) CoordinatorLayout mediaDetailCoord;

  private WatchMediaValue watchMedia;
  private MediaDetailPagerAdapter pagerAdapter;
  private int similarMoviesPage = 0;
  private SimilarMoviesAdapter adapter;
  private boolean movieLiked = false;

  public static Intent getStartIntent(Context context, WatchMediaValue watchMedia) {
    Intent intent = new Intent(context, MediaDetailActivity.class);
    Bundle bundle = new Bundle();
    bundle.putParcelable(WATCH_MEDIA_DATA, watchMedia);
    intent.putExtra(WATCH_MEDIA_DATA, bundle);
    intent.putExtra(REMOVE_TRANSITIONS, true);
    return intent;
  }

  public static Intent getStartIntentFromSimMovies(Context context, WatchMediaValue watchMedia) {
    Intent intent = new Intent(context, MediaDetailActivity.class);
    Bundle bundle = new Bundle();
    bundle.putParcelable(WATCH_MEDIA_DATA, watchMedia);
    intent.putExtra(WATCH_MEDIA_DATA, bundle);
    intent.putExtra(REMOVE_TRANSITIONS, false);
    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.media_detail_activity);
    ButterKnife.bind(this);
    setupActionBar();
    Bundle bundle = getIntent().getBundleExtra(WATCH_MEDIA_DATA);

    watchMedia = bundle.getParcelable(WATCH_MEDIA_DATA);
    presenter.attachView(this);
    int movieId = (int) watchMedia.id();

    presenter.getMovieDetailById(movieId);
    presenter.getMoviesSimilarTo(movieId, 1);
    postponeEnterTransition();
    setTransitionToViews();
    scheduleStartPostponedTransition(mediaDetailCard);

    mediaDetailTabs.setupWithViewPager(mediaDetailViewpager);
    pagerAdapter = new MediaDetailPagerAdapter(this);

    mediaDetailViewpager.setAdapter(pagerAdapter);
    setupSimilarMoviesList();
    bindViewToData(watchMedia);

    appbarDetailMedia.addOnOffsetChangedListener(new AppBarStateChangeListener() {
      @Override
      public void onStateChanged(AppBarLayout appBarLayout, State state) {
        if (state == State.COLLAPSED) {
          toolbarDetailMedia.setTitle(watchMedia.name());
        } else {
          toolbarDetailMedia.setTitle("");
          collapsingDetailMedia.setTitle("");
        }
      }
    });
  }

  private void setTransitionToViews() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      String posterTransition = getResources().getString(R.string.poster_card_transition);
      String titleTransition = getResources().getString(R.string.media_title_transition);
      String cardTransition = getResources().getString(R.string.detail_card_transition);
      String genreTransition = getResources().getString(R.string.detail_genre_transition);
      mediaDetailGenre.setTransitionName(genreTransition);
      mediaDetailPictureCard.setTransitionName(posterTransition);
      mediaDetailTitleText.setTransitionName(titleTransition);
      mediaDetailCard.setTransitionName(cardTransition);
    }
  }

  @Override public void onActivityReenter(int resultCode, Intent data) {
    super.onActivityReenter(resultCode, data);
    postponeEnterTransition();
    setTransitionToViews();
    scheduleStartPostponedTransition(mediaDetailCard);
  }

  private void scheduleStartPostponedTransition(final View sharedElement) {
    sharedElement.getViewTreeObserver().addOnPreDrawListener(
        new ViewTreeObserver.OnPreDrawListener() {
          @Override
          public boolean onPreDraw() {
            boolean shouldRemoveTransitions =
                getIntent().getBooleanExtra(REMOVE_TRANSITIONS, false);
            sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
            startPostponedEnterTransition();
            if (shouldRemoveTransitions) {
              mediaDetailGenre.setTransitionName(null);
              mediaDetailPictureCard.setTransitionName(null);
              mediaDetailTitleText.setTransitionName(null);
              mediaDetailCard.setTransitionName(null);
            }
            return true;
          }
        });
  }

  private void setupSimilarMoviesList() {
    LinearLayoutManager layout =
        new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    similiarMoviesList.setLayoutManager(
        layout);
    adapter = new SimilarMoviesAdapter(this);
    similiarMoviesList.setAdapter(adapter);
    SnapHelper snapHelper = new LinearSnapHelper();
    snapHelper.attachToRecyclerView(similiarMoviesList.getRecyclerView());
    similiarMoviesList.setupMoreListener(onMoreSimilarMoviesAsked(), MIN_ITEMS_THRESHOLD);
  }

  private OnMoreListener onMoreSimilarMoviesAsked() {
    return (overallItemsCount, itemsBeforeMore, maxLastVisiblePosition) -> {
      similarMoviesPage++;
      presenter.getMoviesSimilarTo((int) watchMedia.id(), similarMoviesPage);
    };
  }

  @Override protected void setupActivityComponent(Bundle savedInstanceState) {
    appInstance.createMediaDetailComponent(this);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    appInstance.releaseDetailComponent();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        NavUtils.navigateUpFromSameTask(this);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void setupActionBar() {
    setSupportActionBar(toolbarDetailMedia);
    ActionBar supportActionBar = getSupportActionBar();
    supportActionBar.setDisplayHomeAsUpEnabled(true);
    supportActionBar.setTitle("");
    supportActionBar.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);

  }

  private void bindViewToData(WatchMediaValue watchMedia) {
    String posterPath = watchMedia.posterPath();
    String name = watchMedia.name();
    String posterUrl = ImageUtils.getPosterUrl(posterPath);
    Picasso.with(this)
        .load(posterUrl)
        .fit()
        .placeholder(R.drawable.placeholder)
        .into(mediaDetailPictureImageview);

    mediaDetailTitleText.setText(name);
    String dateText = Utils.parseDateText(watchMedia.releaseDate());
    mediaDetailDate.setText(dateText);
    Double voteAverage = watchMedia.voteAverage();
    float movieRating = (float) (voteAverage / 2);
    mediaDetailRating.setRating(movieRating);
    mediaDetailRatingText.setText(String.valueOf(voteAverage));
    pagerAdapter.updateOverView(watchMedia.overview());
  }

  @Override public void updateMovieData(MovieDetailValue movieDetailValue) {
    Timber.e("MOVIE DETAIL LOADED: %s", movieDetailValue);
    String runTime = String.valueOf(movieDetailValue.runTime().intValue()) + "min";
    mediaDetailPlaytime.setText(runTime);

    HashMap<String, Integer> stringIntegerHashMap = movieDetailValue.genreList();
    String genreText = "";
    for (String genreName : stringIntegerHashMap.keySet()) {
      genreText = genreText + ", " + genreName;
    }
    mediaDetailGenreText.setText(genreText.substring(2));
    movieDetailValue.tagLine();
    DetailStyleManager
        .builder()
        .context(this)
        .appBarLayout(appbarDetailMedia)
        .backDropPath(movieDetailValue.backdropPath())
        .posterPath(movieDetailValue.posterPath())
        .collapsingToolbarLayout(collapsingDetailMedia)
        .kenBurnsView(imageBackdropDetailMedia)
        .toolbar(toolbarDetailMedia)
        .tabLayout(mediaDetailTabs)
        .build()
        .loadBitmapAndCreateColorPallete();

    pagerAdapter.updateOverViewPage(movieDetailValue);
  }

  @Override public void showSimilarMovies(List<WatchMediaValue> mediaValues) {
    Timber.e("Similar movies arrived: %s", mediaValues.size());
    adapter.addSimilarMovies(mediaValues);
  }

  @Override public void showErrorWarning() {

  }
  @Override public RxAppCompatActivity getRxAppCompatActivity() {
    return this;
  }


  @OnClick(R.id.media_detail_like_fab) public void onClick(View view) {
    if (movieLiked) {
      mediaDetailLikeOverlay.setBackgroundColor(getResources().getColor(R.color.white));
    } else {
      mediaDetailLikeOverlay.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    int x = mediaDetailPictureImageview.getRight() / 2;
    int y = mediaDetailPictureImageview.getBottom();

    int startRadius = 0;
    int endRadius =
        (int) Math.hypot(mediaDetailPictureCard.getWidth(), mediaDetailPictureCard.getHeight());

    Animator anim = ViewAnimationUtils.createCircularReveal(
        mediaDetailLikeOverlay,
        x,
        y,
        startRadius,
        endRadius);

    mediaDetailLikeOverlay.setVisibility(View.VISIBLE);
    anim
        .setDuration(300)
        .addListener(new Animator.AnimatorListener() {
          @Override public void onAnimationStart(Animator animation) {
          }

          @Override public void onAnimationEnd(Animator animation) {
            removeOverlayWithReveal();
          }

          @Override public void onAnimationCancel(Animator animation) {

          }

          @Override public void onAnimationRepeat(Animator animation) {

          }
        });
    anim.start();
  }

  private void setLikedColor() {
    likeFab.setImageDrawable(ContextCompat.getDrawable(MediaDetailActivity.this,
        R.drawable.ic_favorite_red_500_24dp));
    int whiteColor = MediaDetailActivity.this.getResources().getColor(R.color.white);
    likeFab.setBackgroundTintList(ColorStateList.valueOf(whiteColor));
  }

  private void setUnlikedColor() {
    likeFab.setImageDrawable(ContextCompat.getDrawable(MediaDetailActivity.this,
        R.drawable.ic_favorite_border_white_24dp));
    int whiteColor = MediaDetailActivity.this.getResources().getColor(R.color.colorAccent);
    likeFab.setBackgroundTintList(ColorStateList.valueOf(whiteColor));

  }

  private void removeOverlayWithReveal() {
    int x = mediaDetailPictureImageview.getRight() / 2;
    int y = mediaDetailPictureImageview.getBottom();

    int endRadius = 0;
    int startRadius =
        (int) Math.hypot(mediaDetailPictureCard.getWidth(), mediaDetailPictureCard.getHeight());

    Animator anim = ViewAnimationUtils.createCircularReveal(
        mediaDetailLikeOverlay,
        x,
        y,
        startRadius,
        endRadius);
    anim.setStartDelay(150);
    anim
        .setDuration(450)
        .addListener(new Animator.AnimatorListener() {
          @Override public void onAnimationStart(Animator animation) {

          }

          @Override public void onAnimationEnd(Animator animation) {
            mediaDetailLikeOverlay.setVisibility(View.GONE);
            movieLiked = !movieLiked;
            createLikedSnackBar(movieLiked);
            if (movieLiked) {
              setLikedColor();
            } else {
              setUnlikedColor();
            }
          }

          @Override public void onAnimationCancel(Animator animation) {

          }

          @Override public void onAnimationRepeat(Animator animation) {

          }
        });
    anim.start();
  }

  private void createLikedSnackBar(boolean movieLiked) {
    String text;
    if (movieLiked) {
      text = getString(R.string.detail_activity_liked_snackbar);
    } else {
      text = getString(R.string.detail_activity_liked_removed);
    }

    Snackbar.make(mediaDetailCoord, text, Snackbar.LENGTH_SHORT).show();
  }
}
