package site.iurysouza.cinefilo.di.modules;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import javax.inject.Singleton;

/**
 * Created by Iury Souza on 11/10/2016.
 */
@Module
public class AppModule {
  private Context context;
  private Realm realm;

  public AppModule(Context context, Realm realm) {
    this.context = context;
    this.realm = realm;
  }

  @Provides
  @Singleton
  public Context provideContext() {
    return context;
  }

  @Provides
  @Singleton
  public Realm provideRealm() {
    return realm;
  }

}