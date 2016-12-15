package site.iurysouza.cinefilo.presentation;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import javax.inject.Inject;
import site.iurysouza.cinefilo.R;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class NavigationManager {

  private final FragmentManager fragmentManager;
  private AppCompatActivity activity;

  @IdRes
  private int fragmentContainer;

  @Inject
  public NavigationManager(AppCompatActivity activity, FragmentManager fragmentManager) {
    this.activity = activity;
    this.fragmentManager = fragmentManager;
    fragmentContainer = R.id.main_fragment_container;
  }

  public void setFragmentContainer(@IdRes int fragmentContainer) {
    this.fragmentContainer = fragmentContainer;
  }

  public void openFragment(Fragment fragment) {
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.replace(fragmentContainer, fragment);
    transaction.commit();
  }

  public FragmentManager getFragManager() {
    return fragmentManager;
  }
}
