package site.iurysouza.cinefilo.presentation.medias.filter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import site.iurysouza.cinefilo.R;

import static site.iurysouza.cinefilo.util.ImageUtils.changeIconColor;

/**
 * Created by Iury Souza on 15/01/2017.
 */

class GenreGridAdapter extends RecyclerView.Adapter<GenreGridAdapter.GenreViewHolder> {

  private List<GenderEnum> genderEnumList = Arrays.asList(GenderEnum.values());
  private List<GenderEnum> selectedGenreList = new ArrayList<>();
  private GenderEnum selectedGenre;
  private Context context;
  private FilterViewManager.OnAdapterClickListener listener;

  GenreGridAdapter(Context context, FilterViewManager.OnAdapterClickListener listener) {
    this.context = context;
    this.listener = listener;
  }

  @Override
  public GenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater
        .from(parent.getContext())
        .inflate(R.layout.genre_grid_item, parent, false);

    GenreViewHolder vh = new GenreViewHolder(view);
    vh.genreItemRoot.setOnClickListener(v -> vh.onItemSelected());
    return vh;
  }

  @Override public void onBindViewHolder(GenreViewHolder holder, int position) {
    holder.bindTo(genderEnumList.get(holder.getAdapterPosition()));
  }

  @Override
  public int getItemCount() {
    return genderEnumList.size();
  }

  class GenreViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.genre_item_btn) ImageView genreItemImageView;
    @BindView(R.id.genre_item_text) TextView genreItemText;
    @BindView(R.id.genre_item_root) RelativeLayout genreItemRoot;
    @DrawableRes int iconRes;
    @ColorRes int defaultColor = R.color.colorPrimary;
    @ColorRes int selectedColor = R.color.appRed;
    GenderEnum currentItemGender;

    GenreViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }

    void bindTo(GenderEnum genderEnum) {
      currentItemGender = genderEnum;
      iconRes = genderEnum.getGenreIconRes();
      Drawable icon = changeIconColor(context, iconRes, defaultColor);
      genreItemImageView.setImageDrawable(icon);
      genreItemText.setText(genderEnum.getGenreNameRes());
      initItemState(currentItemGender, iconRes);
    }

    private void initItemState(GenderEnum currentGender, int iconRes) {
      if (selectedGenreList.contains(currentGender)) {
        Drawable unSelectedIcon = changeIconColor(context, iconRes, selectedColor);
        genreItemImageView.setImageDrawable(unSelectedIcon);
      } else {
        Drawable selectedIcon = changeIconColor(context, iconRes, defaultColor);
        genreItemImageView.setImageDrawable(selectedIcon);
      }
    }

    void onItemSelected() {
      if (selectedGenreList.contains(currentItemGender)) {
        selectedGenreList.remove(currentItemGender);
      } else {
        selectedGenreList.add(currentItemGender);
      }
        listener.onItemSelected(selectedGenreList);
      notifyItemChanged(getAdapterPosition());
    }

    //void onItemClicked() {
    //  int previousPosition = -1;
    //  if (selectedGenre != null) {
    //    previousPosition = genderEnumList.indexOf(selectedGenre);
    //  }
    //  if (selectedGenre != null && selectedGenre.equals(currentItemGender)) {
    //    Drawable unselectedIcon = changeIconColor(context, iconRes, defaultColor);
    //    genreItemImageView.setImageDrawable(unselectedIcon);
    //    selectedGenre = null;
    //  } else {
    //    Drawable selectedIcon = changeIconColor(context, iconRes, selectedColor);
    //    genreItemImageView.setImageDrawable(selectedIcon);
    //    selectedGenre = currentItemGender;
    //  }
    //  if (selectedGenre == null) {
    //    listener.onItemSelected(GenderEnum.NONE_SELECTED);
    //  } else {
    //    listener.onItemSelected(selectedGenre);
    //  }
    //  //rebinds previous selected item
    //  if (previousPosition != -1) {
    //    notifyItemChanged(previousPosition);
    //  }
    //}
  }
}