package site.iurysouza.cinefilo.presentation.medias;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.domain.entity.WatchMediaValue;
import site.iurysouza.cinefilo.presentation.medias.filter.GenderEnum;
import site.iurysouza.cinefilo.util.ImageUtils;

/**
 * Created by Iury Souza on 15/12/2016.
 */

public final class MediaItemView extends FrameLayout {
  @BindView(R.id.movie_item_picture_imageview) ImageView movieImage;
  @BindView(R.id.movie_item_picture_card) CardView movieCard;
  @BindView(R.id.movie_item_genre) CardView genreCard;
  @BindView(R.id.movie_item_genre_text) TextView genreText;
  @BindView(R.id.movie_item_overview_text) TextView overviewText;
  @BindView(R.id.movie_item_rating) RatingBar movieRating;
  @BindView(R.id.movie_item_title_text) TextView movieTitle;
  @BindView(R.id.movie_item_card) CardView backgroundCard;

  public MediaItemView(Context context, AttributeSet attrs) {
    super(context, attrs);

    TypedValue outValue = new TypedValue();
    context.getTheme().resolveAttribute(android.R.attr.textColorSecondary, outValue, true);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
  }

  public void bindTo(WatchMediaValue realmMovie, Picasso picasso) {
    String posterPath = realmMovie.posterPath();
    if (posterPath != null) {
      String posterUrl = ImageUtils.getPosterUrl(posterPath);
      picasso.load(posterUrl)
          .fit()
          .into(movieImage);
    }

    movieTitle.setText(realmMovie.name());

    float movieRating = (float) (realmMovie.voteAverage() / 2);
    this.movieRating.setRating(movieRating);

    overviewText.setText(realmMovie.overview());
    Integer genre = realmMovie.genre();

    if (genre != null) {
      GenderEnum genderEnum = GenderEnum.getGenreById(genre);
      if (genderEnum != null) {
        genreText.setText(getContext().getString(genderEnum.getGenreNameRes()));
        genreCard.setCardBackgroundColor(
            (getContext().getResources().getColor(genderEnum.getColorRes())));
      }
    }
  }
}