package br.com.nome.flashmind.logic.model;

/**
 * Created by Alessandro Pryds on 19/01/2017.
 */
public class CustomSearchRequest {

    private String title, searchTerms, safe, cx, imgSize, totalResults, filter;
    private int count, startIndex;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSearchTerms(String searchTerms) {
        this.searchTerms = searchTerms;
    }

    public void setSafe(String safe) {
        this.safe = safe;
    }

    public void setCx(String cx) {
        this.cx = cx;
    }

    public void setImgSize(String imgSize) {
        this.imgSize = imgSize;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public String getTitle() {
        return title;
    }

    public String getSearchTerms() {
        return searchTerms;
    }

    public String getSafe() {
        return safe;
    }

    public String getCx() {
        return cx;
    }

    public String getImgSize() {
        return imgSize;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public String getFilter() {
        return filter;
    }

    public int getCount() {
        return count;
    }

    public int getStartIndex() {
        return startIndex;
    }
}
