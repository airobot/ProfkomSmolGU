package ru.smolgu.profkomsmolgu.singleactivity.discont;

import ru.profkom.profkomsmolgu.R;
import ru.test.image.ImageLoader;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleAkchiiActivity extends Activity {

	// JSON node keys
	private static final String TAG_TITLE = "title";
	private static final String TAG_IMAGE = "image_path";
	private static final String TAG_CONTENT = "content";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_articles);
		// Создание кнопки назад.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent intent = getIntent();

		// getting intent data
		Intent in = getIntent();
		// Get JSON values from previous intent
		String title = in.getStringExtra(TAG_TITLE);
		String content = in.getStringExtra(TAG_CONTENT);
		String imageName = in.getStringExtra(TAG_IMAGE);

		// Displaying all values on the screen
		TextView lbltitle = (TextView) findViewById(R.id.singles_title_articles);
		TextView lblcontent = (TextView) findViewById(R.id.singles_content_articles);
		ImageView lblimage = (ImageView) findViewById(R.id.singles_image_articles);

		lbltitle.setText(title);
		lblcontent.setText(content);

		// Loader image - will be shown before loading image
		int loader = R.drawable.loader;

		// Image url
		// String image_url =
		// "http:///system/articles/images/000/000/004/original/2014-04-22_16.48.597.jpg?1406099395";

		// ImageLoader class instance
		ImageLoader imgLoader = new ImageLoader(getApplicationContext());

		// whenever you want to load an image from url
		// call DisplayImage function
		// url - image url to load
		// loader - loader image, will be displayed before getting image
		// image - ImageView
		imgLoader.DisplayImage(imageName, loader, lblimage);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}