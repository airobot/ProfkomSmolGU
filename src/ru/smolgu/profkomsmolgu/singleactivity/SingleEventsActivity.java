package ru.smolgu.profkomsmolgu.singleactivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import ru.profkom.profkomsmolgu.R;
import ru.test.image.ImageLoader;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleEventsActivity extends Activity {
	// JSON node keys
	private static final String TAG_TITLE_EVENTS = "title";
	private static final String TAG_IMAGE_EVENTS = "image_path";
	private static final String TAG_DESCRIPTION_EVENTS = "description";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_events);
		// Создание кнопки назад.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent intent = getIntent();

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		// getting intent data
		Intent in = getIntent();
		// Get JSON values from previous intent
		String singleTitleEvents = in.getStringExtra(TAG_TITLE_EVENTS);
		String singleDescriptionEvents = in
				.getStringExtra(TAG_DESCRIPTION_EVENTS);
		String singleImageEvents = in.getStringExtra(TAG_IMAGE_EVENTS);
		String imageBaseDirectory = "http://profcom.pro";

		// Displaying all values on the screen
		TextView lblSingleTitleEvents = (TextView) findViewById(R.id.singles_title_events);
		TextView lblSingleDescriptionEvents = (TextView) findViewById(R.id.singles_description_events);
		ImageView lblimage = (ImageView) findViewById(R.id.singles_image_events);

		lblSingleTitleEvents.setText(singleTitleEvents);
		lblSingleDescriptionEvents.setText(singleDescriptionEvents);

		int loader = R.drawable.loader;
		ImageLoader imgLoader = new ImageLoader(getApplicationContext());
		imgLoader.DisplayImage(singleImageEvents, loader, lblimage);
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
