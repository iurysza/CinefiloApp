package site.iurysouza.cinefilo.di.modules;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
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

/**
 * Created by Iury Souza on 12/10/2016.
 */
@Module
public class MediaListModule {
  @Provides
  public MovieService providesMovieService(Retrofit retrofit) {
    return retrofit.create(MovieService.class);
  }

  @Provides
  public SeriesService providesSeriesService(Retrofit retrofit) {
    return retrofit.create(SeriesService.class);
  }

  @Provides public ICloudMovieDataSource providesCloudMovieDataSource(MovieService movieService) {
    return new CloudMovieDataSource(movieService);
  }

  @Provides public ICloudSeriesDataSource providesCloudSeriesDataSource(SeriesService seriesService) {
    return new CloudSeriesDataSource(seriesService);
  }

  @Provides public ILocalMovieDataSource providesLocalMovieDataSource() {
    return new LocalMovieDataSource();
  }

  @Provides public ILocalSeriesDataSource providesLocalSeriesDataSource() {
    return new LocalSeriesDataSource();
  }

  @Provides public IWatchMediaRepository providesMovieDataRepository(
      LocalMovieDataSource localMovieDataSource,
      CloudMovieDataSource cloudMovieDataSource) {
    return new MoviesRepositoryI(localMovieDataSource, cloudMovieDataSource);
  }

  @Provides public IWatchMediaRepository providesSeriesDataRepository(
      LocalSeriesDataSource localSeriesDataSource,
      CloudSeriesDataSource cloudSeriesDataSource) {
    return new SeriesRepositoryI(localSeriesDataSource, cloudSeriesDataSource);
  }

  @Provides public UseCase providesMovieUseCase(MoviesRepositoryI dataRepository) {
    return new MoviesUseCase(dataRepository);
  }

  @Provides public UseCase providesSeriesUseCase(SeriesRepositoryI dataRepository) {
    return new SeriesUseCase(dataRepository);
  }
}
