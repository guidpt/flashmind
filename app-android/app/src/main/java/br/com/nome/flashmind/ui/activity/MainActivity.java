package br.com.nome.flashmind.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;

import br.com.nome.flashmind.R;
import br.com.nome.flashmind.logic.presenter.MainPresenter;
import br.com.nome.flashmind.ui.adapter.FlashMindViewPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainPresenter.IMainView {

    private static final int DURATION = 1500;
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator(5);


    @BindView(R.id.bottomNavigation)
    protected SpaceNavigationView bottomNavigation;
    @BindView(R.id.vpMain)
    protected ViewPager vpMain;

    private MainPresenter mPresenter;

    //region Android Lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bottomNavigation.initWithSaveInstanceState(savedInstanceState);

        new MainPresenter(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bottomNavigation.onSaveInstanceState(outState);
    }
    //endregion

    //region IMainView
    @Override
    public void setPresenter(MainPresenter presenter) {
        this.mPresenter = presenter;
        this.mPresenter.start();
    }

    @Override
    public void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void setupViewPager() {
        FlashMindViewPagerAdapter vpAdapter = new FlashMindViewPagerAdapter(getSupportFragmentManager());
        vpMain.setAdapter(vpAdapter);
    }

    @Override
    public void setupBottomNavigation() {
        bottomNavigation.addSpaceItem(new SpaceItem("HOME", R.drawable.near_me));
        bottomNavigation.addSpaceItem(new SpaceItem("SEARCH", R.drawable.near_me));
        bottomNavigation.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                vpMain.setCurrentItem(1);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex){
                    case 0:
                        vpMain.setCurrentItem(0);
                        break;
                    case 1:
                        vpMain.setCurrentItem(2);
                        break;
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });
    }

    @Override
    public void hideBottomNavigation() {
        Transition slide = new Slide(Gravity.BOTTOM);
        slide.addTarget(bottomNavigation);
        slide.setDuration(600);
        slide.setInterpolator(new DecelerateInterpolator());
        TransitionManager.beginDelayedTransition(bottomNavigation, slide);
        bottomNavigation.setVisibility(View.GONE);

    }

    @Override
    public void showBottomNavigation() {
        Transition slide = new Slide(Gravity.BOTTOM);
        slide.addTarget(bottomNavigation);
        slide.setDuration(600);
        slide.setInterpolator(new DecelerateInterpolator());
        TransitionManager.beginDelayedTransition(bottomNavigation, slide);
        bottomNavigation.setVisibility(View.VISIBLE);


    }
    //endregion
}
