package site.iurysouza.cinefilo.presentation.moviedetail.viewpager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.List;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.domain.moviedetail.Cast;
import site.iurysouza.cinefilo.util.ImageUtils;

/**
 * Created by Iury Souza on 08/03/2017.
 */

class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastHolder> {

  private Context context;
  private List<Cast> castList;

  CastAdapter(Context context, List<Cast> castList) {
    this.context = context;
    if (castList.size() >= 6) {
      this.castList = castList.subList(0, 6);
    } else {
      this.castList = castList;
    }
  }

  @Override public CastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.cast_list_item, parent, false);
    CastHolder castHolder = new CastHolder(view);
    return castHolder;
  }

  @Override public void onBindViewHolder(CastHolder holder, int position) {
    Cast cast = castList.get(holder.getAdapterPosition());
    holder.bindToCast(cast);
  }

  @Override public int getItemCount() {
    return castList.size();
  }

  class CastHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.cast_item_picture) CircleImageView castItemPicture;
    @BindView(R.id.cast_item_name) TextView castItemName;
    @BindView(R.id.cast_item_character) TextView castItemCharacter;

    CastHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    void bindToCast(Cast cast) {
      castItemName.setText(cast.getName());
      castItemCharacter.setText(cast.getCharacter());
      Glide
          .with(context)
          .load(ImageUtils.getProfileUrl(cast.getProfilePath()))
      .dontAnimate()
          .placeholder(R.drawable.ic_avatar_placeholder)
          .centerCrop()
          .into(castItemPicture);
    }
  }
}
