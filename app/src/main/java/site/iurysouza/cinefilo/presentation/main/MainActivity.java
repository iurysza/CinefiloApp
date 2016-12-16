package site.iurysouza.cinefilo.presentation.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.di.modules.RepositoryModule;
import site.iurysouza.cinefilo.di.modules.UtilityModule;
import site.iurysouza.cinefilo.presentation.base.BaseActivity;
import site.iurysouza.cinefilo.presentation.home.HomeFragment;
import site.iurysouza.cinefilo.presentation.movies.pager.MoviesFragment;

public class MainActivity extends BaseActivity implements BottomBarListener {

  @BindView(R.id.space) SpaceNavigationView bottomBar;

  @Inject
  NavigationManager navigationManager;

  private SharedViewsManager sharedViewsManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    bottomBar.initWithSaveInstanceState(savedInstanceState);
    bottomBar.addSpaceItem(
        new SpaceItem(getString(R.string.bottombar_title_home), R.drawable.ic_drawer_home_24dp));
    bottomBar.addSpaceItem(
        new SpaceItem(getString(R.string.bottombar_title_movies), R.drawable.ic_drawer_movies));
    bottomBar.setActiveCentreButtonIconColor(ContextCompat.getColor(this, R.color.appWhite));
    bottomBar.setInActiveCentreButtonIconColor(ContextCompat.getColor(this, R.color.appWhite));

    List<Fragment> fragments = new ArrayList<>();
    fragments.add(HomeFragment.newInstance());
    fragments.add(MoviesFragment.newInstance());

    navigationManager.setupFragNavController(fragments, this);
    bottomBar.setSpaceOnClickListener(navigationManager);
    sharedViewsManager = SharedViewsManager.createSharedViewsManager(this, bottomBar);
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
}
