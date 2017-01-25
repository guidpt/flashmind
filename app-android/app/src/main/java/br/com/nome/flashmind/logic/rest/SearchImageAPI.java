package br.com.nome.flashmind.logic.rest;

import br.com.nome.flashmind.logic.model.CustomSearchResponse;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Alessandro Pryds on 19/01/2017.
 */

public interface SearchImageAPI {

    @GET("customsearch/v1/")
    Observable<CustomSearchResponse> searchImage(@Query("q") String query, @Query("cx") String customCode, @Query("filter") int filter, @Query("imgSize") String imgSize, @Query("safe") String safe, @Query("key") String key);
}
