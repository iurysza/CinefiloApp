package site.iurysouza.cinefilo.presentation.main;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.github.mmin18.widget.RealtimeBlurView;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import mehdi.sakout.fancybuttons.FancyButton;
import org.greenrobot.eventbus.EventBus;
import rx.Subscription;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.di.modules.RepositoryModule;
import site.iurysouza.cinefilo.di.modules.UtilityModule;
import site.iurysouza.cinefilo.presentation.base.BaseActivity;
import site.iurysouza.cinefilo.presentation.medias.filter.FilterViewManager;
import site.iurysouza.cinefilo.presentation.medias.filter.GenderEnum;
import site.iurysouza.cinefilo.presentation.medias.pager.MediaPagerFragment;
import site.iurysouza.cinefilo.util.CineSubscriber;

import static com.ncapdevi.fragnav.FragNavController.TAB1;
import static com.ncapdevi.fragnav.FragNavController.TAB2;
import static site.iurysouza.cinefilo.util.ImageUtils.changeIconColor;

public class MainActivity extends BaseActivity implements BottomBarListener {

  public static final int BLUR_VIEW_HIDE_DELAY = 350;
  public static final int BLUR_VIEW_SHOW_DELAY = 450;
  @BindView(R.id.space_bottom_bar) SpaceNavigationView bottomBar;

  @Inject
  NavigationManager navigationManager;
  @BindView(R.id.fabtoolbar_fab) FloatingActionButton filterFab;
  @BindView(R.id.main_blurred_view) RealtimeBlurView blurredView;
  @BindView(R.id.filter_btn_close) FancyButton filterBtnClose;
  @BindView(R.id.filter_view_header) FrameLayout filterViewHeader;
  @BindView(R.id.filter_btn_apply) FancyButton filterBtnApply;
  @BindView(R.id.fabtoolbar_toolbar) RelativeLayout fabtoolbarToolbar;
  @BindView(R.id.fabtoolbar) FABToolbarLayout fabtoolbar;

  private SharedViewsManager sharedViewsManager;
  private Subscription filterObserver;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    bottomBar.initWithSaveInstanceState(savedInstanceState);
    bottomBar.addSpaceItem(
        new SpaceItem(getString(R.string.bottombar_title_movies), R.drawable.ic_drawer_movies));
    bottomBar.addSpaceItem(
        new SpaceItem(getString(R.string.bottombar_title_series), R.drawable.ic_tv_series));
    bottomBar.setActiveCentreButtonIconColor(ContextCompat.getColor(this, R.color.appWhite));
    bottomBar.setInActiveCentreButtonIconColor(ContextCompat.getColor(this, R.color.appWhite));

    List<Fragment> fragments = new ArrayList<>();
    fragments.add(MediaPagerFragment.newInstance(TAB1));
    fragments.add(MediaPagerFragment.newInstance(TAB2));

    navigationManager.setupFragNavController(fragments, this);
    bottomBar.setSpaceOnClickListener(navigationManager);
    sharedViewsManager = SharedViewsManager.createSharedViewsManager(this, bottomBar);
    FilterViewManager filterViewManager = new FilterViewManager(this);
    subscribeToFilterManager(filterViewManager);
    filterFab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
  }

  private void subscribeToFilterManager(FilterViewManager filterViewManager) {
    if (filterObserver != null && filterObserver.isUnsubscribed()) {
      filterObserver.unsubscribe();
    }
    filterObserver = filterViewManager.getFilterSubjectAsObservable().subscribe(
        new CineSubscriber<GenderEnum>() {
          @Override public void onNext(GenderEnum genderEnumList) {
            super.onNext(genderEnumList);
            EventBus.getDefault().post(new FilterEvent(genderEnumList));
            hideFilterView();
            changeFilterButtonColor(genderEnumList);
          }

          @Override public void onError(Throwable e) {
            super.onError(e);
            hideFilterView();
          }
        });
  }

  private void changeFilterButtonColor(GenderEnum genres) {
    new Handler().postDelayed(() -> {
      if (genres == null) {
        filterFab.setBackgroundTintList(
            ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
        filterFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_filter_on));
        return;
      }
      Drawable showIcon = changeIconColor(this, genres.getGenreIconRes(), R.color.appWhite);
      filterFab.setImageDrawable(showIcon);
      filterFab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
    }, BLUR_VIEW_HIDE_DELAY);
  }

  @Override protected void setupActivityComponent(Bundle savedInstanceState) {
    appInstance.createRepositoryComponent(new RepositoryModule(),
        new UtilityModule(this)).inject(this);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    sharedViewsManager.withMenu(menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    appInstance.releaseRepositoryComponent();
  }

  @Override public void onTabSelected(Fragment currentFrag) {
    sharedViewsManager.updateViewsForFragment(currentFrag);
  }

  private void hideFilterView() {
    fabtoolbar.hide();
    blurredView.postDelayed(() -> blurredView.setVisibility(View.GONE), BLUR_VIEW_HIDE_DELAY);
  }

  private void showFilterView() {
    fabtoolbar.show();
    blurredView.postDelayed(() -> blurredView.setVisibility(View.VISIBLE), BLUR_VIEW_SHOW_DELAY);
  }

  @Override public void onBackPressed() {
    if (blurredView.isShown()) {
      hideFilterView();
      return;
    }
    super.onBackPressed();
  }

  @OnClick({
      R.id.filter_btn_close,
      R.id.filter_btn_apply,
      R.id.fabtoolbar_fab,
      R.id.main_blurred_view
  })
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.filter_btn_close:
      case R.id.filter_btn_apply:
      case R.id.main_blurred_view:
        hideFilterView();
        break;
      case R.id.fabtoolbar_fab:
        showFilterView();
        break;
    }
  }
}
