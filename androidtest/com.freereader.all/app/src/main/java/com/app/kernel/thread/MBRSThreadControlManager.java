package com.app.kernel.thread;

import android.content.Context;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by kanxue on 2016/1/10.
 */
public class MBRSThreadControlManager
{

    public static MBRSThreadControlManager instance;
    private Context mContext;
    public int corePoolSize=3;
    public int maxThreadPoolSize=Integer.MAX_VALUE;
    public int keepAliveTimeDuration=200;
    public static ThreadPoolExecutor threadPoolExecutor=null;

    public static MBRSThreadControlManager getInstance()
    {
        if(instance==null)
        {
            instance=new MBRSThreadControlManager();
        }
        return instance;
    }

    public void init(Context ctx)
    {
        mContext=ctx;
        threadPoolExecutor=new ThreadPoolExecutor(corePoolSize
                ,maxThreadPoolSize,keepAliveTimeDuration,TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(5));
    }

    public void addThreadToThreadPool(Runnable runnable)
    {
        threadPoolExecutor.execute(runnable);
    }

    public void coseThreadPool(int typeCommand)
    {
        switch (typeCommand)
        {
            case 0:
                /**shut down method is call you never to add newTask for coreThreadPool,till the all tasks is
                 * end threadpool will be closed
                 */
                threadPoolExecutor.shutdown();
                break;
            case 1:
                /**
                 * cose the threadPoolExcutor right now.
                 */
                threadPoolExecutor.shutdownNow();
                break;
        }
    }
}
