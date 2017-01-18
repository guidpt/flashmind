package br.com.nome.flashmind.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.nome.flashmind.R;
import br.com.nome.flashmind.logic.model.Deck;
import br.com.nome.flashmind.logic.model.SharedDeck;
import br.com.nome.flashmind.ui.viewholder.DeckViewHolder;

/**
 * Created by Alessandro Pryds on 21/12/2016.
 */
public class SuggestionDeckRecyclerAdapter extends RecyclerView.Adapter<DeckViewHolder> {
    private final Context context;
    private List<SharedDeck> items;

    public SuggestionDeckRecyclerAdapter(Context context, ArrayList<SharedDeck> decks) {
        this.items = decks;
        this.context = context;
    }

    @Override
    public DeckViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_suggestion_deck, parent, false);
        return new DeckViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DeckViewHolder holder, int position) {
        Deck item = items.get(position);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public void add(SharedDeck object) {
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

    public void replaceAll(ArrayList<SharedDeck> decks) {
        items.clear();
        items.addAll(decks);
        notifyDataSetChanged();
    }
}