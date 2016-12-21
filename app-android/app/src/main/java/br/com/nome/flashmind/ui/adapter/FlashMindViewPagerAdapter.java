package br.com.nome.flashmind.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.nome.flashmind.ui.fragments.DiscoverFragment;
import br.com.nome.flashmind.ui.fragments.HomeFragment;
import br.com.nome.flashmind.ui.fragments.MyDecksFragment;

/**
 * Created by Alessandro Pryds on 20/12/2016.
 */

public class FlashMindViewPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;

    public FlashMindViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MyDecksFragment.newInstance();
            case 1:
                return HomeFragment.newInstance();
            case 2:
                return DiscoverFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
