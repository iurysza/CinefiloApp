package site.iurysouza.cinefilo.di.modules;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by Iury Souza on 11/10/2016.
 */
@Module
public class AppModule {
  protected Context context;

  public AppModule(Context context) {
    this.context = context;
  }

  @Provides
  @Singleton
  public Context provideContext() {
    return context;
  }
}