package br.com.nome.flashmind.logic.interactor;

import java.util.ArrayList;

import br.com.nome.flashmind.application.FlashMindConstants;
import br.com.nome.flashmind.logic.model.CustomSearchItem;
import br.com.nome.flashmind.logic.model.CustomSearchThumbnail;
import br.com.nome.flashmind.logic.rest.SearchImageAPI;
import br.com.nome.flashmind.logic.rest.SearchImageService;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Alessandro Pryds on 19/01/2017.
 */

public class ImageInteractor {
    public void searchQuery(String query, OnImageLoadedListener listener) {
        SearchImageAPI service = SearchImageService.createSearchImageService();
        service.searchImage(query,
                FlashMindConstants.CUSTOM_SEARCH_CODE,
                FlashMindConstants.CUSTOM_SEARCH_FILTER,
                FlashMindConstants.CUSTOM_SEARCH_IMAGE_SIZE_DEFAULT,
                FlashMindConstants.CUSTOM_SEARCH_SAFE,
                FlashMindConstants.GOOGLE_API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> {
                    ArrayList<CustomSearchThumbnail> items = new ArrayList<>();
                    if (response.getItems() != null && response.getItems().size() > 0) {
                        for (CustomSearchItem responseItem : response.getItems()) {
                            items.add(responseItem.getPagemap().getThumbnail());
                        }
                    }
                    return items;
                }).subscribe(new Observer<ArrayList<CustomSearchThumbnail>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onImageLoadError(e.getMessage());
            }

            @Override
            public void onNext(ArrayList<CustomSearchThumbnail> images) {
                listener.onImageLoadSuccess(images);
            }
        });
    }
}
