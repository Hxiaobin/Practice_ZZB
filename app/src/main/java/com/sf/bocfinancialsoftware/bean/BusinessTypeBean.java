package com.sf.bocfinancialsoftware.bean;

import java.util.List;

/**
 * 业务类别Bean类
 * Created by sn on 2017/6/14.
 */

public class BusinessTypeBean {

    private String typeId; //业务类别id
    private String typeName; //业务lei别名称

    public BusinessTypeBean(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}
