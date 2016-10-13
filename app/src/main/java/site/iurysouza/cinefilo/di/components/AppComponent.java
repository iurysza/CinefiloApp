package site.iurysouza.cinefilo.di.components;

import dagger.Component;
import javax.inject.Singleton;
import site.iurysouza.cinefilo.di.modules.AppModule;
import site.iurysouza.cinefilo.di.modules.MoviesApiModule;
import site.iurysouza.cinefilo.di.modules.RepositoryModule;
import site.iurysouza.cinefilo.presentation.CineApplication;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton
@Component(
    modules = {
        AppModule.class,
        MoviesApiModule.class
    })

public interface AppComponent {
  RepositoryComponent plus(RepositoryModule repositoryModule);
  void inject(CineApplication target);
}