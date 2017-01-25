package br.com.nome.flashmind.logic.rxbus;

import net.jokubasdargis.rxbus.Queue;

import br.com.nome.flashmind.logic.model.CardTextChangedEvent;
import br.com.nome.flashmind.logic.model.CustomSearchThumbnail;
import br.com.nome.flashmind.logic.rxbus.events.FlipCardEvent;
import br.com.nome.flashmind.logic.rxbus.events.ListScrolledEvent;

/**
 * Created by Alessandro Pryds on 03/01/2017.
 */

public class RxQueues {
    public static final Queue<ListScrolledEvent> LIST_SCROLLED_EVENT_QUEUE = Queue.of(ListScrolledEvent.class).build();
    public static final Queue<FlipCardEvent> FLIP_CARD_EVENT = Queue.of(FlipCardEvent.class).build();
    public static final Queue<CustomSearchThumbnail> IMAGE_SELECTED_QUEUE = Queue.of(CustomSearchThumbnail.class).build();
    public static final Queue<CardTextChangedEvent> CARD_TEXT_CHANGED = Queue.of(CardTextChangedEvent.class).build();
}
