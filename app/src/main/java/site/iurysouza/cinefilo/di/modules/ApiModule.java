package site.iurysouza.cinefilo.di.modules;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import java.io.File;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import javax.inject.Named;
import javax.inject.Singleton;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;
import rx.schedulers.Schedulers;
import site.iurysouza.cinefilo.BuildConfig;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.model.services.MovieDetailService;
import site.iurysouza.cinefilo.util.Constants;
import site.iurysouza.cinefilo.util.NetworkUtil;
import timber.log.Timber;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@SuppressWarnings("WeakerAccess")

@Module
public class ApiModule {
  @Provides @Singleton public Cache providesCache(Context context) {
    Cache cache = null;
    try {
      cache = new Cache(new File(context.getCacheDir(), "http-cache"), 10 * 1024 * 1024); // 10 MB
    } catch (Exception e) {
      Timber.e(e, "Could not create Cache!");
    }
    return cache;
  }

  @Provides @Singleton @Named(Constants.CACHE_CONTROL)
  public Interceptor providesCacheInterceptor() {
    return chain -> {
      Response response = chain.proceed(chain.request());

      // re-write response header to force use of cache
      CacheControl cacheControl = new CacheControl.Builder().maxAge(2, TimeUnit.MINUTES).build();

      return response.newBuilder().header(Constants.CACHE_CONTROL, cacheControl.toString()).build();
    };
  }

  @Provides @Singleton @Named(Constants.OFFLINE_CACHE)
  public Interceptor providesOfflineCacheInterceptor(final Context context) {
    return chain -> {
      Response response = chain.proceed(chain.request());
      if (!NetworkUtil.isNetworkConnected(context)) {
        CacheControl cacheControl =
            new CacheControl.Builder()
                .maxAge(7, TimeUnit.DAYS)
                .build();

        response = response.newBuilder()
            .header(Constants.CACHE_CONTROL, cacheControl.toString())
            .build();
      }
      return response;
    };
  }

  @Provides @Singleton
  public InputStream providesLocalGenres(Context context) {
    return context.getResources().openRawResource(R.raw.cinefilo_genre_list);
  }

  @Provides @Singleton public OkHttpClient providesLoggingCapableHttpClient(Cache cache,
      @Named(Constants.CACHE_CONTROL) Interceptor cacheInterceptor,
      @Named(Constants.OFFLINE_CACHE) Interceptor offlineInterceptor) {

    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(
        BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

    return new OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(offlineInterceptor)
        .addNetworkInterceptor(cacheInterceptor)
        .cache(cache)
        .build();
  }

  @Provides @Singleton
  public Retrofit providesRetrofit(OkHttpClient okHttpClient) {
    return new Retrofit.Builder()
        .baseUrl(Constants.MOVIE_DB_API.BASE_URL)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build();
  }

  //enables being overriden on test module
  @Provides @Singleton public MockWebServer providesMockWebServer() {
    return null;
  }

  @Provides @Singleton protected NetworkBehavior provideNetworkBehavior() {
    NetworkBehavior networkBehavior = NetworkBehavior.create();
    networkBehavior.setDelay(2L, TimeUnit.SECONDS);
    return networkBehavior;
  }

  @Provides @Singleton
  protected MockRetrofit providesMockRetrofit(Retrofit retrofit, NetworkBehavior networkBehavior) {
    return new MockRetrofit.Builder(retrofit)
        .networkBehavior(networkBehavior)
        .build();
  }

  @Provides @Singleton protected BehaviorDelegate<MovieDetailService> providesBehaviorDelegate(
      MockRetrofit mockRetrofit) {
    return mockRetrofit.create(MovieDetailService.class);
  }
}
