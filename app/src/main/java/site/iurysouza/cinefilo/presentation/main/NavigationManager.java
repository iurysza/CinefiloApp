package site.iurysouza.cinefilo.presentation.main;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.ncapdevi.fragnav.FragNavController;
import java.util.List;
import javax.inject.Inject;
import site.iurysouza.cinefilo.R;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class NavigationManager implements
    SpaceOnClickListener{

  private static final int HOME_TAB = 0;
  private static final int MOVIES_TAB = 1;
  private static final int SEARCH_TAB = 2;

  private static final int START_INDEX = HOME_TAB;

  private final FragmentManager fragManager;
  private FragNavController fragNavController;
  private BottomBarListener bottomBarListener;

  private AppCompatActivity activity;

  @IdRes
  private int fragContainer;

  @Inject
  public NavigationManager(AppCompatActivity activity, FragmentManager fragManager
  ) {
    this.activity = activity;
    this.fragManager = fragManager;
    fragContainer = R.id.main_fragment_container;
  }

  public void setFragmentContainer(@IdRes int fragContainer) {
    this.fragContainer = fragContainer;
  }

  public void setupFragNavController(List<Fragment> fragList, BottomBarListener bottomBarListener) {
    this.bottomBarListener = bottomBarListener;
    fragNavController =
        new FragNavController(null, fragManager, fragContainer, fragList, START_INDEX);
  }

  @Override public void onCentreButtonClick() {
    //fragNavController.switchTab(SEARCH_TAB);
    //bottomBarListener.onTabSelected(fragNavController.getCurrentFrag());
  }

  @Override public void onItemClick(int itemIndex, String itemName) {
    switch (itemIndex) {
      case HOME_TAB:
        fragNavController.switchTab(HOME_TAB);
        bottomBarListener.onTabSelected(fragNavController.getCurrentFrag());
        break;
      case MOVIES_TAB:
        fragNavController.switchTab(MOVIES_TAB);
        bottomBarListener.onTabSelected(fragNavController.getCurrentFrag());
        break;
    }
  }

  @Override public void onItemReselected(int itemIndex, String itemName) {

  }
}
