package site.iurysouza.cinefilo.presentation.mediadetail;

import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import rx.Observable;
import site.iurysouza.cinefilo.domain.MovieDetailUseCase;
import site.iurysouza.cinefilo.model.data.entity.MovieDetailValue;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Iury Souza on 07/02/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieDetailPresenterTest {

  private MovieDetailPresenter presenter;
  @Mock private MovieDetailUseCase mockUseCase;
  @Mock private MovieDetailView mockView;
  @Mock private MovieDetailValue mockMovie;

  @Before
  public void setUp() throws Exception {
    //initialize presenter
    presenter = new MovieDetailPresenter(mockUseCase);
    presenter.attachView(mockView);
  }

  @Test
  public void testGetMovieByIdTriggersUpdateMovieData() throws Exception {
    // Given an initialized MovieDetailPresenter with a stubbed MovieUseCase that returns a MovieDetailValue mock
    int movieId = 20;
    when(mockUseCase.getMovieById(movieId)).thenReturn(Observable.just(mockMovie));

    //when MovieDetailPresenter is asked to load a Movie with the give ID
    presenter.getMovieDetailById(movieId);

    //Verify that MovieDetailUseCase gets called and that MovieDetailView is called with the MovieDetailValue mock
    verify(mockUseCase).getMovieById(movieId);
    verify(mockView).updateMovieData(mockMovie);
  }

  @Test
  public void testShowErrorWarningIfUseCaseSendsAnError() throws Exception {
    int movieId = 20;
    when(mockUseCase.getMovieById(movieId)).thenReturn(
        Observable.error(new RuntimeException("Something went Wrong")));
    presenter.getMovieDetailById(movieId);
    verify(mockUseCase).getMovieById(movieId);
    verify(mockView).showErrorWarning();
  }

  @Test
  public void testGetSimilarMoviesWithSuccess() throws Exception {
    //Given
    int movieId = anyInt();
    int page = anyInt();
    when(mockUseCase.geMoviesSimilarTo(movieId, page)).thenReturn(
        Observable.just(Collections.emptyList()));

    //When
    presenter.getMoviesSimilarTo(movieId, page);

    //Then
    verify(mockUseCase).geMoviesSimilarTo(movieId, page);
    verify(mockView).showSimilarMovies(Collections.emptyList());
  }

  @Test
  public void getSimilarMoviesWithError() throws Exception {
    //Given
    int movieId = anyInt();
    int page = anyInt();
    when(mockUseCase.geMoviesSimilarTo(movieId, page)).thenReturn(
        Observable.error(new RuntimeException("Something went Wrong")));

    //When
    presenter.getMoviesSimilarTo(movieId, page);

    //Then
    verify(mockUseCase).geMoviesSimilarTo(movieId, page);
    verify(mockView).showErrorWarning();
  }
}