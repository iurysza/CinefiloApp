package site.iurysouza.cinefilo.presentation;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import javax.inject.Inject;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.presentation.home.HomeFragment;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class NavigationManager {

  private AppCompatActivity activity;
  private final FragmentManager fragmentManager;
  private final FragmentTransaction fragmentTransaction;

  @Inject
  public NavigationManager(AppCompatActivity activity, FragmentManager fragmentManager, FragmentTransaction fragmentTransaction) {
    this.activity = activity;
    this.fragmentManager = fragmentManager;
    this.fragmentTransaction = fragmentTransaction;
  }

  public void openHomeFragment(HomeFragment homeFragment) {
    fragmentTransaction.add(R.id.main_fragment_container,  homeFragment);
    fragmentTransaction.commit();
  }
}
