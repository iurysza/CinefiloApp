package site.iurysouza.cinefilo.presentation.medias.filter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import site.iurysouza.cinefilo.R;

/**
 * Created by Iury Souza on 14/01/2017.
 */

public class FilterViewManager {

  BehaviorSubject<String> filterSubject = BehaviorSubject.create();

  @BindView(R.id.media_list_filter_viewpager) ViewPager viewPager;
  @BindView(R.id.media_list_page_indicator) CircleIndicator indicator;

  public FilterViewManager(View view) {
    ButterKnife.bind(this, view);
    FilterPagerAdapter filterAdater = new FilterPagerAdapter(view.getContext());
    viewPager.setAdapter(filterAdater);
    indicator.setViewPager(viewPager);
  }

  public Observable getFilterSubjectAsObservable() {
    return filterSubject.asObservable();
  }

  public class FilterPagerAdapter extends PagerAdapter {

    private Context context;

    public FilterPagerAdapter(Context context) {
      this.context = context;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
      ViewGroup layout = getView(collection, FilterPagerEnum.values()[position]);
      layout.getRootView().setOnClickListener(v -> filterSubject.onNext(""));
      return layout;
    }

    private ViewGroup getView(ViewGroup collection, FilterPagerEnum filterPagerEnum) {
      LayoutInflater inflater = LayoutInflater.from(context);
      int layoutResId = filterPagerEnum.getLayoutResId();
      ViewGroup layout = (ViewGroup) inflater.inflate(layoutResId, collection, false);
      collection.addView(layout);
      return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
      collection.removeView((View) view);
    }

    @Override
    public int getCount() {
      return FilterPagerEnum.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
      return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      FilterPagerEnum customPagerEnum = FilterPagerEnum.values()[position];
      return context.getString(customPagerEnum.getTitleResId());
    }
  }
}
