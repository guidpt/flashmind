package br.com.nome.flashmind.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Alessandro Pryds on 21/12/2016.
 */
public class DeckViewHolder extends RecyclerView.ViewHolder {
    public DeckViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}