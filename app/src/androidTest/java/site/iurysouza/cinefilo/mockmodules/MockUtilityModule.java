//package site.iurysouza.cinefilo.mockmodules;
//
//import android.support.v4.app.FragmentManager;
//import android.support.v7.app.AppCompatActivity;
//import site.iurysouza.cinefilo.di.modules.UtilityModule;
//import site.iurysouza.cinefilo.presentation.main.NavigationManager;
//
///**
// * Created by Iury Souza on 13/02/2017.
// */
//
//public class MockUtilityModule extends UtilityModule {
//  private AppCompatActivity activity;
//
//  public MockUtilityModule(AppCompatActivity activity) {
//    this.activity = activity;
//  }
//
//  @Override
//  public AppCompatActivity provideActivity() {
//    return activity;
//  }
//
//  @Override public FragmentManager providesFragmentManager(AppCompatActivity activity) {
//    return activity.getSupportFragmentManager();
//  }
//
//  @Override public NavigationManager providesNavigationManager(AppCompatActivity activity,
//      FragmentManager manager) {
//    return new NavigationManager(activity, manager);
//  }
//}
