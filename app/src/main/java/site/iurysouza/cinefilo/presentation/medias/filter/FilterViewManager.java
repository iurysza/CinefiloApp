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
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.github.mmin18.widget.RealtimeBlurView;
import java.util.Date;
import java.util.List;
import me.relex.circleindicator.CircleIndicator;
import mehdi.sakout.fancybuttons.FancyButton;
import org.greenrobot.eventbus.EventBus;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.domain.MediaFilter;
import site.iurysouza.cinefilo.presentation.main.FilterEvent;

import static site.iurysouza.cinefilo.R.id.fabtoolbar;

/**
 * Created by Iury Souza on 14/01/2017.
 */

public class FilterViewManager {
  private static final int BLUR_VIEW_HIDE_DELAY = 450;
  private static final int BLUR_VIEW_SHOW_DELAY = 550;

  private final Resources resources;
  @BindView(R.id.media_list_filter_viewpager) ViewPager viewPager;
  @BindView(R.id.media_list_page_indicator) CircleIndicator indicator;
  @BindView(R.id.fabtoolbar_fab) FloatingActionButton filterFab;
  @BindView(R.id.main_blurred_view) RealtimeBlurView blurredView;
  @BindView(R.id.filter_btn_close) FancyButton btnClose;
  @BindView(R.id.filter_btn_apply) FancyButton btnApply;
  @BindView(fabtoolbar) FABToolbarLayout fabToolbar;
  @BindView(R.id.filter_view_header) FrameLayout filterViewHeader;
  @BindView(R.id.fabtoolbar_toolbar) RelativeLayout fabtoolbarToolbar;

  @BindColor(R.color.colorPrimary) int defaultColor;
  @BindColor(R.color.colorAccent) int selectedColor;

  private List<GenderEnum> selectedGenreList = null;
  private Integer mMinScore = null;
  private Date mEndDate = null;
  private Date mStartDate = null;

  public FilterViewManager(Activity activity) {
    ButterKnife.bind(this, activity);
    resources = activity.getResources();
    FilterPagerAdapter filterAdater = new FilterPagerAdapter(activity, createGenreSelectListener());
    viewPager.setAdapter(filterAdater);
    indicator.setViewPager(viewPager);
  }

  @NonNull private OnAdapterClickListener createGenreSelectListener() {
    return new OnAdapterClickListener() {

      @Override public void onGenreSelected(List<GenderEnum> genderList) {
        selectedGenreList = genderList;
        validateChanges();
      }

      @Override public void onStartDateChanged(Date startDate) {
        mStartDate = startDate;
        validateChanges();
      }

      @Override public void onEndDateChanged(Date endDate) {
        mEndDate = endDate;
        validateChanges();
      }

      @Override public void onMinScoreChanged(int minScore) {
        mMinScore = minScore;
        validateChanges();
      }
    };
  }

  void validateChanges() {
    if (selectedGenreList == null &&
        mStartDate == null &&
        mEndDate == null &&
        mMinScore == null
        ) {
      btnApply.setBackgroundColor(defaultColor);
    } else {
      btnApply.setBackgroundColor(selectedColor);
    }
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

  private void changeFabColor(List<GenderEnum> genderList) {
    new Handler().postDelayed(() -> {
      if (genderList.isEmpty()) {
        filterFab.setBackgroundTintList(ColorStateList.valueOf(defaultColor));
        return;
      }
      filterFab.setBackgroundTintList(ColorStateList.valueOf(selectedColor));
    }, BLUR_VIEW_HIDE_DELAY);
  }

  private void checkFilterChanges(Object filterValue) {
    if (filterValue == null) {
      btnApply.setBackgroundColor(defaultColor);
    } else {
      btnApply.setBackgroundColor(selectedColor);
    }
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
        onApplyClicked();
        break;
      case R.id.fabtoolbar_fab:
        showFilterView();
        break;
    }
  }

  private void onApplyClicked() {
    MediaFilter filter = MediaFilter
        .builder()
        .endDate(mEndDate)
        .startDate(mStartDate)
        .minScore(mMinScore)
        .genderList(selectedGenreList)
        .build();
    hideFilterView();
    changeFabColor(selectedGenreList);
    EventBus.getDefault().post(new FilterEvent(filter));
  }

  public interface OnAdapterClickListener {
    void onGenreSelected(List<GenderEnum> genderList);

    void onStartDateChanged(Date startDate);

    void onEndDateChanged(Date endDate);

    void onMinScoreChanged(int minScore);
  }
}
