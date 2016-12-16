package site.iurysouza.cinefilo.presentation.main;

import android.support.v4.app.Fragment;
import android.view.Menu;
import com.luseen.spacenavigation.SpaceNavigationView;

/**
 * Created by Iury Souza on 16/12/2016.
 */
class SharedViewsManager {

  private final MainActivity mainActivity;
  private final SpaceNavigationView bottomBar;
  //@BindView(R.id.toolbar_fragment_movies) Toolbar toolbarFragmentMovies;
  //@BindView(R.id.image_backdrop_fragment_movies) ImageView imageBackdropFragmentMovies;
  //@BindView(R.id.collapsing_fragment_movies) CollapsingToolbarLayout collapsingFragmentMovies;
  //@BindView(R.id.tablayout_fragment_movies) TabLayout tablayoutFragmentMovies;
  //@BindView(R.id.appbar_fragment_movies) AppBarLayout appbarFragmentMovies;
  private Menu menu;
  private Fragment currentFrag;

  private SharedViewsManager(MainActivity mainActivity, SpaceNavigationView bottomBar) {
    this.mainActivity = mainActivity;
    this.bottomBar = bottomBar;
    //ButterKnife.bind(this, mainActivity);
  }

  static SharedViewsManager createSharedViewsManager(MainActivity mainActivity,
      SpaceNavigationView bottomBar) {
    return new SharedViewsManager(mainActivity, bottomBar);
  }

  void withMenu(Menu menu) {
    this.menu = menu;
  }

  void updateViewsForFragment(Fragment currentFrag) {
    //this.currentFrag = currentFrag;
    //if (currentFrag instanceof MoviesFragment) {
    //  MoviesFragment frag = (MoviesFragment) currentFrag;
    //  toolbarFragmentMovies.setTitle("UPDATED");
    //}
  }
}
