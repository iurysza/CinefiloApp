package site.iurysouza.cinefilo.presentation;

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

  @Inject
  public NavigationManager(AppCompatActivity activity, FragmentManager fragmentManager) {
    this.activity = activity;
    this.fragmentManager = fragmentManager;
  }

  public void openFragmentFromDrawer(Fragment homeFragment) {
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.replace(R.id.main_fragment_container, homeFragment);
    transaction.commit();
  }
}
