package ru.profkom.profkomsmolgu;

import java.util.ArrayList;

import ru.profkom.profkomsmolgu.R;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapSingleActivity extends FragmentActivity implements
		LocationListener {
	private static final String TAG_DISCONT_PLACES = "places";
	GoogleMap googleMap;
	SupportMapFragment mapFragment;
	LatLng centerSity = new LatLng(54.771172, 32.053089);
	ArrayList<Place> places;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_view);

		// Создание кнопки назад.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent intent = getIntent();
		places = (ArrayList<Place>) intent.getSerializableExtra(TAG_DISCONT_PLACES);
		
		
		mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		googleMap = mapFragment.getMap();
		googleMap.getUiSettings().isMyLocationButtonEnabled();
		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(centerSity, 12));
		if (googleMap == null) {
			finish();
			return;
		}
		init();
	}

	private void init() {
		
		 for (Place place : places){
			String lat = place.latitude;
			String lng = place.longitude;
			double doublelat = Double.parseDouble(lat);
			double doublelng = Double.parseDouble(lng);
			LatLng latLng = new LatLng(doublelat, doublelng);
			
				// Добавляем на карту точки скидок
				googleMap.addMarker(new MarkerOptions().position(latLng).title("Скидка"));
				 
		 }
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

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