package br.com.nome.flashmind.logic.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Alessandro Pryds on 19/01/2017.
 */
public class CustomSearchPageMap {
    @SerializedName("cse_thumbnail")
    private ArrayList<CustomSearchThumbnail> thumbnail;

    public CustomSearchThumbnail getThumbnail() {
        return thumbnail.get(0);
    }
}
