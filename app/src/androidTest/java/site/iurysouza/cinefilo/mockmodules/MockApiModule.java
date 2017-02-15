package site.iurysouza.cinefilo.mockmodules;

import site.iurysouza.cinefilo.di.modules.ApiModule;

/**
 * Created by Iury Souza on 13/02/2017.
 */
public class MockApiModule extends ApiModule {

  /**
   *
   * using mockwebserver
   */
/*
  @Override public MockWebServer providesMockWebServer() {
    return new MockWebServer();
  }
  @Override
  public Retrofit providesRetrofit(OkHttpClient okHttpClient, MockWebServer mockWebServer) {
    return new Retrofit.Builder()
        .baseUrl(mockWebServer.url("/").toString())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build();
  }

  */


  //@Override protected NetworkBehavior provideNetworkBehavior() {
  //  NetworkBehavior networkBehavior = NetworkBehavior.create();
  //  networkBehavior.setDelay(2L, TimeUnit.SECONDS);
  //  return networkBehavior;
  //}
  //
  //@Override protected MockRetrofit providesMockRetrofit(Retrofit retrofit, NetworkBehavior networkBehavior) {
  //  return new MockRetrofit.Builder(retrofit)
  //      .networkBehavior(networkBehavior)
  //      .build();
  //}
  //
  //@Override protected BehaviorDelegate<MovieDetailService> providesBehaviorDelegate(MockRetrofit mockRetrofit) {
  //  return mockRetrofit.create(MovieDetailService.class);
  //}


}
