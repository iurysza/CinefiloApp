package site.iurysouza.cinefilo.di.components;

import dagger.Subcomponent;
import site.iurysouza.cinefilo.di.ActivityScope;
import site.iurysouza.cinefilo.di.modules.RepositoryModule;
import site.iurysouza.cinefilo.di.modules.UtilityModule;
import site.iurysouza.cinefilo.presentation.MainActivity;
import site.iurysouza.cinefilo.presentation.home.HomeFragment;
import site.iurysouza.cinefilo.presentation.movies.MoviesFragment;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@ActivityScope
@Subcomponent(
    modules = {
        RepositoryModule.class, UtilityModule.class
    })

public interface RepositoryComponent {
    void inject(MainActivity mainActivity);
    void inject(MoviesFragment target);
    void inject(HomeFragment target);
}
