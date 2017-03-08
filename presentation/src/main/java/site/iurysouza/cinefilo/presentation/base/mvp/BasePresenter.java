package site.iurysouza.cinefilo.presentation.base.mvp;

/**
 * Base class that implements the LifeCyclePresenter interface and provides a base implementation
 * for
 * attachView() and dettachView(). It also handles keeping a reference to the mvpView
 * that
 * can be accessed from the children classes by calling getBaseView().
 */
public class BasePresenter<T extends BaseView> implements LifeCyclePresenter<T> {

  private T baseView;

  @Override public void attachView(T view) {
    this.baseView = view;
  }

  @Override public void dettachView() {
    baseView = null;
  }

  public boolean isViewAttached() {
    return baseView != null;
  }

  public T getBaseView() {
    return baseView;
  }

  public void checkViewAttached() {
    if (!isViewAttached()) throw new MvpViewNotAttachedException();
  }

  public static class MvpViewNotAttachedException extends RuntimeException {
    public MvpViewNotAttachedException() {
      super("Please call LifeCyclePresenter.attachView(BaseView) before"
          + " requesting data to the LifeCyclePresenter");
    }
  }
}

