package site.iurysouza.cinefilo.testhelpers;

import site.iurysouza.cinefilo.CineApplication;
import site.iurysouza.cinefilo.di.modules.MockMediaDetailModule;
import site.iurysouza.cinefilo.di.modules.UtilityModule;
import site.iurysouza.cinefilo.mockmodules.MockMediaListModule;
import site.iurysouza.cinefilo.presentation.main.MainActivity;
import site.iurysouza.cinefilo.presentation.mediadetail.MediaDetailActivity;

/**
 * Created by Iury Souza on 13/02/2017.
 */

public class CinefiloTestApplication extends CineApplication {
  @Override public void createMediaListComponent(MainActivity activity) {
    if (getMediaListComponent() == null) {
      mediaListComponent = getAppComponent().plus(new MockMediaListModule(),
          new UtilityModule(activity));
      mediaListComponent.inject(activity);
    }
  }

  @Override public void createMediaDetailComponent(MediaDetailActivity mediaDetailActivity) {
    if (getMediaDetailComponent() == null) {
      mediaDetailComponent = getAppComponent().plus(new MockMediaDetailModule());
      mediaDetailComponent.inject(mediaDetailActivity);
    }
  }
}
