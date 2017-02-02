package site.iurysouza.cinefilo.presentation.mediadetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;
import org.joda.time.DateTime;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.di.modules.MediaDetailModule;
import site.iurysouza.cinefilo.domain.entity.WatchMediaValue;
import site.iurysouza.cinefilo.model.data.entity.MovieDetailValue;
import site.iurysouza.cinefilo.presentation.base.BaseActivity;
import site.iurysouza.cinefilo.util.ImageUtils;
import timber.log.Timber;

public class MediaDetailActivity extends BaseActivity implements MovieDetailView {

  public static final String WATCH_MEDIA_DATA = "WATCH_MEDIA_DATA";
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
  @Inject
  MovieDetailPresenter presenter;
  private WatchMediaValue watchMedia;
  private MediaDetailPagerAdapter pagerAdapter;

  public static Intent getStartIntent(Context context, WatchMediaValue watchMedia) {
    Intent intent = new Intent(context, MediaDetailActivity.class);
    Bundle bundle = new Bundle();
    bundle.putParcelable(WATCH_MEDIA_DATA, watchMedia);
    intent.putExtra(WATCH_MEDIA_DATA, bundle);
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
    presenter.getMovieDetailById((int) watchMedia.id());
    mediaDetailTabs.setupWithViewPager(mediaDetailViewpager);
    pagerAdapter = new MediaDetailPagerAdapter(this);
    mediaDetailViewpager.setAdapter(pagerAdapter);


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

  @Override protected void setupActivityComponent(Bundle savedInstanceState) {
    appInstance.createMediaDetailComponent(new MediaDetailModule()).inject(this);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        supportFinishAfterTransition();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void setupActionBar() {
    setSupportActionBar(toolbarDetailMedia);
    ActionBar supportActionBar = getSupportActionBar();
    supportActionBar.setDisplayHomeAsUpEnabled(true);
    supportActionBar.setTitle("");
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
    DateTime movieReleaseDate = new DateTime(watchMedia.releaseDate());
    mediaDetailDate.setText("(" + movieReleaseDate.getYear() + ")");

    Double voteAverage = watchMedia.voteAverage();
    float movieRating = (float) (voteAverage / 2);
    mediaDetailRating.setRating(movieRating);
    mediaDetailRatingText.setText(String.valueOf(voteAverage));
  }

  @Override public void updateMovieData(MovieDetailValue movieDetailValue) {
    Timber.e("MOVIE UPDATED: %s", movieDetailValue);
    mediaDetailPlaytime.setText(String.valueOf(movieDetailValue.runTime().intValue()));

    DetailStyleManager
        .builder()
        .context(this)
        .backDropPath(movieDetailValue.backdropPath())
        .collapsingToolbarLayout(collapsingDetailMedia)
        .kenBurnsView(imageBackdropDetailMedia)
        .toolbar(toolbarDetailMedia)
        .tabLayout(mediaDetailTabs)
        .build()
        .loadBitmapAndCreateColorPallete();

    pagerAdapter.updateOverViewPage(movieDetailValue);

  }

  @Override public void showErrorWarning() {

  }
}
