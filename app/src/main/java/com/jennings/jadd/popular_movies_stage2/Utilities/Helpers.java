package com.jennings.jadd.popular_movies_stage2.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.net.URI;

public  class  Helpers {

    public static final int SHOWFAV = 3;
    public static final int TOP_RATED_SORT = 2;
    public static final int MOST_POP_SORT = 1;
    public static final String LIFECYCLE_CALLBACKS_SORT_MODE = "sort_mode";
    public static final String MOVIE_LIST_STATE_KEY = "movie_list_state_key";

    public static Uri getImgURI(Context inContext, Bitmap inImg){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImg.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImg, "Poster",null);
        Uri retVal = null;
        if (path!=null)
            retVal = Uri.parse(path);
        return retVal;
    }
}
