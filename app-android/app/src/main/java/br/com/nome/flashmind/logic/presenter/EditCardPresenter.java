package br.com.nome.flashmind.logic.presenter;

import com.google.common.base.Strings;

import br.com.nome.flashmind.application.FlashMindApplication;
import br.com.nome.flashmind.logic.model.Card;
import br.com.nome.flashmind.logic.model.CardTextChangedEvent;
import br.com.nome.flashmind.logic.rxbus.RxQueues;

/**
 * Created by Alessandro Pryds on 22/01/2017.
 */
public class EditCardPresenter {
    private final Card mCard;
    private final int mPosition;
    private IEditCardView mView;

    public EditCardPresenter(Card card, int position, IEditCardView view) {
        this.mView = view;
        this.mCard = card;
        this.mPosition = position;
        this.mView.setPresenter(this);
    }

    public void start() {
        mView.createSubscriptions();
    }

    public void onFrontTextTyped(String text) {
        if(!Strings.isNullOrEmpty(text)){
            mView.showLabel();
        }else{
            mView.hideLabel();
        }
        FlashMindApplication.getRxBus().publish(RxQueues.CARD_TEXT_CHANGED, new CardTextChangedEvent(mPosition, mCard));
    }

    public void onBackTextTyped(String text) {
        if(!Strings.isNullOrEmpty(text)){
            mView.showBackLabel();
        }else{
            mView.hideBackLabel();
        }
        FlashMindApplication.getRxBus().publish(RxQueues.CARD_TEXT_CHANGED, new CardTextChangedEvent(mPosition, mCard));
    }

    public interface IEditCardView {
        void setPresenter(EditCardPresenter presenter);

        void showLabel();

        void hideLabel();

        void showBackLabel();

        void hideBackLabel();

        void createSubscriptions();

    }
}
