package br.com.nome.flashmind.ui.activity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.rd.PageIndicatorView;
import com.thebluealliance.spectrum.SpectrumDialog;
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
    @BindView(R.id.btnColor)
    protected ImageButton btnColor;
    @BindView(R.id.ivColorSelected)
    protected ImageView ivColorSelected;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.appBarLayout)
    protected AppBarLayout appBarLayout;
    private CreateDeckPresenter mPresenter;
    private boolean isImageSelect;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (isImageSelect) {
            inflater.inflate(R.menu.create_deck_image_menu, menu);
            return true;
        }
        inflater.inflate(R.menu.create_deck_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.aspect:
                mPresenter.onMenuAspectTouched();
                break;
            case R.id.image:
                mPresenter.onMenuImageTouched();
                break;
        }
        return true;
    }


    //region Button Actions
    @OnClick({R.id.btnColor, R.id.ivColorSelected, R.id.btnFlip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnColor:
            case R.id.ivColorSelected:
                mPresenter.onBtnColorTouched();
                break;
            case R.id.btnFlip:
                mPresenter.onBtnFlipTouched();
                break;
        }
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
        EditCardViewPagerAdapter vpAdapter = new EditCardViewPagerAdapter(getSupportFragmentManager(), mCards);
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

    @Override
    public void navigateToLoadImage() {

    }

    @Override
    public void showColorPickerDialog() {
        new SpectrumDialog.Builder(this)
                .setColors(R.array.card_colors)
                .setDismissOnColorSelected(true)
                .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                        if (positiveResult) {
                            mPresenter.onColorSelected(color);
                        }
                    }
                }).build().show(getSupportFragmentManager(), "colorDialog");
    }

    @Override
    public void tintDeck(int color) {
        ivColorSelected.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        btnColor.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        toolbar.setBackgroundColor(color);
        appBarLayout.setBackgroundColor(color);
    }
    //endregion
}
