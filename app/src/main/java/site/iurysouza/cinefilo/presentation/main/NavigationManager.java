package site.iurysouza.cinefilo.presentation.main;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.ncapdevi.fragnav.FragNavController;
import java.util.List;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.presentation.movies.ItemReselectedEvent;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class NavigationManager implements
    SpaceOnClickListener {

  private final FragmentManager fragManager;
  private final AppCompatActivity activity;
  private FragNavController fragNavController;
  private BottomBarListener bottomBarListener;

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
        new FragNavController(null, fragManager, fragContainer, fragList, FragNavController.TAB1);
  }

  @Override public void onCentreButtonClick() {
    //fragNavController.switchTab(SEARCH_TAB);
    //bottomBarListener.onTabSelected(fragNavController.getCurrentFrag());
  }

  @Override public void onItemClick(int itemIndex, String itemName) {
    switch (itemIndex) {
      case FragNavController.TAB1:
        fragNavController.switchTab(FragNavController.TAB1);
        bottomBarListener.onTabSelected(fragNavController.getCurrentFrag());
        break;
      case FragNavController.TAB2:
        fragNavController.switchTab(FragNavController.TAB2);
        bottomBarListener.onTabSelected(fragNavController.getCurrentFrag());
        break;
    }
  }

  @Override public void onItemReselected(int itemIndex, String itemName) {
    EventBus.getDefault().post(new ItemReselectedEvent(itemIndex));
  }
}
