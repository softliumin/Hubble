package cc.sharper.base;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liumin3 on 2015/12/23.
 */
public class BaseBean implements Serializable
{

    private Date createdTime;


    @JSONField(deserialize = false, serialize = false)
    private String sortCondition;

    @JSONField(deserialize = false, serialize = false)
    private Long pageNum;

    @JSONField(deserialize = false, serialize = false)
    private int pageSize = 10;

    @JSONField(deserialize = false, serialize = false)
    private long offset;


    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }

    public void setPageNum(Long pageNum) {
        if (pageNum < 1)
            pageNum = 1L;
        this.pageNum = pageNum;
        offset = (getPageNum() - 1) * getPageSize();
    }

    public void setPageSize(int pageSize)
    {
        if (pageSize < 1)
            pageSize = 1;
        this.pageSize = pageSize;
        offset = (getPageNum() - 1) * getPageSize();
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public Long getPageNum()
    {
        if (pageNum == null)
            pageNum = 1L;
        return pageNum;
    }

    public long getOffset() {
        return offset;
    }

    public String getSortCondition() {
        return sortCondition;
    }

    public void setSortCondition(String sortCondition) {
        this.sortCondition = sortCondition;
    }
}
