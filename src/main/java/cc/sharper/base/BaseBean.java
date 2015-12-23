package cc.sharper.base;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liumin3 on 2015/12/23.
 */
public class BaseBean implements Serializable
{

    private Date createdTime;

    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }
}
