package br.com.nome.flashmind.logic.model;

/**
 * Created by Alessandro Pryds on 21/12/2016.
 */
public class Deck {
    private String title;
    private int cards;

    public Deck(String title, int cards) {
        this.title = title;
        this.cards = cards;
    }


    public String getTitle() {
        return title;
    }

    public int getCardsCount() {
        return cards;
    }


}
