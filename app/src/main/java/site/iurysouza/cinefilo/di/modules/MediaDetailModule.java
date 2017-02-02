package site.iurysouza.cinefilo.di.modules;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import site.iurysouza.cinefilo.domain.MovieDetailUseCase;
import site.iurysouza.cinefilo.model.data.moviedetail.MovieDetailRepository;
import site.iurysouza.cinefilo.model.data.moviedetail.storage.CloudMovieDetailDataSource;
import site.iurysouza.cinefilo.model.data.moviedetail.storage.LocalDetailDataSource;
import site.iurysouza.cinefilo.model.services.MovieDetailService;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@Module
public class MediaDetailModule {
  @Provides
  MovieDetailService providesMovieDetailService(Retrofit retrofit) {
    return retrofit.create(MovieDetailService.class);
  }

  @Provides CloudMovieDetailDataSource cloudMovieDetailDataSource(MovieDetailService movieDetailService) {
    return new CloudMovieDetailDataSource(movieDetailService);
  }

  @Provides LocalDetailDataSource providesLocalDetailDataStore() {
    return new LocalDetailDataSource();
  }


  @Provides MovieDetailRepository providesmovieDetailRepository(LocalDetailDataSource localDetailDataSource,
      CloudMovieDetailDataSource cloudMovieDetailDataSource) {
    return new MovieDetailRepository(localDetailDataSource, cloudMovieDetailDataSource);
  }

  @Provides MovieDetailUseCase movieDetailUseCase(MovieDetailRepository detailRepository) {
    return new MovieDetailUseCase(detailRepository);
  }
}
