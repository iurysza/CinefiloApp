package site.iurysouza.cinefilo.domain;

import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import rx.Observable;
import site.iurysouza.cinefilo.model.data.entity.MovieDetailValue;
import site.iurysouza.cinefilo.model.data.moviedetail.MovieDetailRepository;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Iury Souza on 08/02/2017.
 */
@RunWith(MockitoJUnitRunner.class)

public class MovieDetailUseCaseTest {

  @Mock MovieDetailRepository mockRepository;
  private MovieDetailUseCase useCase;

  @Before
  public void setUp() throws Exception {
    useCase = new MovieDetailUseCase(mockRepository);
  }

  @Test
  public void movieDetailUseCaseCallsRepo() throws Exception {
    //given
    int movieId = anyInt();
    when(mockRepository.getMovieById(movieId)).thenReturn(
        Observable.just(any(MovieDetailValue.class)));

    //when
    useCase.getMovieById(movieId);

    //then
    verify(mockRepository).getMovieById(movieId);
  }

  @Test
  public void similarMoviesUseCaseCallsRepo() throws Exception {
    //given
    int movieId = anyInt();
    int page = anyInt();
    when(mockRepository.getMoviesSimilarTo(movieId, page)).thenReturn(
        Observable.just(Collections.emptyList()));

    //when
    useCase.geMoviesSimilarTo(movieId, page);

    //then
    verify(mockRepository).getMoviesSimilarTo(movieId, page);
  }
}