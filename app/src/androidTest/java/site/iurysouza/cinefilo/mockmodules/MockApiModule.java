package site.iurysouza.cinefilo.mockmodules;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import site.iurysouza.cinefilo.di.modules.ApiModule;

import static org.mockito.Mockito.mock;

/**
 * Created by Iury Souza on 13/02/2017.
 */
public class MockApiModule extends ApiModule {
  @Override
  public Retrofit providesRetrofit(OkHttpClient okHttpClient) {
    return mock(Retrofit.class);
  }
}
