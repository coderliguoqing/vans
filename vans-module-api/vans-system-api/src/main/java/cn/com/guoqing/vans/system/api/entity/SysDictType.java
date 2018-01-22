package cn.com.guoqing.vans.system.api.entity;

import cn.com.guoqing.vans.common.api.DataEntity;

public class SysDictType extends DataEntity {
    private String dicttypeId;

    private String dicttypeName;

    private static final long serialVersionUID = 1L;

    public String getDicttypeId() {
        return dicttypeId;
    }

    public void setDicttypeId(String dicttypeId) {
        this.dicttypeId = dicttypeId;
    }

    public String getDicttypeName() {
        return dicttypeName;
    }

    public void setDicttypeName(String dicttypeName) {
        this.dicttypeName = dicttypeName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dicttypeId=").append(dicttypeId);
        sb.append(", dicttypeName=").append(dicttypeName);
        sb.append("]");
        return sb.toString();
    }
}