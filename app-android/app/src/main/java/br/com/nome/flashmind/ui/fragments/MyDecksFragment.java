package br.com.nome.flashmind.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.nome.flashmind.R;
import br.com.nome.flashmind.application.FlashMindApplication;
import br.com.nome.flashmind.logic.model.Deck;
import br.com.nome.flashmind.logic.presenter.MyDecksPresenter;
import br.com.nome.flashmind.logic.rxbus.RxQueues;
import br.com.nome.flashmind.logic.rxbus.events.ListScrolledEvent;
import br.com.nome.flashmind.ui.adapter.DeckRecyclerAdapter;
import br.com.nome.flashmind.utils.HideShowScrollListener;
import br.com.nome.flashmind.utils.RecyclerItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MyDecksFragment extends Fragment implements MyDecksPresenter.IMyDecksView {

    @BindView(R.id.rvDecks)
    RecyclerView rvDecks;
    private DeckRecyclerAdapter mAdapter;
    private MyDecksPresenter mPresenter;

    public MyDecksFragment() {
        // Required empty public constructor
    }

    public static MyDecksFragment newInstance() {
        MyDecksFragment fragment = new MyDecksFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_decks, container, false);
        ButterKnife.bind(this, view);
        new MyDecksPresenter(this);
        return view;
    }

    //region IMyDecksView
    @Override
    public void setupRecyclerView() {
        rvDecks.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
        rvDecks.setHasFixedSize(true);
        mAdapter = new DeckRecyclerAdapter(getContext());
        rvDecks.setAdapter(mAdapter);
        rvDecks.addOnScrollListener(new HideShowScrollListener() {
            @Override
            public void onHide() {
                FlashMindApplication.getRxBus().publish(RxQueues.LIST_SCROLLED_EVENT_QUEUE, new ListScrolledEvent(true));
            }

            @Override
            public void onShow() {
                FlashMindApplication.getRxBus().publish(RxQueues.LIST_SCROLLED_EVENT_QUEUE, new ListScrolledEvent(false));
            }
        });
        rvDecks.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), rvDecks, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mPresenter.onDeckTouched(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

    }

    @Override
    public void populateRecyclerView(ArrayList<Deck> decks) {
        mAdapter.replaceAll(decks);
    }

    @Override
    public void setPresenter(MyDecksPresenter presenter) {
        this.mPresenter = presenter;
        this.mPresenter.start();
    }

    @Override
    public void navigateToCreateDeckActivity() {

    }

    @Override
    public void showDeckOptionsDialog() {

    }

    @Override
    public void navigateToPlayDeckActivity(Deck mSelectedDeck) {

    }

    @Override
    public void removeDeck(int mSelectedPosition) {

    }

    @Override
    public void navigateToEditDeckActivity(Deck mSelectedDeck) {

    }
    //endregion
}
