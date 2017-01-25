package br.com.nome.flashmind.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.nome.flashmind.R;
import br.com.nome.flashmind.logic.model.CustomSearchThumbnail;
import br.com.nome.flashmind.ui.viewholder.ImageViewHolder;

/**
 * Created by Alessandro Pryds on 19/01/2017.
 */
public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageViewHolder> {
    private final Context context;
    private ArrayList<CustomSearchThumbnail> items;

    public ImageRecyclerAdapter(Context context, ArrayList<CustomSearchThumbnail> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        CustomSearchThumbnail item = items.get(position);
        holder.loadImageUrl(context,item.getImageUrl());
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public void add(CustomSearchThumbnail object) {
        if (object == null)
            return;

        items.add(object);
        int pos = items.indexOf(object);
        notifyItemInserted(pos);
    }

    public void remove(int pos) {
        if (pos > items.size() - 1)
            return;

        items.remove(pos);
        notifyItemRemoved(pos);
    }

    public void remove(CustomSearchThumbnail object) {
        if (object == null)
            return;

        int pos = items.indexOf(object);
        items.remove(pos);
        notifyItemRemoved(pos);
    }

    public void replaceAll(ArrayList<CustomSearchThumbnail> images) {
        this.items.clear();
        this.items = images;
        notifyDataSetChanged();

    }
}