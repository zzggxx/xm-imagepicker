package cn.iterlog.xmimagepicker;

import android.app.Application;
import android.os.Handler;

import com.squareup.picasso.Picasso;

import cn.iterlog.xmimagepicker.Utils.VideoRequestHandler;

public class Gallery {
    public static int TYPE_MUSIC = 1;
    public static int TYPE_VIDEO = 2;
    public static int TYPE_DOC = 3;
    public static int TYPE_PICTURE = 4;

    public static int THUMB_SIZE = 256;

    public volatile static Application applicationContext;
    public volatile static Handler applicationHandler;
    public volatile static Picasso picasso;
    public static void init(Application application) {
        VideoRequestHandler requestHandler = new VideoRequestHandler();
        if (applicationContext == null) {
            applicationContext = application;
            applicationHandler = new Handler(application.getMainLooper());
            picasso = new Picasso.Builder(applicationContext)
                    .addRequestHandler(requestHandler)
                    .build();
        }
    }
}