package site.iurysouza.cinefilo.di.components;

import site.iurysouza.cinefilo.CineApplication;
import site.iurysouza.cinefilo.di.modules.MediaDetailModule;
import site.iurysouza.cinefilo.di.modules.MediaListModule;
import site.iurysouza.cinefilo.di.modules.UtilityModule;

/**
 * A component whose lifetime is the life of the application.
 */

public interface CineComponent {
  MediaListComponent plus(MediaListModule mediaListModule, UtilityModule utilityModule);
  MediaDetailComponent plus(MediaDetailModule mediaDetailModule);

  void inject(CineApplication target);
}
