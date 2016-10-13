package site.iurysouza.cinefilo.presentation;

import android.app.Application;
import site.iurysouza.cinefilo.di.components.AppComponent;
import site.iurysouza.cinefilo.di.components.DaggerAppComponent;
import site.iurysouza.cinefilo.di.components.RepositoryComponent;
import site.iurysouza.cinefilo.di.modules.AppModule;
import site.iurysouza.cinefilo.di.modules.MoviesApiModule;
import site.iurysouza.cinefilo.di.modules.RepositoryModule;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class CineApplication extends Application {

  private static CineApplication appInstance;
  private AppComponent appComponent;
  private RepositoryComponent repositoryComponent;

  public static CineApplication getAppInstance() {
    return appInstance;
  }

  @Override public void onCreate() {
    super.onCreate();
    appInstance = this;
    createAppComponent();
  }

  private void createAppComponent() {
    appComponent = DaggerAppComponent
        .builder()
        .appModule(new AppModule(this))
        .moviesApiModule(new MoviesApiModule())
        .build();
  }

  public RepositoryComponent createRepositoryComponent() {
    return repositoryComponent = getAppComponent().plus(new RepositoryModule());
  }

  public AppComponent getAppComponent() {
    return appComponent;
  }

  public void releaseAppComponent() {
    appComponent = null;
  }

  public void releaseRepositoryComponent() {
    repositoryComponent = null;
  }
}
