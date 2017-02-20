package site.iurysouza.cinefilo.presentation.medias.filter;

import android.app.Activity;
import android.content.res.ColorStateList;
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
import java.util.List;
import me.relex.circleindicator.CircleIndicator;
import mehdi.sakout.fancybuttons.FancyButton;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.domain.MediaFilter;
import site.iurysouza.cinefilo.presentation.main.FilterEvent;
import site.iurysouza.cinefilo.presentation.medias.pager.MediaPageChangedEvent;
import site.iurysouza.cinefilo.util.Utils;

/**
 * Created by Iury Souza on 14/01/2017.
 */

public class FilterViewManager {
  private static final int BLUR_VIEW_HIDE_DELAY = 350;
  private static final int BLUR_VIEW_SHOW_DELAY = 250;
  public static final int Filter = 6;

  @BindView(R.id.media_list_filter_viewpager) ViewPager viewPager;
  @BindView(R.id.media_list_page_indicator) CircleIndicator indicator;
  @BindView(R.id.fabtoolbar_fab) FloatingActionButton filterFab;
  @BindView(R.id.main_blurred_view) RealtimeBlurView blurredView;
  @BindView(R.id.filter_btn_close) FancyButton btnClose;
  @BindView(R.id.filter_btn_apply) FancyButton btnApply;
  @BindView(R.id.fabtoolbar) FABToolbarLayout fabToolbar;
  @BindView(R.id.filter_view_header) FrameLayout filterViewHeader;
  @BindView(R.id.fabtoolbar_toolbar) RelativeLayout fabtoolbarToolbar;

  @BindColor(R.color.colorPrimary) int defaultColor;
  @BindColor(R.color.colorAccent) int selectedColor;

  private List<GenderEnum> selectedGenreList = null;
  private Integer mMinScore = null;
  private SortingMethod mSortBy = null;
  private Integer mEndDate = null;
  private Integer mStartDate = null;
  private Activity activity;

  public FilterViewManager(Activity activity) {
    this.activity = activity;
    ButterKnife.bind(this, activity);
    Utils.safeRegisterEventBus(this);
    filterFab.setBackgroundTintList(ColorStateList.valueOf(defaultColor));
    createViewPager(activity);
  }

  private void createViewPager(Activity activity) {
    FilterPagerAdapter filterAdater = new FilterPagerAdapter(activity, createGenreSelectListener());
    viewPager.setAdapter(filterAdater);
    indicator.setViewPager(viewPager);
  }

  @NonNull private OnAdapterClickListener createGenreSelectListener() {
    return new OnAdapterClickListener() {

      @Override public void onGenreSelected(List<GenderEnum> genderList) {
        selectedGenreList = genderList;
        wasFilterAdded();
      }

      @Override public void onStartDateChanged(Integer startDate) {
        mStartDate = startDate;
        wasFilterAdded();
      }

      @Override public void onEndDateChanged(Integer endDate) {
        mEndDate = endDate;
        wasFilterAdded();
      }

      @Override public void onMinScoreChanged(Integer minScore) {
        mMinScore = minScore;
        wasFilterAdded();
      }

      @Override public void onSortingMethodChanged(SortingMethod sortBy) {
        mSortBy = sortBy;
        wasFilterAdded();
      }

      @Override public void closeOnRefresh() {
        hideFilterView();
      }
    };
  }

  private boolean wasFilterAdded() {
    if (selectedGenreList == null &&
        mStartDate == null &&
        mEndDate == null &&
        mMinScore == MediaFilter.DEFAULT_RATING_SCORE &&
        mSortBy == null
        ) {
      btnApply.setBackgroundColor(defaultColor);
      return false;
    } else {
      btnApply.setBackgroundColor(selectedColor);
      return true;
    }
  }

  public boolean hideFilterIfShown() {
    if (blurredView.isShown()) {
      hideFilterView();
      return true;
    }
    return false;
  }

  private void hideFilterView() {
    fabToolbar.hide();
    blurredView.postDelayed(() -> blurredView.setVisibility(View.GONE), BLUR_VIEW_HIDE_DELAY);
  }

  private void showFilterView() {
    fabToolbar.show();
    blurredView.postDelayed(() -> blurredView.setVisibility(View.VISIBLE), BLUR_VIEW_SHOW_DELAY);
  }

  private void changeFabColor() {
    new Handler().postDelayed(() -> {
      if (!wasFilterAdded()) {
        filterFab.setBackgroundTintList(ColorStateList.valueOf(defaultColor));
        return;
      }
      filterFab.setBackgroundTintList(ColorStateList.valueOf(selectedColor));
    }, BLUR_VIEW_HIDE_DELAY);
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
        removeFilters();
        break;
      case R.id.main_blurred_view:
        hideFilterView();
        break;
      case R.id.filter_btn_apply:
        applyFilters();
        break;
      case R.id.fabtoolbar_fab:
        showFilterView();
        break;
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onPageChangedEvent(MediaPageChangedEvent event) {
    if (wasFilterAdded()) {
    removeFilters();
    }
  }

  private void removeFilters() {
    createViewPager(activity);
    hideFilterView();
    changeFabColor();
    if (wasFilterAdded()) {
      mEndDate = null;
      mStartDate = null;
      mMinScore = MediaFilter.DEFAULT_RATING_SCORE;
      selectedGenreList = null;
      mSortBy = null;
      wasFilterAdded();
      EventBus.getDefault().post(new FilterEvent(null));
    }
  }

  private void applyFilters() {
    MediaFilter filter = null;
    if (wasFilterAdded()) {
      filter = MediaFilter
          .builder()
          .endDate(mEndDate)
          .startDate(mStartDate)
          .minScore(mMinScore)
          .sortBy(mSortBy)
          .genderList(selectedGenreList)
          .build();
    }
    hideFilterView();
    changeFabColor();
    EventBus.getDefault().post(new FilterEvent(filter));
  }

  interface OnAdapterClickListener {
    void onGenreSelected(List<GenderEnum> genderList);

    void onStartDateChanged(Integer startDate);

    void onEndDateChanged(Integer endDate);

    void onMinScoreChanged(Integer minScore);

    void onSortingMethodChanged(SortingMethod sortBy);

    void closeOnRefresh();

  }
}
