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
import br.com.nome.flashmind.logic.model.Deck;
import br.com.nome.flashmind.logic.presenter.HomePresenter;
import br.com.nome.flashmind.logic.rxbus.RxQueues;
import br.com.nome.flashmind.logic.rxbus.events.ListScrolledEvent;
import br.com.nome.flashmind.ui.adapter.DeckRecyclerAdapter;
import br.com.nome.flashmind.utils.HideShowScrollViewListener;
import br.com.nome.flashmind.utils.RecyclerItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements HomePresenter.IHomeView {


    @BindView(R.id.rvLastDecks)
    protected RecyclerView rvLastDecks;
    @BindView(R.id.scrollView)
    protected ScrollView scrollView;

    private HomePresenter mPresenter;
    private DeckRecyclerAdapter mLastDecksAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        new HomePresenter(this);
        return view;
    }


    //region IHomeView
    @Override
    public void setPresenter(HomePresenter homePresenter) {
        this.mPresenter = homePresenter;
        this.mPresenter.start();
    }

    @Override
    public void setupRecentDecksRecyclerView() {
        rvLastDecks.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvLastDecks.setHasFixedSize(true);
        mLastDecksAdapter = new DeckRecyclerAdapter(getContext(), false);
        rvLastDecks.setAdapter(mLastDecksAdapter);
        rvLastDecks.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), rvLastDecks, new RecyclerItemClickListener.OnItemClickListener() {
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
    public void showDeckOptionsDialog() {

    }

    @Override
    public void populateLastDecks(ArrayList<Deck> decks) {
        mLastDecksAdapter.replaceAll(decks);
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
