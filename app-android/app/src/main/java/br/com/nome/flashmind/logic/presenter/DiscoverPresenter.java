package br.com.nome.flashmind.logic.presenter;

import java.util.ArrayList;

import br.com.nome.flashmind.logic.model.SharedDeck;

/**
 * Created by Alessandro Pryds on 16/01/2017.
 */
public class DiscoverPresenter {
    private IDiscoverView mView;
    private ArrayList<SharedDeck> mDecks;
    private ArrayList<SharedDeck> mLastUpdatedDecks;

    public DiscoverPresenter(IDiscoverView view) {
        this.mView = view;
        this.mView.setPresenter(this);
    }

    public void start() {
        mDecks = createSampleDecks();
        mLastUpdatedDecks = createSampleDecks();
        mView.setupSuggestionDecks(mDecks);
        mView.setupLastUpdatedDecks(mLastUpdatedDecks);
        mView.setupScrollViewListener();
    }

    private ArrayList<SharedDeck> createSampleDecks() {
        ArrayList<SharedDeck> decks = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            decks.add(new SharedDeck(String.valueOf(i + 1), i * 20 - i,"FlashMind", "Ontem"));
        }
        return decks;
    }

    public void onSuggestionDeckTouched(int position) {

    }

    public void onLastDeckTouched(int position) {

    }

    public interface IDiscoverView {
        void setPresenter(DiscoverPresenter presenter);

        void setupSuggestionDecks(ArrayList<SharedDeck> decks);

        void setupLastUpdatedDecks(ArrayList<SharedDeck> mLastUpdatedDecks);

        void setupScrollViewListener();
    }
}
