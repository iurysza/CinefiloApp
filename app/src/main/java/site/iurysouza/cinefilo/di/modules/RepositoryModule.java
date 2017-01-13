package site.iurysouza.cinefilo.di.modules;

import dagger.Module;
import dagger.Provides;
import java.io.InputStream;
import retrofit2.Retrofit;
import site.iurysouza.cinefilo.domain.MoviesUseCase;
import site.iurysouza.cinefilo.domain.SeriesUseCase;
import site.iurysouza.cinefilo.model.data.movies.MoviesRepository;
import site.iurysouza.cinefilo.model.data.movies.storage.CloudMovieDataSource;
import site.iurysouza.cinefilo.model.data.movies.storage.LocalMovieDataSource;
import site.iurysouza.cinefilo.model.data.series.SeriesRepository;
import site.iurysouza.cinefilo.model.data.series.storage.CloudSeriesDataSource;
import site.iurysouza.cinefilo.model.data.series.storage.LocalSeriesDataSource;
import site.iurysouza.cinefilo.model.services.MovieService;
import site.iurysouza.cinefilo.model.services.SeriesService;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@Module
public class RepositoryModule {
  @Provides
  MovieService providesMovieService(Retrofit retrofit) {
    return retrofit.create(MovieService.class);
  }

  @Provides
  SeriesService providesSeriesService(Retrofit retrofit) {
    return retrofit.create(SeriesService.class);
  }

  @Provides CloudMovieDataSource providesCloudMovieDataStore(MovieService movieService) {
    return new CloudMovieDataSource(movieService);
  }

  @Provides CloudSeriesDataSource providesCloudSeriesDataStore(SeriesService seriesService) {
    return new CloudSeriesDataSource(seriesService);
  }

  @Provides LocalMovieDataSource providesLocalMovieDataStore() {
    return new LocalMovieDataSource();
  }

  @Provides LocalSeriesDataSource providesLocalSeriesDataStore() {
    return new LocalSeriesDataSource();
  }


  @Provides MoviesRepository providesMovieDataRepository(LocalMovieDataSource localMovieDataSource,
      CloudMovieDataSource cloudMovieDataSource, InputStream gendersFromJson) {
    return new MoviesRepository(localMovieDataSource, cloudMovieDataSource, gendersFromJson);
  }

  @Provides SeriesRepository providesSeriesDataRepository(LocalSeriesDataSource localSeriesDataSource,
      CloudSeriesDataSource cloudSeriesDataSource) {
    return new SeriesRepository(localSeriesDataSource, cloudSeriesDataSource);
  }


  @Provides MoviesUseCase providesMovieUseCase(MoviesRepository dataRepository) {
    return new MoviesUseCase(dataRepository);
  }
  @Provides SeriesUseCase providesSeriesUseCase(SeriesRepository dataRepository) {
    return new SeriesUseCase(dataRepository);
  }
}
