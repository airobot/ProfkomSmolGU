package ru.profkom.profkomsmolgu;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapAllDiscontActivity extends FragmentActivity implements
LocationListener  {

	GoogleMap googleMap;
	SupportMapFragment mapFragment;
	LatLng latLng = new LatLng(54.771172, 32.053089);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_view);

		// Создание кнопки назад.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent intent = getIntent();

		mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		googleMap = mapFragment.getMap();
		googleMap.getUiSettings().isMyLocationButtonEnabled();
		googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
		if (googleMap == null) {
			finish();
			return;
		}
		init();
	}

	private void init() {
		// Добавляем на карту точки скидок
		googleMap.addMarker(new MarkerOptions().position(new LatLng(54.766821, 32.06283)).title("Circus"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(54.762643, 32.084286)).title("ДоДо Пицца"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(54.762643, 32.084286)).title("ДоДо Пицца"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(54.772278, 32.028839)).title("Чао Италия"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(54.772278, 32.028839)).title("Ле бо бо"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(54.772278, 32.028839)).title("Клуб GOLD"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(54.773195, 32.028978)).title("PUBLICA"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(54.778166, 32.016118)).title("Фитнес клуб OrangeClub"));		
		googleMap.addMarker(new MarkerOptions().position(new LatLng(54.780494, 32.049769)).title("El Smoke"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(54.784263, 32.048368)).title("Кафе Буфет"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(54.770622, 31.98456)).title("Картинг-Клуб Time Attack"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(54.774762, 32.045757)).title("1 LIMO"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(54.779626, 32.048842)).title("Тур бюро Поехали вместе"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(54.777965, 32.023719)).title("Gross Haus"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(54.772437, 32.035126)).title("Gross Haus"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(54.794081, 32.056326)).title("Gross Haus"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(54.762029, 32.10119)).title("Gross Haus"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(54.776142, 32.048997)).title("Принцип компани"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(54.768682, 32.040348)).title("Принцип компани"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(55.050008, 32.728434)).title("Принцип компани"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(55.110866, 33.237837)).title("Принцип компани"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(55.205964, 34.297417)).title("Принцип компани"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(53.947586, 32.853827)).title("Принцип компани"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(54.769786, 32.044062)).title("Эрудит"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(54.760758, 32.108882)).title("Эрудит"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(54.795331, 32.047715)).title("Эрудит"));
		googleMap.addMarker(new MarkerOptions().position(new LatLng(54.783732, 32.04794)).title("Культурно-выставочный центр имени Тенешевых"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.778116, 32.080283)).title("LADAMARKET"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.778641, 32.080877)).title("АВТОСПЕКТР"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.783082, 32.034262)).title("Союз Тур"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.76642, 32.076155)).title("Союз Тур"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.781675, 32.054379)).title("Союз Тур"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.78423, 32.053295)).title("фотостудия Птичка"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.778315, 32.048539)).title("фотостудия Птичка"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.769932, 32.023699)).title("фотостудия Птичка"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.772135, 32.04557)).title("ФОТОПОРТ"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.780303, 32.049354)).title("Много метров"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.766632, 32.029496)).title("Пейнтбольный клуб Легион"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.779127, 32.066372)).title("ГИНЕКОЛОГиЯ"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.773668, 32.085838)).title("Зооветцентр"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.768414, 32.05596)).title("Зооветцентр"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.758915, 32.102243)).title("Зооветцентр"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.770174, 32.103038)).title("Антэль мебель"));	
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.794998, 32.059736)).title("Октопус"));	
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.780416, 32.049767)).title("Силуэт"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.776233, 32.093905)).title("Дуэт Принт"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.76866, 32.039241)).title("Евроторг"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.767991, 32.084657)).title("Евроторг"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.77083, 32.05008)).title("Евроторг"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.768516, 32.004303)).title("Евроторг"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.764123, 32.055314)).title("Евроторг"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.769759, 32.022375)).title("Евроторг"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.778173, 32.016123)).title("Евроторг"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.784399, 32.048325)).title("Букет"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.794898, 32.057078)).title("Много цветов"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.783657, 32.053345)).title("Много цветов"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.770545, 32.021277)).title("Много цветов"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.770001, 32.075025)).title("Машенька"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.779892, 32.023334)).title("Машенька"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.774234, 32.047696)).title("Машенька"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.784297, 32.043721)).title("Арсенал декор"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.776184, 32.060874)).title("Кисейная барышня"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.775643, 32.031999)).title("АЭРОБУМ"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.781975, 32.052528)).title("Тотем"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.775096, 32.045875)).title("ЭлитСАН"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.758907, 32.102404)).title("ЭлитСАН"));
		googleMap.addMarker(new MarkerOptions().position(
				new LatLng(54.764774, 32.074684)).title("\"Уромед\""));
		
		
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
