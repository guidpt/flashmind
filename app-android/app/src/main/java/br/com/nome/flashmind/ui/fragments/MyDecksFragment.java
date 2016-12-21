package br.com.nome.flashmind.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.nome.flashmind.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyDecksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyDecksFragment extends Fragment {

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
        return inflater.inflate(R.layout.fragment_my_decks, container, false);
    }

}
