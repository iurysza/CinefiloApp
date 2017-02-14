package site.iurysouza.cinefilo.components;

import dagger.Subcomponent;
import site.iurysouza.cinefilo.di.ActivityScope;
import site.iurysouza.cinefilo.di.components.MediaDetailComponent;
import site.iurysouza.cinefilo.di.modules.MediaDetailModule;
import site.iurysouza.cinefilo.tests.MediaDetailActivityTest;

/**
 * Created by Iury Souza on 14/02/2017.
 */

@ActivityScope
@Subcomponent(
    modules = {
        MediaDetailModule.class
    })
public interface MediaDetailTestComponent extends MediaDetailComponent{
  void inject(MediaDetailActivityTest mediaDetailActivity);
}

