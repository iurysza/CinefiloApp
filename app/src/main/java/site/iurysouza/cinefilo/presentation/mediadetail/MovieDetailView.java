package site.iurysouza.cinefilo.presentation.mediadetail;

import site.iurysouza.cinefilo.model.data.entity.MovieDetailValue;
import site.iurysouza.cinefilo.presentation.base.mvp.BaseView;

/**
 * Created by Iury Souza on 01/02/2017.
 */
public interface MovieDetailView extends BaseView{
  void updateMovieData(MovieDetailValue movieDetailValue);

  void showErrorWarning();

}
