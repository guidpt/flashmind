package br.com.nome.flashmind.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import java.util.ArrayList;

import br.com.nome.flashmind.R;
import br.com.nome.flashmind.application.FlashMindApplication;
import br.com.nome.flashmind.logic.model.SharedDeck;
import br.com.nome.flashmind.logic.presenter.DiscoverPresenter;
import br.com.nome.flashmind.logic.rxbus.RxQueues;
import br.com.nome.flashmind.logic.rxbus.events.ListScrolledEvent;
import br.com.nome.flashmind.ui.adapter.LastDeckRecyclerAdapter;
import br.com.nome.flashmind.ui.adapter.SuggestionDeckRecyclerAdapter;
import br.com.nome.flashmind.utils.HideShowScrollViewListener;
import br.com.nome.flashmind.utils.RecyclerItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoverFragment extends Fragment implements DiscoverPresenter.IDiscoverView {


    @BindView(R.id.rvSuggestion)
    protected RecyclerView rvSuggestion;
    @BindView(R.id.rvLastDecks)
    protected RecyclerView rvLastDecks;
    @BindView(R.id.scrollView)
    protected ScrollView scrollView;

    private SuggestionDeckRecyclerAdapter mSuggestionAdapter;
    private LastDeckRecyclerAdapter mLastDecksAdapter;
    private DiscoverPresenter mPresenter;

    public DiscoverFragment() {
        // Required empty public constructor
    }

    public static DiscoverFragment newInstance() {
        DiscoverFragment fragment = new DiscoverFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        ButterKnife.bind(this, view);

        new DiscoverPresenter(this);
        return view;
    }

    //region IDiscoverView
    @Override
    public void setPresenter(DiscoverPresenter presenter) {
        this.mPresenter = presenter;
        this.mPresenter.start();
    }

    @Override
    public void setupSuggestionDecks(ArrayList<SharedDeck> decks) {
        rvSuggestion.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvSuggestion.setHasFixedSize(true);
        mSuggestionAdapter = new SuggestionDeckRecyclerAdapter(getContext(),decks);
        rvSuggestion.setAdapter(mSuggestionAdapter);
        rvSuggestion.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), rvSuggestion, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mPresenter.onSuggestionDeckTouched(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void setupLastUpdatedDecks(ArrayList<SharedDeck> decks) {
        rvLastDecks.setNestedScrollingEnabled(false);
        rvLastDecks.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvLastDecks.setHasFixedSize(true);
        mLastDecksAdapter = new LastDeckRecyclerAdapter(getContext(),decks);
        rvLastDecks.setAdapter(mLastDecksAdapter);
        rvLastDecks.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), rvLastDecks, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mPresenter.onLastDeckTouched(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void setupScrollViewListener() {
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new HideShowScrollViewListener(scrollView) {
            @Override
            public void onHide() {
                FlashMindApplication.getRxBus().publish(RxQueues.LIST_SCROLLED_EVENT_QUEUE, new ListScrolledEvent(true));
            }

            @Override
            public void onShow() {
                FlashMindApplication.getRxBus().publish(RxQueues.LIST_SCROLLED_EVENT_QUEUE, new ListScrolledEvent(false));
            }
        });
    }
    //endregion
}
