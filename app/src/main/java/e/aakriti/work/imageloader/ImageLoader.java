package e.aakriti.work.imageloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import e.aakriti.work.podcast_app.R;

public class ImageLoader {

    MemoryCache memoryCache = new MemoryCache();
    FileCache fileCache;
    private Map<ImageView, String> imageViews = Collections
            .synchronizedMap(new WeakHashMap<ImageView, String>());
    ExecutorService executorService;

    public ImageLoader(Context context) {
        fileCache = new FileCache(context);
        executorService = Executors.newFixedThreadPool(5);
    }

    int stub_id = R.drawable.default_image;

    public void setImageNotFound(int defaultImage) {
        this.stub_id = defaultImage;
    }

    public void DisplayImage(String url, ImageView imageView) {
        imageViews.put(imageView, url);
        Bitmap bitmap = memoryCache.get(url);
        BitmapDrawable bmd = new BitmapDrawable(bitmap);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            queuePhoto(url, imageView);
            // imageView.setBackgroundResource(stub_id);
        }
        // imageView.setImageResource(R.drawable.yellowframewhite);
    }

    public void DisplayImage(String url, ImageView imageView, int stub_id) {
        DisplayImage(url, imageView);
        this.stub_id = stub_id;
    }


    private void queuePhoto(String url, ImageView imageView) {
        PhotoToLoad p = new PhotoToLoad(url, imageView);
        executorService.submit(new PhotosLoader(p));
    }

    private Bitmap getBitmap(String url) {
        File f = fileCache.getFile(url);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // from SD cache
        Bitmap b = decodeFile(f);
        if (b != null)
            return b;

        // from web
        try {
            Bitmap bitmap = null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is = conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            bitmap = decodeFile(f);
            return bitmap;
        } catch (Throwable ex) {
            ex.printStackTrace();
            if (ex instanceof OutOfMemoryError)
                memoryCache.clear();
            return null;
        }
    }

    // decodes image and scales it to reduce memory consumption
    private Bitmap decodeFileOld(File f) {
        try {
            // decode image size
            /*
             * BitmapFactory.Options o = new BitmapFactory.Options();
			 * o.inJustDecodeBounds = true; FileInputStream stream1 = new
			 * FileInputStream(f); BitmapFactory.decodeStream(stream1, null, o);
			 * stream1.close();
			 * 
			 * // Find the correct scale value. It should be the power of 2.
			 * final int REQUIRED_SIZE = 70; int width_tmp = o.outWidth,
			 * height_tmp = o.outHeight; int scale = 1; while (true) { if
			 * (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
			 * break; width_tmp /= 2; height_tmp /= 2; scale *= 2; }
			 * 
			 * // decode with inSampleSize BitmapFactory.Options o2 = new
			 * BitmapFactory.Options(); // o2.inSampleSize = scale;
			 * 
			 * FileInputStream stream2 = new FileInputStream(f); Bitmap bitmap =
			 * BitmapFactory.decodeStream(stream2, null, o2); stream2.close();
			 * return bitmap;
			 */
            Bitmap b = null;

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            FileInputStream fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();
            int IMAGE_MAX_SIZE = 250;
            int scale = 1;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(
                        2,
                        (int) Math.round(Math.log(IMAGE_MAX_SIZE
                                / (double) Math.max(o.outHeight, o.outWidth))
                                / Math.log(0.5)));
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(f);
            b = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();

            return b;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f) {
        try {
            // decode image size
			/*
			 * BitmapFactory.Options o = new BitmapFactory.Options();
			 * o.inJustDecodeBounds = true; FileInputStream stream1 = new
			 * FileInputStream(f); BitmapFactory.decodeStream(stream1, null, o);
			 * stream1.close();
			 * 
			 * // Find the correct scale value. It should be the power of 2.
			 * final int REQUIRED_SIZE = 70; int width_tmp = o.outWidth,
			 * height_tmp = o.outHeight; int scale = 1; while (true) { if
			 * (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
			 * break; width_tmp /= 2; height_tmp /= 2; scale *= 2; }
			 * 
			 * // decode with inSampleSize BitmapFactory.Options o2 = new
			 * BitmapFactory.Options(); // o2.inSampleSize = scale;
			 * 
			 * FileInputStream stream2 = new FileInputStream(f); Bitmap bitmap =
			 * BitmapFactory.decodeStream(stream2, null, o2); stream2.close();
			 * return bitmap;
			 */
            Bitmap b = null;

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            FileInputStream fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();
            int IMAGE_MAX_SIZE = 300;
            int scale = 1;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(
                        2,
                        (int) Math.round(Math.log(IMAGE_MAX_SIZE
                                / (double) Math.max(o.outHeight, o.outWidth))
                                / Math.log(0.5)));
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(f);
            b = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();

            return b;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Task for the queue
    private class PhotoToLoad {
        public String url;
        public ImageView imageView;

        public PhotoToLoad(String u, ImageView i) {
            url = u;
            imageView = i;
        }
    }

    class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;

        PhotosLoader(PhotoToLoad photoToLoad) {
            this.photoToLoad = photoToLoad;
        }

        @Override
        public void run() {
            try {
                if (imageViewReused(photoToLoad))
                    return;
                Bitmap bmp = getBitmap(photoToLoad.url);
                memoryCache.put(photoToLoad.url, bmp);
                if (imageViewReused(photoToLoad))
                    return;
                BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
                Activity a = (Activity) photoToLoad.imageView.getContext();
                a.runOnUiThread(bd);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    boolean imageViewReused(PhotoToLoad photoToLoad) {
        String tag = imageViews.get(photoToLoad.imageView);
        if (tag == null || !tag.equals(photoToLoad.url))
            return true;
        return false;
    }

    // Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable {
        Bitmap bitmap;
        BitmapDrawable bmd;
        PhotoToLoad photoToLoad;

        public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
            bitmap = b;
            bmd = new BitmapDrawable(bitmap);
            photoToLoad = p;
        }

        public void run() {
            try {
                if (imageViewReused(photoToLoad))
                    return;
                if (bitmap != null) {
                    photoToLoad.imageView.setImageDrawable(bmd);
                } else {
                    photoToLoad.imageView.setImageResource(stub_id);
                }
                // photoToLoad.imageView.setBackgroundResource(R.drawable.zdetail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }

}
