package site.iurysouza.cinefilo.presentation;

import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import site.iurysouza.cinefilo.BuildConfig;
import site.iurysouza.cinefilo.di.components.AppComponent;
import site.iurysouza.cinefilo.di.components.DaggerAppComponent;
import site.iurysouza.cinefilo.di.components.RepositoryComponent;
import site.iurysouza.cinefilo.di.modules.AppModule;
import site.iurysouza.cinefilo.di.modules.MoviesApiModule;
import site.iurysouza.cinefilo.di.modules.RepositoryModule;
import timber.log.Timber;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class CineApplication extends Application {

  private static CineApplication appInstance;
  private AppComponent appComponent;
  private RepositoryComponent repositoryComponent;
  private Realm realm;

  public static CineApplication getAppInstance() {
    return appInstance;
  }

  @Override public void onCreate() {
    super.onCreate();
    appInstance = this;
    realm = initRealm();
    initTimber();
    createAppComponent();
  }

  private void initTimber() {
    Timber.plant(new Timber.DebugTree() {
      @Override protected String createStackElementTag(StackTraceElement element) {
        return super.createStackElementTag(element)
            + ":"
            + element.getLineNumber()
            + " "
            + element.getMethodName();
      }
    });
  }

  private Realm initRealm() {
    Realm.init(getApplicationContext());

    RealmConfiguration.Builder realmConfig = new RealmConfiguration.Builder();
    if (BuildConfig.DEBUG) {
      realmConfig = realmConfig.deleteRealmIfMigrationNeeded();
    }
    realmConfig.build();

    Realm.setDefaultConfiguration(realmConfig.build());
    return Realm.getDefaultInstance();
  }

  private void createAppComponent() {
    appComponent = DaggerAppComponent
        .builder()
        .appModule(new AppModule(this, realm))
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

