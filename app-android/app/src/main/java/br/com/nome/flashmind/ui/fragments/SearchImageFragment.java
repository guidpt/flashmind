package br.com.nome.flashmind.ui.fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.mypopsy.widget.FloatingSearchView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import br.com.nome.flashmind.R;
import br.com.nome.flashmind.logic.model.CustomSearchThumbnail;
import br.com.nome.flashmind.logic.presenter.SearchImagePresenter;
import br.com.nome.flashmind.ui.adapter.ImageRecyclerAdapter;
import br.com.nome.flashmind.utils.NetworkStatsUtil;
import br.com.nome.flashmind.utils.RecyclerItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;

public class SearchImageFragment extends DialogFragment implements SearchImagePresenter.ISearchImageView {


    @BindView(R.id.rvSearch)
    protected RecyclerView rvSearch;
    @BindView(R.id.searchView)
    protected FloatingSearchView searchView;

    private SearchImagePresenter mPresenter;
    private AlertDialog progressDialog;

    public SearchImageFragment() {
        // Required empty public constructor
    }

    public static SearchImageFragment newInstance() {
        SearchImageFragment fragment = new SearchImageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_search, container, false);
        ButterKnife.bind(this, view);
        getDialog().setTitle(getString(R.string.DIALOG_TITLE_IMAGE_SEARCH));

        new SearchImagePresenter(this);
        return view;
    }

    //region IDiscoverView
    @Override
    public void setPresenter(SearchImagePresenter presenter) {
        this.mPresenter = presenter;
        this.mPresenter.start();
    }

    @Override
    public void setupImagesRecyclerView() {
        rvSearch.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        rvSearch.setHasFixedSize(true);
        rvSearch.setAdapter(new ImageRecyclerAdapter(getContext(), new ArrayList<CustomSearchThumbnail>()));
        rvSearch.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), rvSearch, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mPresenter.onImageTouched(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void setupSearchView() {
        RxTextView
                .textChangeEvents((TextView) searchView.findViewById(R.id.fsv_search_text))
                .filter(e -> e.text().length() > 3)
                .distinctUntilChanged()
                .delay(500, TimeUnit.MILLISECONDS)
                .subscribe(new Observer<TextViewTextChangeEvent>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TextViewTextChangeEvent event) {
                        mPresenter.attemptToSearch(event.text().toString());
                    }
                });
    }

    @Override
    public void showProgress() {
        if(progressDialog == null){
            progressDialog = new ProgressDialog.Builder(getContext())
                    .setMessage(R.string.loading_message)
                    .create();
        }
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean isOnline() {
        return NetworkStatsUtil.isConnected(getContext());
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void populateRecyclerView(ArrayList<CustomSearchThumbnail> images) {
        ((ImageRecyclerAdapter) rvSearch.getAdapter()).replaceAll(images);
    }


    //endregion
}
