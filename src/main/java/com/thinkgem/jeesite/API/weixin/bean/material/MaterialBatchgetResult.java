package com.thinkgem.jeesite.API.weixin.bean.material;


import com.thinkgem.jeesite.API.weixin.bean.BaseResult;

import java.util.List;

public class MaterialBatchgetResult extends BaseResult {

    private String total_count;

    private String item_count;

    private List<MaterialBatchgetResultItem> item;

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public String getItem_count() {
        return item_count;
    }

    public void setItem_count(String item_count) {
        this.item_count = item_count;
    }

    public List<MaterialBatchgetResultItem> getItem() {
        return item;
    }

    public void setItem(List<MaterialBatchgetResultItem> item) {
        this.item = item;
    }


}
