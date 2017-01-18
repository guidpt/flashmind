package br.com.nome.flashmind.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;

import com.rd.PageIndicatorView;
import com.transitionseverywhere.Rotate;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;

import java.util.ArrayList;

import br.com.nome.flashmind.R;
import br.com.nome.flashmind.application.FlashMindApplication;
import br.com.nome.flashmind.application.FlashMindConstants;
import br.com.nome.flashmind.logic.model.Card;
import br.com.nome.flashmind.logic.presenter.CreateDeckPresenter;
import br.com.nome.flashmind.logic.rxbus.RxQueues;
import br.com.nome.flashmind.logic.rxbus.events.FlipCardEvent;
import br.com.nome.flashmind.ui.adapter.EditCardViewPagerAdapter;
import br.com.nome.flashmind.utils.DisableWhenAnimatingListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateDeckActivity extends BaseActivity implements CreateDeckPresenter.ICreateDeckView {

    @BindView(R.id.vpCards)
    protected ViewPager vpCards;
    @BindView(R.id.btnFlip)
    protected ImageButton btnFlip;
    @BindView(R.id.root)
    protected CoordinatorLayout root;
    private CreateDeckPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_deck);
        ButterKnife.bind(this);

        new CreateDeckPresenter(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //region Button ACtions
    @OnClick(R.id.btnFlip)
    public void onBtnFlipTouched() {
        mPresenter.onBtnFlipTouched();
    }
    //endregion

    //region ICreateDeckView
    @Override
    public void setPresenter(CreateDeckPresenter presenter) {
        this.mPresenter = presenter;
        this.mPresenter.start();
    }

    @Override
    public void initToolbar() {
        String deckName = (String) getIntent().getExtras().get(FlashMindConstants.INTENT_TAG_DECK_NAME);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(deckName);
    }

    @Override
    public void setupDeckViewPager(ArrayList<Card> mCards) {
        EditCardViewPagerAdapter vpAdapter = new EditCardViewPagerAdapter(getSupportFragmentManager(),mCards);
        vpCards.setAdapter(vpAdapter);
        vpCards.setOffscreenPageLimit(3);
        vpCards.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPresenter.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        PageIndicatorView pageIndicatorView = (PageIndicatorView) findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setViewPager(vpCards);
    }

    @Override
    public void flipView(int position, int rotation) {
        FlashMindApplication.getRxBus().publish(RxQueues.FLIP_CARD_EVENT, new FlipCardEvent(position));
        Transition rotate = new Rotate();
        rotate.addListener(new DisableWhenAnimatingListener(btnFlip));
        TransitionManager.beginDelayedTransition(root, rotate);
        btnFlip.setRotation(rotation);
    }
    //endregion
}
