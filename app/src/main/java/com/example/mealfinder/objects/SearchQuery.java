package com.example.mealfinder.objects;

import java.util.Date;

public class SearchQuery {
    private String query;
    private Date searchDate;
    private String searchId;

    public SearchQuery(String query, String userId) {
        this.query = query;
        this.searchDate = new Date();
        this.searchId = userId + this.searchDate.getTime() + this.query.hashCode();
    }
    public SearchQuery() {
        this.query = "";
        this.searchDate = new Date();
        this.searchId = "" + this.searchDate.getTime();
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Date getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }
}
