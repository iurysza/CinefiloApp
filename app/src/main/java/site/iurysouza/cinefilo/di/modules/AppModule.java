package site.iurysouza.cinefilo.di.modules;

import android.content.Context;
import android.support.compat.BuildConfig;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import javax.inject.Singleton;

/**
 * Created by Iury Souza on 11/10/2016.
 */
@Module
public class AppModule {
  private Context context;

  public AppModule(Context context) {
    this.context = context;
  }

  @Provides
  @Singleton
  public Context provideContext() {
    return context;
  }

  @Provides
  @Singleton
  static RealmConfiguration provideRealmConfiguration(Context context) {
    Realm.init(context);
    RealmConfiguration.Builder builder = new RealmConfiguration.Builder();
    if (BuildConfig.DEBUG) {
      builder = builder.deleteRealmIfMigrationNeeded();
    }
    return builder.build();
  }

  @Provides
  @Singleton
  static Realm provideRealm(RealmConfiguration realmConfiguration) {
    return Realm.getInstance(realmConfiguration);
  }

}