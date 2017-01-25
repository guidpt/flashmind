package br.com.nome.flashmind.logic.model;

import java.util.ArrayList;

/**
 * Created by Alessandro Pryds on 19/01/2017.
 */
public class CustomSearchResponse {
    private SearchQueries queries;
    private ArrayList<CustomSearchItem> items;

    public ArrayList<CustomSearchItem> getItems() {
        return items;
    }

    public SearchQueries getQueries() {
        return queries;
    }
}
