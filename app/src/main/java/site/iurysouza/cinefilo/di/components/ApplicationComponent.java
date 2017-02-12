package site.iurysouza.cinefilo.di.components;

import dagger.Component;
import javax.inject.Singleton;
import site.iurysouza.cinefilo.di.modules.ApiModule;
import site.iurysouza.cinefilo.di.modules.AppModule;

/**
 * Created by Iury Souza on 10/02/2017.
 */
@Singleton
@Component(
    modules = {
        AppModule.class,
        ApiModule.class
    })
public interface ApplicationComponent extends CineComponent {
}
