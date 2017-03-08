package site.iurysouza.cinefilo.presentation.main;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import javax.inject.Inject;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.presentation.base.BaseFragment;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class NavigationManagerVanilla {

  private final FragmentManager fragmentManager;
  private AppCompatActivity activity;

  @IdRes
  private int fragmentContainer;

  @Inject
  public NavigationManagerVanilla(AppCompatActivity activity, FragmentManager fragmentManager) {
    this.activity = activity;
    this.fragmentManager = fragmentManager;
    fragmentContainer = R.id.main_fragment_container;
  }

  public void setFragmentContainer(@IdRes int fragmentContainer) {
    this.fragmentContainer = fragmentContainer;
  }

  public void openFragment(BaseFragment fragment) {
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.replace(fragmentContainer, fragment);
    transaction.commit();
  }

  public void replaceFragment (Fragment fragment){
    String backStateName =  fragment.getClass().getName();
    boolean fragmentPopped = fragmentManager.popBackStackImmediate (backStateName, 0);

    if (!fragmentPopped && fragmentManager.findFragmentByTag(backStateName) == null){ //fragment not in back stack, create it.
      FragmentTransaction ft = fragmentManager.beginTransaction();
      ft.replace(fragmentContainer, fragment, backStateName);
      ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
      ft.addToBackStack(backStateName);
      ft.commit();
    }
  }
}
