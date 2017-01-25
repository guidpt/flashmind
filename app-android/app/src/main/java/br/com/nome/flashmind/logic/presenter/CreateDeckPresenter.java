package br.com.nome.flashmind.logic.presenter;

import java.util.ArrayList;

import br.com.nome.flashmind.logic.model.Card;
import br.com.nome.flashmind.ui.view.IBaseView;

/**
 * Created by Alessandro Pryds on 17/01/2017.
 */
public class CreateDeckPresenter {
    private final String mDeckName;
    private ICreateDeckView mView;
    private ArrayList<Card> mCards;
    private int mCurrentRotation;
    private int mCurrentPosition;

    public CreateDeckPresenter(ICreateDeckView view, String deckName) {
        this.mView = view;
        this.mDeckName = deckName;
        this.mCurrentRotation = 0;
        this.mCurrentPosition = 0;
        this.mView.setPresenter(this);
    }

    public void start() {
        this.mCards = new ArrayList<>();
        this.mCards.add(new Card());
        mView.initToolbar(mDeckName);
        mView.setupDeckViewPager(mCards);
        mView.setNameDeckTextView(mDeckName);
    }

    public void onBtnFlipTouched() {
        mCurrentRotation += 180;
        mView.flipView(mCurrentPosition, mCurrentRotation);
    }

    public void onPageSelected(int position) {
        mCurrentPosition = position;
    }

    public void onMenuImageTouched() {
        mView.showImageSearchDialog();
    }

    public void onMenuAspectTouched() {

    }

    public void onBtnColorTouched() {
        mView.showColorPickerDialog();
    }

    public void onColorSelected(int color) {
        mView.tintDeck(color);
    }

    public void onMenuAddTouched() {
        mCards.add(new Card());
        mView.newCardAdded();
    }


    public interface ICreateDeckView extends IBaseView{
        void setPresenter(CreateDeckPresenter presenter);

        void initToolbar(String title);

        void setupDeckViewPager(ArrayList<Card> mDecks);

        void flipView(int mCurrentPosition, int mCurrenRotation);

        void showImageSearchDialog();

        void showColorPickerDialog();

        void tintDeck(int color);

        void newCardAdded();

        void setNameDeckTextView(String mDeckName);
    }
}
