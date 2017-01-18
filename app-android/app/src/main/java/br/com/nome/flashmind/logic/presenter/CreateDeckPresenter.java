package br.com.nome.flashmind.logic.presenter;

import java.util.ArrayList;

import br.com.nome.flashmind.logic.model.Card;

/**
 * Created by Alessandro Pryds on 17/01/2017.
 */
public class CreateDeckPresenter {
    private ICreateDeckView mView;
    private ArrayList<Card> mCards;
    private int mCurrentRotation;
    private int mCurrentPosition;

    public CreateDeckPresenter(ICreateDeckView view) {
        this.mView = view;
        this.mCurrentRotation = 0;
        this.mCurrentPosition = 0;
        this.mView.setPresenter(this);
    }

    public void start() {
        this.mCards = createSampleCardList();
        mView.initToolbar();
        mView.setupDeckViewPager(mCards);
    }

    private ArrayList<Card> createSampleCardList() {
        ArrayList<Card> cards = new ArrayList<>();
        for(int i = 0; i < 5; i++)
            cards.add(new Card());
        return cards;
    }

    public void onBtnFlipTouched() {
        mCurrentRotation += 180;
        mView.flipView(mCurrentPosition, mCurrentRotation);
    }

    public void onPageSelected(int position) {
        mCurrentPosition = position;
    }

    public interface ICreateDeckView {
        void setPresenter(CreateDeckPresenter presenter);

        void initToolbar();

        void setupDeckViewPager(ArrayList<Card> mDecks);

        void flipView(int mCurrentPosition, int mCurrenRotation);
    }
}
