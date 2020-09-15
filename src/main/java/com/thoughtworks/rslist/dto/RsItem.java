package com.thoughtworks.rslist.dto;

public class RsItem {
    private String name;
    private String keyword;

    public RsItem(String name, String keyword) {
        this.name = name;
        this.keyword = keyword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return name + ',' + keyword;
    }
}
