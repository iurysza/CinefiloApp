package site.iurysouza.cinefilo;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import site.iurysouza.cinefilo.di.components.ApplicationComponent;
import site.iurysouza.cinefilo.di.components.DaggerApplicationComponent;
import site.iurysouza.cinefilo.di.components.MediaDetailComponent;
import site.iurysouza.cinefilo.di.components.MediaListComponent;
import site.iurysouza.cinefilo.di.modules.ApiModule;
import site.iurysouza.cinefilo.di.modules.AppModule;
import site.iurysouza.cinefilo.di.modules.MediaDetailModule;
import site.iurysouza.cinefilo.di.modules.MediaListModule;
import site.iurysouza.cinefilo.di.modules.UtilityModule;
import site.iurysouza.cinefilo.presentation.base.BaseActivity;
import site.iurysouza.cinefilo.presentation.mediadetail.MediaDetailActivity;
import timber.log.Timber;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class CineApplication extends Application {

  private static CineApplication appInstance;
  protected ApplicationComponent applicationComponent;
  protected MediaListComponent mediaListComponent;
  protected MediaDetailComponent mediaDetailComponent;

  public static CineApplication getAppInstance() {
    return appInstance;
  }

  @Override public void onCreate() {
    super.onCreate();
    appInstance = this;
    initRealm();
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

  private void initRealm() {
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
  }

  public void createAppComponent() {
    if (applicationComponent == null) {
      applicationComponent = DaggerApplicationComponent
          .builder()
          .appModule(new AppModule(this))
          .apiModule(new ApiModule())
          .build();
    }
  }

  public MediaListComponent createMediaListComponent(BaseActivity activity) {
    if (mediaListComponent == null) {
      mediaListComponent = getAppComponent()
          .plus(new MediaListModule(), new UtilityModule(activity));
    }
    return mediaListComponent;
  }

  public void createMediaDetailComponent(MediaDetailActivity mediaDetailActivity) {
    if (mediaDetailComponent == null) {
      mediaDetailComponent = getAppComponent().plus(new MediaDetailModule());
    }
    mediaDetailComponent.inject(mediaDetailActivity);
  }

  public ApplicationComponent getAppComponent() {
    return applicationComponent;
  }

  public MediaListComponent getMediaListComponent() {
    return mediaListComponent;
  }

  public MediaDetailComponent getMediaDetailComponent() {
    return mediaDetailComponent;
  }

  public void releaseAppComponent() {
    applicationComponent = null;
  }

  public void releaseMediaListComponent() {
    mediaListComponent = null;
  }

  public void releaseDetailComponent() {
    mediaDetailComponent = null;
  }
}

