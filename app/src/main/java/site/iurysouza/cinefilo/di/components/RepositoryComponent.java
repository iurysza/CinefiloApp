package site.iurysouza.cinefilo.di.components;

import dagger.Subcomponent;
import site.iurysouza.cinefilo.di.ActivityScope;
import site.iurysouza.cinefilo.di.modules.RepositoryModule;
import site.iurysouza.cinefilo.di.modules.UtilityModule;
import site.iurysouza.cinefilo.presentation.main.MainActivity;
import site.iurysouza.cinefilo.presentation.medias.MediaListFragment;
import site.iurysouza.cinefilo.presentation.medias.pager.MediaPagerFragment;

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
    void inject(MediaPagerFragment target);
    void inject(MediaListFragment target);
}
