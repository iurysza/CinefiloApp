package site.iurysouza.cinefilo.testhelpers;

import site.iurysouza.cinefilo.CineApplication;
import site.iurysouza.cinefilo.components.DaggerCineTestComponent;
import site.iurysouza.cinefilo.di.components.MediaListComponent;
import site.iurysouza.cinefilo.di.modules.AppModule;
import site.iurysouza.cinefilo.di.modules.MediaListModule;
import site.iurysouza.cinefilo.di.modules.MockMediaDetailModule;
import site.iurysouza.cinefilo.di.modules.UtilityModule;
import site.iurysouza.cinefilo.mockmodules.MockApiModule;
import site.iurysouza.cinefilo.presentation.base.BaseActivity;
import site.iurysouza.cinefilo.presentation.moviedetail.MovieDetailActivity;

/**
 * Created by Iury Souza on 13/02/2017.
 */

public class CinefiloTestApplication extends CineApplication {
  @Override public MediaListComponent createMediaListComponent(BaseActivity activity) {
    if (mediaListComponent == null) {
      mediaListComponent = getAppComponent()
          .plus(new MediaListModule(), new UtilityModule(activity));
    }
    return mediaListComponent;
  }
  @Override public void createAppComponent() {
    if (getAppComponent() == null) {
      applicationComponent = DaggerCineTestComponent.builder()
          .appModule(new AppModule(this))
          .apiModule(new MockApiModule())
          .build();
      applicationComponent.inject((getAppInstance()));
    }
  }

  @Override public void createMediaDetailComponent(MovieDetailActivity movieDetailActivity) {
    if (getMediaDetailComponent() == null) {
      mediaDetailComponent = getAppComponent().plus(new MockMediaDetailModule());
      mediaDetailComponent.inject(movieDetailActivity);
    }
  }
}
