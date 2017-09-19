package com.ahuo.myapp2.core.alive;

import android.content.Context;
import android.content.Intent;

/**
 * Created on 2016-10-13.
 *
 * @author GuoNing.
 */
public class AliveActivityManager {

    private static AliveActivityManager sInstance = null;

    private FinishAliveListener mListener = null;

    private AliveActivityManager(){
    }

    public static AliveActivityManager getsInstance(){
        if(sInstance == null){
            synchronized (AliveActivityManager.class){
                if(sInstance == null){
                    sInstance = new AliveActivityManager();
                }
            }
        }
        return sInstance;
    }

    public void setFinishAliveListener(FinishAliveListener listener){
        this.mListener = listener;
    }

    public void startAliveActivity(Context context){
        Intent intent = new Intent(context, KeepAliveActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void finishAliveActivity(){
        if(mListener != null){
            mListener.finishAliveActivity();
        }
    }
}
