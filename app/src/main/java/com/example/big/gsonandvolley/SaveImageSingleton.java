package com.example.big.gsonandvolley;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Big on 11/3/2015.
 */
public class SaveImageSingleton {
    private static SaveImageSingleton mInstance;
    private File filesDir;
    private static Context mCtx;

    private SaveImageSingleton(Context context) {
        mCtx=context;
        filesDir = mCtx.getFilesDir();
    }
    public static synchronized SaveImageSingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SaveImageSingleton(context);
        }
        return mInstance;
    }

    // here the image based on the url will be returned, if found
    public Bitmap getImage (String ImageURL) {
        String storableFileName = ImageURL.substring(ImageURL.lastIndexOf("/") + 1);
        Bitmap bitmap;
        try {
            File f= new File(filesDir + storableFileName);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    // here the image will be saved for caching after being successfully downloaded
    public void saveImage (String ImageURL , Bitmap bitmap)  {
        // get the name of the image. for exmaple if the image url is http://www.google.com/image.jpg the filename
        // will be image.jpg
        String storableFileName = ImageURL.substring(ImageURL.lastIndexOf("/") + 1);
        // the filename extension, for .jpg will be jpg
        String fileExtension = storableFileName.split("\\.")[1];

        File file = new File(filesDir + storableFileName);
        System.out.println("filesDir + storableFileName!!!!!!");
        System.out.println(filesDir + storableFileName);

        try {

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(getCompressFormat(fileExtension), 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // Gets the appropriate Bitmap.CompressFormat according to the extension of the image
    private static Bitmap.CompressFormat getCompressFormat(String fileExtension) {
        Bitmap.CompressFormat compressFormat = null;

        //set fileextension to lowercase
        switch (fileExtension.toLowerCase()){
            case "jpg":
                compressFormat=Bitmap.CompressFormat.JPEG;
                break;
            case "jpeg":
                compressFormat=Bitmap.CompressFormat.JPEG;
                break;
            case "png":
                compressFormat=Bitmap.CompressFormat.PNG;
                break;
            case "webp":
                compressFormat=Bitmap.CompressFormat.WEBP;
                break;
            default: return compressFormat;
        }

        return compressFormat;
    }
}






















