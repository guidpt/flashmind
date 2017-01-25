package br.com.nome.flashmind.logic.interactor;

import java.util.ArrayList;

import br.com.nome.flashmind.logic.model.CustomSearchThumbnail;

/**
 * Created by Alessandro Pryds on 19/01/2017.
 */
public interface OnImageLoadedListener {
    void onImageLoadSuccess(ArrayList<CustomSearchThumbnail> images);
    void onImageLoadError(String error);
}
