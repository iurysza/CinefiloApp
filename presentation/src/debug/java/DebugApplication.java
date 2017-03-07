import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import site.iurysouza.cinefilo.CineApplication;
import timber.log.Timber;

/**
 * Created by Iury Souza on 12/10/2016.
 */

public class DebugApplication extends CineApplication {
  @Override public void onCreate() {
    super.onCreate();

    // Create an InitializerBuilder
    // Enable Chrome DevTools
    // Enable command line interface
    Stetho.initialize(Stetho.newInitializerBuilder(this)
        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
        .build());


    Timber.plant(new Timber.DebugTree() {
      @Override protected String createStackElementTag(StackTraceElement element) {
        return super.createStackElementTag(element)
            + ":"
            + element.getLineNumber()
            + " "
            + element.getMethodName();
      }
    });

    RxJavaPlugins.getInstance().registerErrorHandler(new RxJavaErrorHandler() {
      @Override public void handleError(Throwable e) {
        super.handleError(e);
        Timber.e(e.toString());
      }
    });
  }
}