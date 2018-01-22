package cn.com.guoqing.vans.system.api.entity;

import cn.com.guoqing.vans.common.api.DataEntity;

public class SysDictEntry extends DataEntity {
    private String dicttypeId;

    private String dictId;

    private String dictName;

    private Integer status;

    private Integer sort;

    private static final long serialVersionUID = 1L;

    public String getDicttypeId() {
        return dicttypeId;
    }

    public void setDicttypeId(String dicttypeId) {
        this.dicttypeId = dicttypeId;
    }

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dicttypeId=").append(dicttypeId);
        sb.append(", dictId=").append(dictId);
        sb.append(", dictName=").append(dictName);
        sb.append(", status=").append(status);
        sb.append(", sort=").append(sort);
        sb.append("]");
        return sb.toString();
    }
}