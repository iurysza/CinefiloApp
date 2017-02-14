package site.iurysouza.cinefilo.di.modules;

import java.util.Collections;
import rx.Observable;
import site.iurysouza.cinefilo.domain.IMovieDetailUseCase;
import site.iurysouza.cinefilo.model.data.moviedetail.IMovieDetailRepository;
import site.iurysouza.cinefilo.model.data.moviedetail.MovieDetailRepository;
import site.iurysouza.cinefilo.model.data.moviedetail.storage.ICloudMovieDetailDataSource;
import site.iurysouza.cinefilo.model.data.moviedetail.storage.ILocalDetailDataSource;
import site.iurysouza.cinefilo.model.services.MovieDetailService;
import site.iurysouza.cinefilo.tests.MovieFactory;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Iury Souza on 14/02/2017.
 */
public class MockMediaDetailModule extends MediaDetailModule {

  @Override protected ICloudMovieDetailDataSource cloudMovieDetailDataSource(
      MovieDetailService movieDetailService) {

    //given
    ICloudMovieDetailDataSource mockCloudSource = mock(ICloudMovieDetailDataSource.class);
    //when
    when(mockCloudSource.getMovieById(1))
        .thenReturn(Observable.just(MovieFactory.createValidMovieStub()));

    when(mockCloudSource.getMoviesSimilarTo(1,1))
        .thenReturn(Observable.just(Collections.emptyList()));
    //then
    return mockCloudSource;
  }

  @Override protected ILocalDetailDataSource providesLocalDetailDataStore() {
    ILocalDetailDataSource mockLocalDataSource = mock(ILocalDetailDataSource.class);
    when(mockLocalDataSource.getMovieById(1)).thenReturn(Observable.just(null));
    return mockLocalDataSource;
  }

  @Override protected IMovieDetailRepository providesmovieDetailRepository(
      ILocalDetailDataSource localDetailDataSource,
      ICloudMovieDetailDataSource cloudMovieDetailDataSource) {
    return mock(IMovieDetailRepository.class);
  }

  @Override
  protected IMovieDetailUseCase movieDetailUseCase(MovieDetailRepository detailRepository) {
    return mock(IMovieDetailUseCase.class);
  }
}
