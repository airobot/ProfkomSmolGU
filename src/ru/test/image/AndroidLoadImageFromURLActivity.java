package ru.test.image;

import ru.profkom.profkomsmolgu.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class AndroidLoadImageFromURLActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Loader image - will be shown before loading image
        int loader = R.drawable.loader;
        
        // Imageview to show
        ImageView image = (ImageView) findViewById(R.id.image);
        
        // Image url
        String image_url = "http://profcom.pro/system/articles/images/000/000/004/original/2014-04-22_16.48.597.jpg?1406099395";
        
        // ImageLoader class instance
        ImageLoader imgLoader = new ImageLoader(getApplicationContext());
        
        // whenever you want to load an image from url
        // call DisplayImage function
        // url - image url to load
        // loader - loader image, will be displayed before getting image
        // image - ImageView 
        imgLoader.DisplayImage(image_url, loader, image);
    }
}