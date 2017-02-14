package site.iurysouza.cinefilo.components;

import dagger.Component;
import javax.inject.Singleton;
import site.iurysouza.cinefilo.di.components.ApplicationComponent;
import site.iurysouza.cinefilo.di.modules.ApiModule;
import site.iurysouza.cinefilo.di.modules.AppModule;
import site.iurysouza.cinefilo.di.modules.MediaListModule;
import site.iurysouza.cinefilo.di.modules.UtilityModule;
import site.iurysouza.cinefilo.tests.MediaDetailActivityTest;

/**
 * Created by Iury Souza on 13/02/2017.
 */
@Singleton
@Component(
    modules = {
        ApiModule.class,
        AppModule.class
    })
public interface CineTestComponent extends ApplicationComponent {
  void inject(MediaDetailActivityTest activity);
  MediaListTestComponent plus(MediaListModule mediaListModule, UtilityModule utilityModule);
}

