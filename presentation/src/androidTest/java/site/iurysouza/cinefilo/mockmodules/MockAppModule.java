package site.iurysouza.cinefilo.mockmodules;

import android.content.Context;
import site.iurysouza.cinefilo.di.modules.AppModule;

/**
 * Created by Iury Souza on 13/02/2017.
 */
public class MockAppModule extends AppModule {

  public MockAppModule(Context context) {
    super(context);
  }

  @Override public Context provideContext() {
    return this.context;
  }
}
