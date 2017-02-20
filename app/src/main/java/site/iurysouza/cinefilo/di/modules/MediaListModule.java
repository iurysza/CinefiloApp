package site.iurysouza.cinefilo.di.modules;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import site.iurysouza.cinefilo.domain.watchmedialist.MoviesWatchMediaListUseCase;
import site.iurysouza.cinefilo.domain.watchmedialist.SeriesWatchMediaListUseCase;
import site.iurysouza.cinefilo.model.data.movies.MoviesRepository;
import site.iurysouza.cinefilo.model.data.movies.storage.CloudMovieDataSource;
import site.iurysouza.cinefilo.model.data.movies.storage.ICloudMovieDataSource;
import site.iurysouza.cinefilo.model.data.movies.storage.ILocalMovieDataSource;
import site.iurysouza.cinefilo.model.data.movies.storage.LocalMovieDataSource;
import site.iurysouza.cinefilo.model.data.series.SeriesRepository;
import site.iurysouza.cinefilo.model.data.series.storage.CloudSeriesDataSource;
import site.iurysouza.cinefilo.model.data.series.storage.ICloudSeriesDataSource;
import site.iurysouza.cinefilo.model.data.series.storage.ILocalSeriesDataSource;
import site.iurysouza.cinefilo.model.data.series.storage.LocalSeriesDataSource;
import site.iurysouza.cinefilo.model.services.MovieService;
import site.iurysouza.cinefilo.model.services.SeriesService;

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

  @Provides public MoviesRepository providesMovieDataRepository(
      LocalMovieDataSource localMovieDataSource,
      CloudMovieDataSource cloudMovieDataSource) {
    return new MoviesRepository(localMovieDataSource, cloudMovieDataSource);
  }

  @Provides public SeriesRepository providesSeriesDataRepository(
      LocalSeriesDataSource localSeriesDataSource,
      CloudSeriesDataSource cloudSeriesDataSource) {
    return new SeriesRepository(localSeriesDataSource, cloudSeriesDataSource);
  }

  @Provides public MoviesWatchMediaListUseCase providesMovieUseCase(MoviesRepository dataRepository) {
    return new MoviesWatchMediaListUseCase(dataRepository);
  }

  @Provides public SeriesWatchMediaListUseCase providesSeriesUseCase(SeriesRepository dataRepository) {
    return new SeriesWatchMediaListUseCase(dataRepository);
  }
}
