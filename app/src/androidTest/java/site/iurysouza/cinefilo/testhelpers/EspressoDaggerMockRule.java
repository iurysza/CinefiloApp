package site.iurysouza.cinefilo.testhelpers;

import android.support.test.InstrumentationRegistry;
import it.cosenonjaviste.daggermock.DaggerMockRule;
import site.iurysouza.cinefilo.CineApplication;
import site.iurysouza.cinefilo.di.components.ApplicationComponent;
import site.iurysouza.cinefilo.di.modules.AppModule;

/**
 * Created by Iury Souza on 14/02/2017.
 */

public class EspressoDaggerMockRule extends DaggerMockRule<ApplicationComponent> {
  public EspressoDaggerMockRule() {
    super(ApplicationComponent.class, new AppModule(getApp()));
    set(new DaggerMockRule.ComponentSetter<ApplicationComponent>() {
      @Override public void setComponent(ApplicationComponent component) {
        getApp().setApplicationComponent(component);
      }
    });
  }

  private static CineApplication getApp() {
    return (CineApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
  }
}