package br.com.nome.flashmind.logic.presenter;

import java.util.ArrayList;

import br.com.nome.flashmind.logic.model.Deck;

/**
 * Created by Alessandro Pryds on 03/01/2017.
 */
public class HomePresenter {
    private IHomeView mView;
    private ArrayList<Deck> mDecks;
    private int mSelectedPosition;

    public HomePresenter(IHomeView view) {
        this.mView = view;
        this.mView.setPresenter(this);
    }

    public void start() {
        mView.setupRecentDecksRecyclerView();
        mDecks = createSampleDecks();
        mView.populateLastDecks(mDecks);

        mView.setupScrollViewListener();
    }

    private ArrayList<Deck> createSampleDecks() {
        ArrayList<Deck> decks = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            decks.add(new Deck(String.valueOf(i + 1), i * 20 - i));
        }
        return decks;
    }

    public void onDeckTouched(int position) {
        mSelectedPosition = position;
        mView.showDeckOptionsDialog();
    }

    public interface IHomeView {

        void setPresenter(HomePresenter homePresenter);

        void setupRecentDecksRecyclerView();

        void showDeckOptionsDialog();

        void populateLastDecks(ArrayList<Deck> mDecks);

        void setupScrollViewListener();
    }
}
