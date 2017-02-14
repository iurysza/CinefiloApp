package site.iurysouza.cinefilo.components;

import dagger.Subcomponent;
import site.iurysouza.cinefilo.di.ActivityScope;
import site.iurysouza.cinefilo.di.components.MediaListComponent;
import site.iurysouza.cinefilo.di.modules.MediaListModule;
import site.iurysouza.cinefilo.di.modules.UtilityModule;
import site.iurysouza.cinefilo.tests.MediaDetailActivityTest;

/**
 * Created by Iury Souza on 13/02/2017.
 */

@ActivityScope
@Subcomponent(
    modules = {
        MediaListModule.class, UtilityModule.class
    })
public interface MediaListTestComponent extends MediaListComponent{
  void inject(MediaDetailActivityTest mainActivity);

}
