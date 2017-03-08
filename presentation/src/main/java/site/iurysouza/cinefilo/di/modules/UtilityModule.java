package site.iurysouza.cinefilo.di.modules;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import dagger.Module;
import dagger.Provides;
import site.iurysouza.cinefilo.presentation.main.NavigationManager;

/**
 * Created by Iury Souza on 09/11/2016.
 */

@Module
public class UtilityModule {
  protected AppCompatActivity activity;

  public UtilityModule(AppCompatActivity activity) {
    this.activity = activity;
  }

  public UtilityModule() {
  }

  @Provides
  public AppCompatActivity provideActivity() {
    return activity;
  }

  @Provides
  public FragmentManager providesFragmentManager(AppCompatActivity activity) {
    return activity.getSupportFragmentManager();
  }

  @Provides
  public NavigationManager providesNavigationManager(AppCompatActivity activity, FragmentManager manager) {
    return new NavigationManager(activity, manager );
  }
}
