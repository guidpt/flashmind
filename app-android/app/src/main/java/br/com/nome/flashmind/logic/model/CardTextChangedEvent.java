package br.com.nome.flashmind.logic.model;

/**
 * Created by Alessandro Pryds on 22/01/2017.
 */
public class CardTextChangedEvent {
    private final int position;
    private final Card card;

    public CardTextChangedEvent(int position, Card card) {
        this.position = position;
        this.card = card;
    }

    public int getPosition() {
        return position;
    }

    public Card getCard() {
        return card;
    }
}
