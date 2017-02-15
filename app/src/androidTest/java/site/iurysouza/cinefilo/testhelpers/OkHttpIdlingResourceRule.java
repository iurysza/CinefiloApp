package site.iurysouza.cinefilo.testhelpers;

/**
 * Created by Iury Souza on 14/02/2017.
 */

//public class OkHttpIdlingResourceRule implements TestRule {
//  @Override
//  public Statement apply(final Statement base, Description description) {
//    return new Statement() {
//      @Override
//      public void evaluate() throws Throwable {
//        IdlingResource idlingResource = OkHttp3IdlingResource.create(
//            "okhttp", OkHttp.getInstance());
//        Espresso.registerIdlingResources(idlingResource);
//
//        base.evaluate();
//
//        Espresso.unregisterIdlingResources(idlingResource);
//      }
//    };
//  }
//}