package site.iurysouza.cinefilo.presentation.medias.filter;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.github.mmin18.widget.RealtimeBlurView;
import me.relex.circleindicator.CircleIndicator;
import mehdi.sakout.fancybuttons.FancyButton;
import org.greenrobot.eventbus.EventBus;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.presentation.main.FilterEvent;

import static site.iurysouza.cinefilo.R.id.fabtoolbar;
import static site.iurysouza.cinefilo.presentation.main.MainActivity.BLUR_VIEW_HIDE_DELAY;
import static site.iurysouza.cinefilo.presentation.main.MainActivity.BLUR_VIEW_SHOW_DELAY;

/**
 * Created by Iury Souza on 14/01/2017.
 */

public class FilterViewManager {
  private final Resources resources;
  private GenderEnum selectedGender = GenderEnum.NONE_SELECTED;
  @BindView(R.id.media_list_filter_viewpager) ViewPager viewPager;
  @BindView(R.id.media_list_page_indicator) CircleIndicator indicator;
  @BindView(R.id.fabtoolbar_fab) FloatingActionButton filterFab;
  @BindView(R.id.main_blurred_view) RealtimeBlurView blurredView;
  @BindView(R.id.filter_btn_close) FancyButton btnClose;
  @BindView(R.id.filter_btn_apply) FancyButton btnApply;
  @BindView(fabtoolbar) FABToolbarLayout fabToolbar;
  @BindView(R.id.filter_view_header) FrameLayout filterViewHeader;
  @BindView(R.id.fabtoolbar_toolbar) RelativeLayout fabtoolbarToolbar;

  public FilterViewManager(Activity activity) {
    ButterKnife.bind(this, activity);
    resources = activity.getResources();
    FilterPagerAdapter filterAdater = new FilterPagerAdapter(activity, createGenreSelectListener());
    viewPager.setAdapter(filterAdater);
    indicator.setViewPager(viewPager);
  }

  @NonNull private OnAdapterClickListener createGenreSelectListener() {
    return gender -> {
      selectedGender = gender;
      changeApplyColor(selectedGender);
    };
  }

  public void onBackPressed() {
    if (blurredView.isShown()) {
      hideFilterView();
    }
  }

  private void hideFilterView() {
    fabToolbar.hide();
    blurredView.postDelayed(() -> blurredView.setVisibility(View.GONE), BLUR_VIEW_HIDE_DELAY);
  }

  private void showFilterView() {
    fabToolbar.show();
    blurredView.postDelayed(() -> blurredView.setVisibility(View.VISIBLE), BLUR_VIEW_SHOW_DELAY);
  }

  private void changeFabColor(GenderEnum gender) {
    new Handler().postDelayed(() -> {
      if (gender.equals(GenderEnum.NONE_SELECTED)) {
        filterFab.setBackgroundTintList(
            ColorStateList.valueOf(resources.getColor(R.color.colorPrimary)));
        return;
      }
      filterFab.setBackgroundTintList(
          ColorStateList.valueOf(resources.getColor(R.color.colorAccent)));
    }, BLUR_VIEW_HIDE_DELAY);
  }


  private void changeApplyColor(GenderEnum gender) {
    if (gender.equals(GenderEnum.NONE_SELECTED)) {
      btnApply.setBackgroundColor(resources.getColor(R.color.colorPrimary));
      return;
    }
    btnApply.setBackgroundColor(resources.getColor(R.color.colorAccent));
  }
  @OnClick({
      R.id.filter_btn_close,
      R.id.filter_btn_apply,
      R.id.fabtoolbar_fab,
      R.id.main_blurred_view
  })
  void onClick(View view) {
    switch (view.getId()) {
      case R.id.filter_btn_close:
      case R.id.main_blurred_view:
        hideFilterView();
        break;
      case R.id.filter_btn_apply:
        hideFilterView();
        changeFabColor(selectedGender);
        EventBus.getDefault().post(new FilterEvent(selectedGender));
        break;
      case R.id.fabtoolbar_fab:
        showFilterView();
        break;
    }
  }

  public interface OnAdapterClickListener {
    void onItemSelected(GenderEnum gender);
  }
}
