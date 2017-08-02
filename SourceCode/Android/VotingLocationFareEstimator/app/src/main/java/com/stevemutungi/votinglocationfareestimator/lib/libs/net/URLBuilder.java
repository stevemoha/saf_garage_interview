package com.stevemutungi.votinglocationfareestimator.lib.libs.net;

/**
 * URL Builder
 * <p/>
 * Provides apis for quickly building URLS
 */
public class URLBuilder {

    private String url;

    private String query;

    /**
     * URL Builder accepts a url which it builds
     *
     * @param url
     */
    public URLBuilder(String url) {
        this.url = url;
    }

    /**
     * Return the url
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Returns the query
     *
     * @return query
     */
    public String getQuery() {
        return query;
    }

    /**
     * Append new name value pairs to the query
     *
     * @param key
     * @param value
     * @return a cascade object of this class
     */
    public URLBuilder append(String key, String value) {
        if (getQuery() == null) {
            query = key + "=" + value;
        } else {
            query += "&" + key + "=" + value;
        }
        return this;
    }

    /**
     * Returns the built query
     *
     * @return
     */
    public URLBuilder build() {
        return this;
    }

    /**
     * Returns the built query which is appended to the url
     *
     * @return
     */
    public String buildUpon() {
        return getUrl() + "?" + getQuery();
    }

    /**
     * Return the query as a string
     *
     * @return the query
     */
    @Override
    public String toString() {
        return buildUpon();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setQuery(String query) throws IllegalAccessException {
        throw new IllegalAccessException("Cannot set query[" + query + "]");
    }

    public void resetQuery() {
        this.query = "";
    }

    public void resetUrl() {
        this.url = "";
    }

    public void resetAll() {
        resetUrl();
        resetQuery();
    }
}