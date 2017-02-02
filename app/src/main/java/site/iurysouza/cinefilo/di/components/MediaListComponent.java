package site.iurysouza.cinefilo.di.components;

import dagger.Subcomponent;
import site.iurysouza.cinefilo.di.ActivityScope;
import site.iurysouza.cinefilo.di.modules.MediaListModule;
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
        MediaListModule.class, UtilityModule.class
    })

public interface MediaListComponent {
    void inject(MainActivity mainActivity);
    void inject(MediaPagerFragment target);
    void inject(MediaListFragment target);
}
