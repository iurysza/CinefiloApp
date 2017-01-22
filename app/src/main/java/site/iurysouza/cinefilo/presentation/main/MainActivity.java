package site.iurysouza.cinefilo.presentation.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rx.Subscription;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.di.modules.RepositoryModule;
import site.iurysouza.cinefilo.di.modules.UtilityModule;
import site.iurysouza.cinefilo.presentation.base.BaseActivity;
import site.iurysouza.cinefilo.presentation.medias.filter.FilterViewManager;
import site.iurysouza.cinefilo.presentation.medias.pager.MediaPagerFragment;

import static com.ncapdevi.fragnav.FragNavController.TAB1;
import static com.ncapdevi.fragnav.FragNavController.TAB2;

public class MainActivity extends BaseActivity implements BottomBarListener {

  public static final int BLUR_VIEW_HIDE_DELAY = 350;
  public static final int BLUR_VIEW_SHOW_DELAY = 450;
  @BindView(R.id.space_bottom_bar) SpaceNavigationView bottomBar;

  @Inject
  NavigationManager navigationManager;

  @BindView(R.id.fabtoolbar_toolbar) RelativeLayout fabtoolbarToolbar;
  @BindView(R.id.fabtoolbar) FABToolbarLayout fabtoolbar;

  private SharedViewsManager sharedViewsManager;
  private Subscription filterObserver;
  private FilterViewManager filterViewManager;

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
    filterViewManager = new FilterViewManager(this);
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

  @Override public void onBackPressed() {
    filterViewManager.onBackPressed();
    super.onBackPressed();
  }


}
