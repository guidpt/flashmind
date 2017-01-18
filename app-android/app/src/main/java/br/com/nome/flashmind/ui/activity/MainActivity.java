package br.com.nome.flashmind.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

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

    @BindView(R.id.bottomNavigation)
    protected SpaceNavigationView bottomNavigation;
    @BindView(R.id.vpMain)
    protected ViewPager vpMain;

    private MainPresenter mPresenter;
    private int mSelectedIndex;
    private int[] titles = {R.string.PAGE_TITLE_MY_DECKS, R.string.PAGE_TITLE_HOME, R.string.PAGE_TITLE_DISCOVER};

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        switch (mSelectedIndex){
            case 0:
                inflater.inflate(R.menu.deck_menu, menu);
                break;
            case 1:
                inflater.inflate(R.menu.main_menu, menu);
                break;
            case 2:
                inflater.inflate(R.menu.search_menu, menu);
                SearchManager searchManager =
                        (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                SearchView searchView =
                        (SearchView) menu.findItem(R.id.menu_search).getActionView();
                searchView.setSearchableInfo(
                        searchManager.getSearchableInfo(getComponentName()));

                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.list:
                mPresenter.onMenuListTouched();
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
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
        vpMain.setOffscreenPageLimit(3);
        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPresenter.onPageSwipe(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void setupBottomNavigation() {
        bottomNavigation.addSpaceItem(new SpaceItem(getString(R.string.LABEL_MY_DECKS), R.drawable.near_me));
        bottomNavigation.addSpaceItem(new SpaceItem(getString(R.string.LABEL_DISCOVER), R.drawable.near_me));
        bottomNavigation.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                mPresenter.onCenterButtonTouched();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                mPresenter.onPageSelected(itemIndex);
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                mPresenter.onPageSelected(itemIndex);
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

    @Override
    public void invalidateOptionsMenu(int position) {
        mSelectedIndex = position;
        invalidateOptionsMenu();
    }

    @Override
    public void moveToPage(int page) {
        vpMain.setCurrentItem(page);
    }

    @Override
    public void setTitleForPage(int position) {
        getSupportActionBar().setTitle(getString(titles[position]));
    }
    //endregion
}
