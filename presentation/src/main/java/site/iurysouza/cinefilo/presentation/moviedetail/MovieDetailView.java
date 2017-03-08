package site.iurysouza.cinefilo.presentation.moviedetail;

import java.util.List;
import site.iurysouza.cinefilo.domain.moviedetail.Credits;
import site.iurysouza.cinefilo.presentation.base.mvp.BaseView;
import site.iurysouza.cinefilo.presentation.moviedetail.entity.MovieDetailValue;
import site.iurysouza.cinefilo.presentation.medialist.entity.WatchMediaValue;

/**
 * Created by Iury Souza on 01/02/2017.
 */
public interface MovieDetailView extends BaseView{
  void updateMovieData(MovieDetailValue movieDetailValue);

  void showErrorWarning();

  void showSimilarMovies(List<WatchMediaValue> mediaValues);

  void onMovieCreditsLoaded(Credits credits);
}
