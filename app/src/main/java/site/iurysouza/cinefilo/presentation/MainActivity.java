package site.iurysouza.cinefilo.presentation;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import javax.inject.Inject;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.di.modules.RepositoryModule;
import site.iurysouza.cinefilo.di.modules.UtilityModule;
import site.iurysouza.cinefilo.presentation.base.BaseActivity;
import site.iurysouza.cinefilo.presentation.home.HomeFragment;
import site.iurysouza.cinefilo.presentation.home.HomePresenter;
import site.iurysouza.cinefilo.presentation.movies.MoviesFragment;

public class MainActivity extends BaseActivity
    implements
    SpaceOnClickListener {

  @BindView(R.id.space) SpaceNavigationView bottomBar;

  @Inject
  NavigationManager navigationManager;
  @Inject
  HomePresenter homePresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    bottomBar.initWithSaveInstanceState(savedInstanceState);
    bottomBar.addSpaceItem(new SpaceItem(getString(R.string.bottombar_title_home), R.drawable.ic_drawer_home_24dp));
    bottomBar.addSpaceItem(new SpaceItem(getString(R.string.bottombar_title_movies), R.drawable.ic_drawer_movies));
    bottomBar.setActiveCentreButtonIconColor(ContextCompat.getColor(this,R.color.colorWhite));
    bottomBar.setInActiveCentreButtonIconColor(ContextCompat.getColor(this,R.color.colorWhite));
    bottomBar.setSpaceOnClickListener(this);
    navigationManager.openFragment(HomeFragment.newInstance());
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


  @Override public void onCentreButtonClick() {
    Toast.makeText(appInstance, "Search", Toast.LENGTH_SHORT).show();
  }

  @Override public void onItemClick(int itemIndex, String itemName) {
    switch (itemIndex) {
      case 0:
        navigationManager.openFragment(HomeFragment.newInstance());
        break;
      case 1:
        navigationManager.openFragment(MoviesFragment.newInstance());
        break;
    }
  }

  @Override public void onItemReselected(int itemIndex, String itemName) {
    Toast.makeText(appInstance, "Reselected", Toast.LENGTH_SHORT).show();
  }
}
