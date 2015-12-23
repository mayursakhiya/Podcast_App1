package e.aakriti.work.imageloader;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import android.content.Context;
import android.os.Environment;
import e.aakriti.work.common.Utility;

public class FileCache {

    private File cacheDir;

    public FileCache(Context context) {
        // Find the dir to save cached images
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            cacheDir = new File(Environment.getExternalStorageDirectory(),
                    ".Whooshka");
        else
            cacheDir = context.getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();
    }

    public File getFile(String url) {
        String filename = URLEncoder.encode(url);
        //String filename = Uri.parse(url).getLastPathSegment();

        File f = new File(cacheDir, filename);
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;

    }

    public File getCacheDir() {
        return cacheDir;

    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        for (File f : files)
            f.delete();
    }

}