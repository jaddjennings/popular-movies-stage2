package com.jennings.jadd.popular_movies_stage2.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;

public  class  Helpers {

    public static Uri getImgURI(Context inContext, Bitmap inImg){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImg.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImg, "Poster",null);
        return Uri.parse(path);
    }
}
