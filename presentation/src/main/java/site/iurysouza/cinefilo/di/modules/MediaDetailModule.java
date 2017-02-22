package site.iurysouza.cinefilo.di.modules;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.mock.BehaviorDelegate;
import site.iurysouza.cinefilo.domain.moviedetail.IMovieDetailUseCase;
import site.iurysouza.cinefilo.domain.moviedetail.MovieDetailUseCase;
import site.iurysouza.cinefilo.domain.moviedetail.IMovieDetailRepository;
import site.iurysouza.cinefilo.model.data.moviedetail.MovieDetailRepository;
import site.iurysouza.cinefilo.model.data.moviedetail.storage.CloudMovieDetailDataSource;
import site.iurysouza.cinefilo.model.data.moviedetail.storage.ICloudMovieDetailDataSource;
import site.iurysouza.cinefilo.model.data.moviedetail.storage.ILocalDetailDataSource;
import site.iurysouza.cinefilo.model.data.moviedetail.storage.LocalDetailDataSource;
import site.iurysouza.cinefilo.model.data.moviedetail.services.MovieDetailService;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@Module
public class MediaDetailModule {

  //to be overriden in tests

  @Provides
  protected MovieDetailService providesMovieDetailService(Retrofit retrofit, BehaviorDelegate<MovieDetailService> delegate) {
    return retrofit.create(MovieDetailService.class);
  }

  @Provides protected ICloudMovieDetailDataSource cloudMovieDetailDataSource(MovieDetailService movieDetailService) {
    return new CloudMovieDetailDataSource(movieDetailService);
  }

  @Provides protected ILocalDetailDataSource providesLocalDetailDataStore() {
    return new LocalDetailDataSource();
  }

  @Provides protected IMovieDetailRepository providesmovieDetailRepository(ILocalDetailDataSource localDetailDataSource,
      ICloudMovieDetailDataSource cloudMovieDetailDataSource) {
    return new MovieDetailRepository(localDetailDataSource, cloudMovieDetailDataSource);
  }

  @Provides protected IMovieDetailUseCase movieDetailUseCase(MovieDetailRepository detailRepository) {
    return new MovieDetailUseCase(detailRepository);
  }
}
