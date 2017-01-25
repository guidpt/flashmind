package br.com.nome.flashmind.logic.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;

/**
 * Created by Alessandro Pryds on 21/12/2016.
 */
@Entity(indexes = {
        @Index(value = "title, cards DESC")
})
public class Deck {
    private String title;
    private int cards;

    @Generated(hash = 560171503)
    public Deck(String title, int cards) {
        this.title = title;
        this.cards = cards;
    }


    @Generated(hash = 626573677)
    public Deck() {
    }


    public String getTitle() {
        return title;
    }

    public int getCardsCount() {
        return cards;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public int getCards() {
        return this.cards;
    }


    public void setCards(int cards) {
        this.cards = cards;
    }

}
