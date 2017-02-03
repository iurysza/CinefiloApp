package site.iurysouza.cinefilo.presentation;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import site.iurysouza.cinefilo.BuildConfig;
import site.iurysouza.cinefilo.di.components.AppComponent;
import site.iurysouza.cinefilo.di.components.DaggerAppComponent;
import site.iurysouza.cinefilo.di.components.MediaDetailComponent;
import site.iurysouza.cinefilo.di.components.MediaListComponent;
import site.iurysouza.cinefilo.di.modules.ApiModule;
import site.iurysouza.cinefilo.di.modules.AppModule;
import site.iurysouza.cinefilo.di.modules.MediaDetailModule;
import site.iurysouza.cinefilo.di.modules.MediaListModule;
import site.iurysouza.cinefilo.di.modules.UtilityModule;
import timber.log.Timber;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class CineApplication extends Application {

  private static CineApplication appInstance;
  private AppComponent appComponent;
  private MediaListComponent mediaListComponent;
  private MediaDetailComponent mediaDetailComponent;
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
    RxJavaPlugins.getInstance().registerErrorHandler(new RxJavaErrorHandler() {
      @Override public void handleError(Throwable e) {
        super.handleError(e);
        Timber.v(e.toString());
      }
    });
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
      Stetho.initialize(Stetho.newInitializerBuilder(this)
          .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
          .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
          .build());

    }
    realmConfig.build();

    Realm.setDefaultConfiguration(realmConfig.build());
    return Realm.getDefaultInstance();
  }



  private void createAppComponent() {
    appComponent = DaggerAppComponent
        .builder()
        .appModule(new AppModule(this, realm))
        .apiModule(new ApiModule())
        .build();
  }

  public MediaListComponent createMediaListComponent(MediaListModule mediaListModule, UtilityModule utilityModule) {
    mediaListComponent = getAppComponent().plus(mediaListModule, utilityModule);
    return mediaListComponent;
  }

  public MediaDetailComponent createMediaDetailComponent(MediaDetailModule mediaDetailModule) {
    mediaDetailComponent = getAppComponent().plus(mediaDetailModule);
    return mediaDetailComponent;
  }



  public AppComponent getAppComponent() {
    return appComponent;
  }
  public MediaListComponent getMediaListComponent() {
    return mediaListComponent;
  }
  public MediaDetailComponent getMediaDetailComponent() {
    return mediaDetailComponent;
  }

  public void releaseAppComponent() {
    appComponent = null;
  }

  public void releaseRepositoryComponent() {
    mediaListComponent = null;
  }
  public void releaseDetailComponent() {
    mediaDetailComponent = null;
  }
}

