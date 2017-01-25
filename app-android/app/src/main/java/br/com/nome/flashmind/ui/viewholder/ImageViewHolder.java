package br.com.nome.flashmind.ui.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import br.com.nome.flashmind.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alessandro Pryds on 19/01/2017.
 */
public class ImageViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.imageView)
    protected ImageView imageView;

    public ImageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void loadImageUrl(Context context, String url){
        Glide.with(context).load(url).into(imageView);
    }

}