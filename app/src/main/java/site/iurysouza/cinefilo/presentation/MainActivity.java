package site.iurysouza.cinefilo.presentation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import javax.inject.Inject;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.di.modules.RepositoryModule;
import site.iurysouza.cinefilo.di.modules.UtilityModule;
import site.iurysouza.cinefilo.presentation.base.BaseActivity;
import site.iurysouza.cinefilo.presentation.home.HomeFragment;
import site.iurysouza.cinefilo.presentation.home.HomePresenter;
import timber.log.Timber;

public class MainActivity extends BaseActivity
    implements
    OnNavigationItemSelectedListener {

  @BindView(R.id.main_navigation_view) NavigationView navigationView;
  @BindView(R.id.main_drawer_layout) DrawerLayout mainDrawerLayout;

  @Inject
  NavigationManager navigationManager;
  @Inject
  HomePresenter homePresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    navigationView.setNavigationItemSelectedListener(this);
    navigationManager.openHomeFragment(HomeFragment.newInstance());
  }

  @Override protected void setupActivityComponent() {
    appInstance.createRepositoryComponent(new RepositoryModule(), new UtilityModule(this))
        .inject(this);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
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

  @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {

      case R.id.drawer_home:
        navigationManager.openHomeFragment(HomeFragment.newInstance());
        break;

      case R.id.drawer_movies:
        Timber.d("movies");
        break;

      default:
        break;
    }

    mainDrawerLayout.closeDrawer(GravityCompat.START, true);
    return true;
  }
}
