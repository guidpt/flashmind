package br.com.nome.flashmind.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import br.com.nome.flashmind.logic.model.Card;
import br.com.nome.flashmind.ui.fragments.EditCardFragment;

/**
 * Created by Alessandro Pryds on 20/12/2016.
 */

public class EditCardViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Card> mCards;

    public EditCardViewPagerAdapter(FragmentManager fm, ArrayList<Card> cards) {
        super(fm);
        this.mCards = cards;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            default:
                return EditCardFragment.newInstance(position, mCards.get(position));
        }
    }

    @Override
    public int getCount() {
        return mCards.size();
    }
}
