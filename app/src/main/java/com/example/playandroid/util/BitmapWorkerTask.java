package com.example.playandroid.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.playandroid.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 可以用于下载单个图片.
 */
public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
    /**
     * 磁盘缓存的子文件夹名字.
     */
    private static final String IMAGE_DIR_NAME = "bitmap";

    /**
     * 磁盘缓存的最大空间,默认大小10M.
     */
    private static final long IMAGE_DIR_MAX_SIZE = 10 * 1024 * 1024;

    private static DiskLruCache mDiskLruCache;

    /**
     * 加载的图片的url.
     */
    private String mImageUrl;

    private WeakReference<ImageView> mWeak;

    //创建磁盘缓存实例.
    static {
        File file = DiskLruCacheHelper.getDiskCacheDir(ApplicationContext.getContext(), IMAGE_DIR_NAME);
        int appVersion = DiskLruCacheHelper.getAppVersion(ApplicationContext.getContext());
        try {
            mDiskLruCache = DiskLruCache.open(file, appVersion, 1, IMAGE_DIR_MAX_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BitmapWorkerTask(ImageView imageView) {
        mWeak = new WeakReference<>(imageView);
    }

    /**
     * 后台任务，加载图片.
     */
    @Override
    protected Bitmap doInBackground(String... params) {
        mImageUrl = params[0];
        //先从内存缓存中拿图片
        Bitmap bitmap = ImageMemoryCache.getBitmapFromMemoryCache(mImageUrl);
        
        if (bitmap != null) {
            return bitmap;
        }

        FileDescriptor fileDescriptor = null;
        FileInputStream fileInputStream = null;
        DiskLruCache.Snapshot snapshot;
        try {
            //生成url所对应的磁盘缓存的图片的key
            String key = DiskLruCacheHelper.hashKeyForDisk(mImageUrl);
            snapshot = mDiskLruCache.get(key);
            //通过key查找磁盘缓存
            if (snapshot == null) {
                //磁盘缓存中没有找到对应的图片,那么就去请求网络数据，并且写入缓存
                DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                if (editor != null) {
                    OutputStream out = editor.newOutputStream(0);
                    if (downloadUrlToStream(mImageUrl, out)) {
                        //写入成功
                        editor.commit();
                    } else {
                        //写入失败
                        editor.abort();
                    }
                }
                //缓存被写入后，再从缓存中拿
                snapshot = mDiskLruCache.get(key);
            }
            if (snapshot != null) {
                //磁盘缓存中找到对应的文件
                fileInputStream = (FileInputStream) snapshot.getInputStream(0);
                fileDescriptor = fileInputStream.getFD();
            }

            if (fileDescriptor != null) {
                bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            }
            //将bitmap添加到内存缓存中
            if (bitmap != null) {
                ImageMemoryCache.addBitmapToMemory(mImageUrl, bitmap);
            }
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileDescriptor == null && fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 回调到调用方设置图片.
     */
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        ImageView imageView = mWeak.get();
        if (imageView != null && bitmap != null && mImageUrl.equals(imageView.getTag())) {
            imageView.setImageBitmap(bitmap);
        }
    }

    private boolean downloadUrlToStream(String imageUrl, OutputStream outputStream) {
        HttpURLConnection connection = null;
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            URL url = new URL(imageUrl);
            connection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(connection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    /**
     * 根据传入的options计算应该压缩的图片的比率.
     * @param reqHeight 需要的图片的高度
     * @param reqWidth 需要的图片的宽度
     * @return 比率
     * */
    private int calculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if(height > reqHeight || width > reqWidth){
            int heightRatio = Math.round((float)height/(float)reqHeight);
            int widthRatio  = Math.round((float)width/(float)reqWidth);
            inSampleSize = Math.min(heightRatio, widthRatio);
        }
        return inSampleSize;
    }
//    /**
//     * 加载图片的逻辑.
//     */
//    private Bitmap downloadBitmap(String imageUrl) {
//        Bitmap bitmap = null;
//        HttpURLConnection conn = null;
//        try {
//            URL url = new URL(imageUrl);
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setConnectTimeout(5 * 1000);
//            conn.setReadTimeout(10 * 1000);
//            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (conn != null) {
//                conn.disconnect();
//            }
//        }
//        return bitmap;
//    }

//    @FunctionalInterface
//    public interface ImageCallback {
//        void getDrawable(Drawable drawable);
//    }

}