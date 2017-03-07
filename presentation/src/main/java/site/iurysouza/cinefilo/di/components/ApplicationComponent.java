package site.iurysouza.cinefilo.di.components;

import dagger.Component;
import javax.inject.Singleton;
import site.iurysouza.cinefilo.CineApplication;
import site.iurysouza.cinefilo.di.modules.ApiModule;
import site.iurysouza.cinefilo.di.modules.AppModule;
import site.iurysouza.cinefilo.di.modules.MediaDetailModule;
import site.iurysouza.cinefilo.di.modules.MediaListModule;
import site.iurysouza.cinefilo.di.modules.UtilityModule;

/**
 * Created by Iury Souza on 10/02/2017.
 */
@Singleton
@Component(
    modules = {
        AppModule.class,
        ApiModule.class
    })
public interface ApplicationComponent  {
    MediaListComponent plus(MediaListModule mediaListModule, UtilityModule utilityModule);
    MediaDetailComponent plus(MediaDetailModule mediaDetailModule);

    void inject(CineApplication target);
}
