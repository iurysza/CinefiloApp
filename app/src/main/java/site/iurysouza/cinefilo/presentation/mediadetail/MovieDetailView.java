package site.iurysouza.cinefilo.presentation.mediadetail;

import java.util.List;
import site.iurysouza.cinefilo.presentation.base.mvp.BaseView;
import site.iurysouza.cinefilo.presentation.medias.entity.MovieDetailValue;
import site.iurysouza.cinefilo.presentation.medias.entity.WatchMediaValue;

/**
 * Created by Iury Souza on 01/02/2017.
 */
public interface MovieDetailView extends BaseView{
  void updateMovieData(MovieDetailValue movieDetailValue);

  void showErrorWarning();

  void showSimilarMovies(List<WatchMediaValue> mediaValues);
}
