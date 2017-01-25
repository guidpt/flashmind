package br.com.nome.flashmind.logic.presenter;

import java.util.ArrayList;

import br.com.nome.flashmind.application.FlashMindApplication;
import br.com.nome.flashmind.logic.interactor.ImageInteractor;
import br.com.nome.flashmind.logic.interactor.OnImageLoadedListener;
import br.com.nome.flashmind.logic.model.CustomSearchThumbnail;
import br.com.nome.flashmind.logic.rxbus.RxQueues;

/**
 * Created by Alessandro Pryds on 19/01/2017.
 */
//TODO: FIX Custom image search
public class SearchImagePresenter implements OnImageLoadedListener {
    private ISearchImageView mView;
    private ImageInteractor mImageInteractor;
    private ArrayList<CustomSearchThumbnail> imagesLoaded;

    public SearchImagePresenter(ISearchImageView view) {
        this.mImageInteractor = new ImageInteractor();
        this.mView = view;
        this.mView.setPresenter(this);
    }

    public void start() {
        mView.setupSearchView();
        mView.setupImagesRecyclerView();
    }

    public void onImageTouched(int position) {
        FlashMindApplication.getRxBus().publish(RxQueues.IMAGE_SELECTED_QUEUE, imagesLoaded.get(position));
    }

    public void attemptToSearch(String text) {
        if(mView.isOnline()){
            //TODO: Show progress
            mImageInteractor.searchQuery(text,this);
        }
    }

    //region OnImageLoadedListener
    @Override
    public void onImageLoadSuccess(ArrayList<CustomSearchThumbnail> images) {
        this.imagesLoaded = images;
        mView.populateRecyclerView(imagesLoaded);
    }

    @Override
    public void onImageLoadError(String error) {
        mView.showToastMessage(error);
    }
    //endregion

    public interface ISearchImageView{
        void setPresenter(SearchImagePresenter presenter);

        void setupImagesRecyclerView();

        void setupSearchView();

        void populateRecyclerView(ArrayList<CustomSearchThumbnail> images);

        void showProgress();

        void hideProgress();

        boolean isOnline();

        void showToastMessage(String error);
    }
}
