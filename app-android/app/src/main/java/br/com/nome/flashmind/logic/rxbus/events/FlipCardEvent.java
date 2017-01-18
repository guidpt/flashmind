package br.com.nome.flashmind.logic.rxbus.events;

/**
 * Created by Alessandro Pryds on 17/01/2017.
 */

public class FlipCardEvent {
    private int position;

    public FlipCardEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
