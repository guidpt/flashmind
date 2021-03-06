package br.com.nome.flashmind.ui.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import br.com.nome.flashmind.R;
import br.com.nome.flashmind.application.FlashMindApplication;
import br.com.nome.flashmind.application.FlashMindConstants;
import br.com.nome.flashmind.logic.model.Deck;
import br.com.nome.flashmind.logic.presenter.MyDecksPresenter;
import br.com.nome.flashmind.logic.rxbus.RxQueues;
import br.com.nome.flashmind.logic.rxbus.events.ListScrolledEvent;
import br.com.nome.flashmind.ui.activity.CreateDeckActivity;
import br.com.nome.flashmind.ui.adapter.DeckRecyclerAdapter;
import br.com.nome.flashmind.utils.GridSpacingItemDecoration;
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
        mAdapter = new DeckRecyclerAdapter(getContext(), true);
        rvDecks.setAdapter(mAdapter);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_layout_margin);
        rvDecks.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true, 0));

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
    public void navigateToCreateDeckActivity(String deckName) {
        Intent intent = new Intent(getActivity(), CreateDeckActivity.class);
        intent.putExtra(FlashMindConstants.INTENT_TAG_DECK_NAME, deckName);
        startActivity(intent);
    }

    @Override
    public void showDeckOptionsDialog() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_deck_option, null);

        //TODO: Set layout components values

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialoglayout)
                .setPositiveButton(getString(R.string.BUTTON_PLAY_TEXT), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.onBtnPlaySelectedDeckTouched();
                    }
                })
                .setNegativeButton(getString(R.string.BUTTON_TRAIN_TEXT), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.onBtnTrainSelectedDeckTouched();
                    }
                })
                .setNeutralButton(getString(R.string.BUTTON_EDIT_TEXT), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.onBtnEditSelectedDeckTouched();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void showCreateNewDeckDialog() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_new_deck, null);

        final EditText mDeckName = ButterKnife.findById(dialoglayout, R.id.etDeckName);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialoglayout)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.onBtnConfirmCreationTouched(mDeckName.getText().toString());
                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
        }).create().show();
    }

    @Override
    public void removeDeck(int mSelectedPosition) {

    }


    @Override
    public void navigateToPlayDeckActivity(Deck mSelectedDeck) {

    }

    @Override
    public void navigateToEditDeckActivity(Deck mSelectedDeck) {

    }

    @Override
    public void navigateToTrainDeckActivity(Deck selectedDeck) {

    }
    //endregion
}
