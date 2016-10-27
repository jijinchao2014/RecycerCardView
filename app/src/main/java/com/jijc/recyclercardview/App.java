package com.jijc.recyclercardview;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * Description:
 * Created by jijc on 2016/8/29.
 * PackageName: com.jijc.recyclercardview
 */
public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            initImageLoader(this);//初始化imageloader
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图像加载处理工具类初始化
     *
     * @param context
     */
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        Log.i("jijinchao","App:initImageLoader");
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .diskCacheSize(100 * 1024 * 1024)
                .diskCacheFileCount(1000)
                .denyCacheImageMultipleSizesInMemory()
                .discCache(new UnlimitedDiskCache(StorageUtils.getOwnCacheDirectory(context, "imageloader/Cache"))) //自定义缓存路径
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.FIFO);

        L.writeLogs(false);//正式发布屏蔽log

        ImageLoaderConfiguration config = builder.build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

}
