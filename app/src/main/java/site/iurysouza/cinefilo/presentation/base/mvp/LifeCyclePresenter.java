package site.iurysouza.cinefilo.presentation.base.mvp;

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the BaseView type that wants to be attached with.
 */
public interface LifeCyclePresenter<V extends BaseView> {

  void attachView(V mvpView);

  void dettachView();
}
