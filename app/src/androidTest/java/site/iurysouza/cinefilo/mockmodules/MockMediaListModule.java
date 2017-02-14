package site.iurysouza.cinefilo.mockmodules;

import site.iurysouza.cinefilo.di.modules.MediaListModule;
import site.iurysouza.cinefilo.domain.IWatchMediaRepository;
import site.iurysouza.cinefilo.domain.MoviesUseCase;
import site.iurysouza.cinefilo.domain.SeriesUseCase;
import site.iurysouza.cinefilo.model.data.movies.MoviesRepositoryI;
import site.iurysouza.cinefilo.model.data.movies.storage.CloudMovieDataSource;
import site.iurysouza.cinefilo.model.data.movies.storage.ICloudMovieDataSource;
import site.iurysouza.cinefilo.model.data.movies.storage.ILocalMovieDataSource;
import site.iurysouza.cinefilo.model.data.movies.storage.LocalMovieDataSource;
import site.iurysouza.cinefilo.model.data.series.SeriesRepositoryI;
import site.iurysouza.cinefilo.model.data.series.storage.CloudSeriesDataSource;
import site.iurysouza.cinefilo.model.data.series.storage.ICloudSeriesDataSource;
import site.iurysouza.cinefilo.model.data.series.storage.ILocalSeriesDataSource;
import site.iurysouza.cinefilo.model.data.series.storage.LocalSeriesDataSource;
import site.iurysouza.cinefilo.model.services.MovieService;
import site.iurysouza.cinefilo.model.services.SeriesService;
import site.iurysouza.cinefilo.presentation.UseCase;

import static org.mockito.Mockito.mock;

/**
 * Created by Iury Souza on 13/02/2017.
 */

public class MockMediaListModule extends MediaListModule {

  @Override public ICloudMovieDataSource providesCloudMovieDataSource(MovieService movieService) {
    return mock(CloudMovieDataSource.class);
  }

  @Override public ICloudSeriesDataSource providesCloudSeriesDataSource(SeriesService seriesService) {
    return mock(CloudSeriesDataSource.class);
  }

  @Override public ILocalMovieDataSource providesLocalMovieDataSource() {
    return mock(LocalMovieDataSource.class);
  }

  @Override public ILocalSeriesDataSource providesLocalSeriesDataSource() {
    return mock(LocalSeriesDataSource.class);
  }

  @Override public IWatchMediaRepository providesMovieDataRepository(LocalMovieDataSource localMovieDataSource,
      CloudMovieDataSource cloudMovieDataSource) {
    return new MoviesRepositoryI(localMovieDataSource, cloudMovieDataSource);
  }

  @Override public IWatchMediaRepository providesSeriesDataRepository(
      LocalSeriesDataSource localSeriesDataSource,
      CloudSeriesDataSource cloudSeriesDataSource) {
    return new SeriesRepositoryI(localSeriesDataSource, cloudSeriesDataSource);
  }

  @Override public UseCase providesMovieUseCase(MoviesRepositoryI dataRepository) {
    return new MoviesUseCase(dataRepository);
  }

  @Override public UseCase providesSeriesUseCase(SeriesRepositoryI dataRepository) {
    return new SeriesUseCase(dataRepository);
  }
}