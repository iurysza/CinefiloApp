package site.iurysouza.cinefilo.presentation.medias.filter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
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
import site.iurysouza.cinefilo.util.ImageUtils;

/**
 * Created by Iury Souza on 15/01/2017.
 */

public class GenreGridAdapter extends RecyclerView.Adapter<GenreGridAdapter.GenreViewHolder> {

  private List<GenderEnum> genderEnumList = Arrays.asList(GenderEnum.values());
  private List<GenderEnum> selectedGenresList = new ArrayList<>();
  private Context context;
  private OnAdapterClickListener listener;

  public GenreGridAdapter(Context context, OnAdapterClickListener listener) {
    this.context = context;
    this.listener = listener;
  }

  @Override
  public GenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_grid_item, parent, false);
    return new GenreViewHolder(view);
  }

  @Override public void onBindViewHolder(GenreViewHolder holder, int position) {
    int iconRes = genderEnumList.get(position).getGenreIconRes();
    GenderEnum currentGender = genderEnumList.get(position);
    int defaultColor = R.color.colorPrimary;
    int selectedColor = R.color.appRed;
    Drawable icon = ImageUtils.changeIconColor(context, iconRes, defaultColor);
    holder.genreItemImageView.setImageDrawable(icon);
    holder.genreItemText.setText(genderEnumList.get(position).getGenreNameRes());
    initItemState(holder, currentGender, iconRes, defaultColor, selectedColor);
    holder.genreItemRoot.setOnClickListener(
        onItemSelected(holder, currentGender, iconRes, defaultColor, selectedColor));
  }

  @NonNull private View.OnClickListener onItemSelected(final GenreViewHolder holder,
      final GenderEnum currentGender, final int iconRes, int defaultColor, int selectedColor) {
    return v -> {

      if (selectedGenresList.contains(currentGender)) {
        holder.genreItemImageView.setImageDrawable(
            ImageUtils.changeIconColor(context, iconRes, defaultColor));
        selectedGenresList.remove(currentGender);
      } else {
        holder.genreItemImageView.setImageDrawable(
            ImageUtils.changeIconColor(context, iconRes, selectedColor));
        selectedGenresList.add(currentGender);
      }
      if (selectedGenresList.isEmpty()) {
        listener.onNoneSelected();
      } else {
        listener.onItemSelected();
      }
    };
  }

  private void initItemState(GenreViewHolder holder,
      GenderEnum currentGender, int iconRes, int defaultColor, int selectedColor) {
    if (selectedGenresList.contains(currentGender)) {
      holder.genreItemImageView.setImageDrawable(
          ImageUtils.changeIconColor(context, iconRes, selectedColor));
    } else {
      holder.genreItemImageView.setImageDrawable(
          ImageUtils.changeIconColor(context, iconRes, defaultColor));
    }
  }

  @Override
  public int getItemCount() {
    return genderEnumList.size();
  }

  public List<GenderEnum> getSelectedGenres() {
    return selectedGenresList;
  }

  static class GenreViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.genre_item_btn) ImageView genreItemImageView;
    @BindView(R.id.genre_item_text) TextView genreItemText;
    @BindView(R.id.genre_item_root) RelativeLayout genreItemRoot;

    GenreViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }

  public interface OnAdapterClickListener{
    void onItemSelected();

    void onNoneSelected();
  }
}