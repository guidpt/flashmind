package br.com.nome.flashmind.ui.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.nome.flashmind.R;
import br.com.nome.flashmind.logic.model.Deck;
import br.com.nome.flashmind.ui.viewholder.DeckViewHolder;

/**
 * Created by Alessandro Pryds on 21/12/2016.
 */
public class DeckRecyclerAdapter extends RecyclerView.Adapter<DeckViewHolder> {
    private final Context context;
    private final boolean isVertical;
    private List<Deck> items;

    public DeckRecyclerAdapter(Context context, boolean vertical) {
        this.items = new ArrayList<>();
        this.context = context;
        this.isVertical = vertical;
    }

    @Override
    public DeckViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View v = null;
        if (isVertical) {
            switch (viewType) {
                case ViewType.DECK_ITEM:
                    v = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.recycler_grid_deck_item, parent, false);
                    break;
                case ViewType.NEW_ITEM:
                    v = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.recycler_grid_new_deck_item, parent, false);
                    break;
            }
        } else {
            switch (viewType) {
                case ViewType.DECK_ITEM:
                    v = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.recycler_list_deck_item, parent, false);
                    break;
                case ViewType.NEW_ITEM:
                    v = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.recycler_list_new_deck_item, parent, false);
                    break;
            }
        }
        return new DeckViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DeckViewHolder holder, int position) {
        Deck item = items.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? ViewType.NEW_ITEM : ViewType.DECK_ITEM;
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public void add(Deck object) {
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

    public void remove(Deck object) {
        if (object == null)
            return;

        int pos = items.indexOf(object);
        items.remove(pos);
        notifyItemRemoved(pos);
    }

    public void replaceAll(ArrayList<Deck> decks) {
        items.clear();
        items.addAll(decks);
        notifyDataSetChanged();
    }

    @IntDef({ViewType.DECK_ITEM, ViewType.NEW_ITEM})
    private @interface ViewType {
        int NEW_ITEM = 0;
        int DECK_ITEM = 1;
    }
}