package br.com.nome.flashmind.logic.rxbus.events;

/**
 * Created by Alessandro Pryds on 03/01/2017.
 */

public class ListScrolledEvent {
    private boolean shouldHide;

    public ListScrolledEvent(boolean shouldHide) {
        this.shouldHide = shouldHide;
    }

    public boolean shouldHide() {
        return shouldHide;
    }
}
