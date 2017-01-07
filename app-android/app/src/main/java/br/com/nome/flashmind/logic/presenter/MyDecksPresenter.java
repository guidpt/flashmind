package br.com.nome.flashmind.logic.presenter;

import java.util.ArrayList;

import br.com.nome.flashmind.logic.model.Deck;

/**
 * Created by Alessandro Pryds on 21/12/2016.
 */
public class MyDecksPresenter {
    private IMyDecksView mView;
    private ArrayList<Deck> mDecks;
    private int mSelectedPosition;
    private Deck mSelectedDeck;

    public MyDecksPresenter(IMyDecksView mView) {
        this.mView = mView;
        this.mDecks = new ArrayList<>();
        this.mView.setPresenter(this);
    }

    public void start(){
        mView.setupRecyclerView();
        mDecks = createSampleDecks();
        mView.populateRecyclerView(mDecks);
    }

    private ArrayList<Deck> createSampleDecks() {
        ArrayList<Deck> decks = new ArrayList<>();
        for(int i = 0; i < 15; i++){
            decks.add(new Deck(String.valueOf(i+1), i*20-i));
        }
        return decks;
    }

    public void onDeckTouched(int position){
        if(position == 0){
            mView.showCreateNewDeckDialog();
            return;
        }
        mSelectedPosition = position-1;
        mSelectedDeck = mDecks.get(mSelectedPosition);
        mView.showDeckOptionsDialog();
    }
    public void onBtnDeleteDeckTouched(){
        mDecks.remove(mSelectedPosition);
        mView.removeDeck(mSelectedPosition);
    }

    public void onBtnShareDeckTouched(){
        //TODO: Check if user is logged in
        //TODO: Share deck
    }

    public void onBtnCreateDeckTouched(){
        mView.navigateToCreateDeckActivity();
    }

    public void onBtnPlaySelectedDeckTouched() {
        mView.navigateToPlayDeckActivity(mSelectedDeck);
    }

    public void onBtnTrainSelectedDeckTouched() {
        mView.navigateToTrainDeckActivity(mSelectedDeck);
    }

    public void onBtnEditSelectedDeckTouched() {
        mView.navigateToEditDeckActivity(mSelectedDeck);
    }

    public interface IMyDecksView{
        void setupRecyclerView();

        void populateRecyclerView(ArrayList<Deck> decks);

        void setPresenter(MyDecksPresenter presenter);

        void navigateToCreateDeckActivity();

        void showDeckOptionsDialog();

        void showCreateNewDeckDialog();

        void removeDeck(int mSelectedPosition);

        void navigateToPlayDeckActivity(Deck selectedDeck);

        void navigateToEditDeckActivity(Deck selectedDeck);

        void navigateToTrainDeckActivity(Deck selectedDeck);
    }
}
