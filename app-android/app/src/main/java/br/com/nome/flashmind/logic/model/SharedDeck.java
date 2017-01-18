package br.com.nome.flashmind.logic.model;

/**
 * Created by Alessandro Pryds on 16/01/2017.
 */

public class SharedDeck extends Deck {
    private String ownerName;
    private String createdDate;

    public SharedDeck(String title, int cards, String ownerName, String createdDate) {
        super(title, cards);
        this.ownerName = ownerName;
        this.createdDate = createdDate;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getCreatedDate() {
        return createdDate;
    }
}
