package site.iurysouza.cinefilo.di.components;

import dagger.Subcomponent;
import site.iurysouza.cinefilo.di.ActivityScope;
import site.iurysouza.cinefilo.di.modules.MediaDetailModule;
import site.iurysouza.cinefilo.presentation.moviedetail.MovieDetailActivity;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@ActivityScope
@Subcomponent(
    modules = {
        MediaDetailModule.class
    })
public interface MediaDetailComponent {
    void inject(MovieDetailActivity movieDetailActivity);
}
