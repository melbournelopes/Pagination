package com.demo.coupondemo.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 09-11-2017.
 */

public class ResponseModel {

    @SerializedName("success")
    private Boolean success;
    @SerializedName("authenticated")
    private Boolean authenticated;
    @SerializedName("response")
    private Response response;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }


    public class Response {

        @SerializedName("list")
        private java.util.List<Item> list = null;
        @SerializedName("total_pages")
        private Integer totalPages;
        @SerializedName("total_items")
        private Integer totalItems;

        public java.util.List<Item> getItemList() {
            return list;
        }

        public void setItemList(java.util.List<Item> list) {
            this.list = list;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }

        public Integer getTotalItems() {
            return totalItems;
        }

        public void setTotalItems(Integer totalItems) {
            this.totalItems = totalItems;
        }

        public class Item {

            @SerializedName("name")
            private String name;
            @SerializedName("icon")
            private String icon;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

        }
    }
}
