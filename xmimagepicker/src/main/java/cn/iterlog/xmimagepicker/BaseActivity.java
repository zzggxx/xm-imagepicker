package cn.iterlog.xmimagepicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.iterlog.swipbackhelper.SwipeBackHelper;

/**
 * Created by Mr.Jude on 2015/9/7.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(true)
                .setSwipeEdgePercent(0.1f)
                .setSwipeSensitivity(0.5f)
                .setSwipeRelateEnable(true)
                .setSwipeRelateOffset(300);
        //ViewServer.get(this).addWindow(this);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
        //ViewServer.get(this).removeWindow(this);
    }

    public void onResume() {
        super.onResume();
        //ViewServer.get(this).setFocusedWindow(this);
    }





}