package ru.profkom.profkomsmolgu.discont;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.profkom.profkomsmolgu.Place;
import ru.profkom.profkomsmolgu.R;
import ru.profkom.profkomsmolgu.ServiceHandler;
import ru.smolgu.profkomsmolgu.singleactivity.discont.SingleAllDiscontActivity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class DosugActivity extends Fragment {

	private ProgressDialog pDialog;

	// URL to get contacts JSON
	private static String urlArticles = "http://profcom.pro/api/v1/discounts";

	// JSON Node names
	private static final String TAG_DISCONT_CATEGORY = "category";
	private static final String TAG_DISCONT_DESCRIPTION = "description";
	private static final String TAG_DISCONT_DISCOUNT= "discount";
	private static final String TAG_DISCONT_LATITUDE = "latitude";
	private static final String TAG_DISCONT_LONGITUDE = "longitude";
	private static final String TAG_DISCONT_NAME = "name";
	private static final String TAG_DISCONT_PHONE = "phone";
	private static final String TAG_DISCONT_SITE = "site";
	private static final String TAG_DISCONT_IMAGE_PATH = "image_path";
	private static final String TAG_DISCONT_PLACES = "places";
	
	ListView listViewDiscont;
	
	// Hashmap for ListView
	ArrayList<HashMap<String, Object>> discont;
	
	//private SwipeRefreshLayout swipeRefreshLayout;
	

	public DosugActivity() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.main_discont, container, false);
		
		
		discont = new ArrayList<HashMap<String, Object>>();

		listViewDiscont = (ListView)rootView.findViewById(R.id.list_discont);
		
		// Убираем разделители между элементами списка.
		ColorDrawable devidrColor = new ColorDrawable(
		      this.getResources().getColor(android.R.color.transparent));
		listViewDiscont.setDivider(devidrColor);
		listViewDiscont.setDividerHeight(1);

		// Listview on item click listener
		listViewDiscont.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// Starting single contact activity
				Intent in = new Intent(getActivity().getApplicationContext(), SingleAllDiscontActivity.class);
				in.putExtra(TAG_DISCONT_CATEGORY,(String) discont.get(position).get(TAG_DISCONT_CATEGORY));
				in.putExtra(TAG_DISCONT_IMAGE_PATH,(String) discont.get(position).get(TAG_DISCONT_IMAGE_PATH));
				in.putExtra(TAG_DISCONT_NAME,(String) discont.get(position).get(TAG_DISCONT_NAME));
				in.putExtra(TAG_DISCONT_PHONE,(String) discont.get(position).get(TAG_DISCONT_PHONE));
				in.putExtra(TAG_DISCONT_LATITUDE,(String) discont.get(position).get(TAG_DISCONT_LATITUDE));
				in.putExtra(TAG_DISCONT_LONGITUDE,(String) discont.get(position).get(TAG_DISCONT_LONGITUDE));
				in.putExtra(TAG_DISCONT_SITE,(String) discont.get(position).get(TAG_DISCONT_SITE));
				in.putExtra(TAG_DISCONT_DISCOUNT,(String) discont.get(position).get(TAG_DISCONT_DISCOUNT));
				in.putExtra(TAG_DISCONT_DESCRIPTION,(String) discont.get(position).get(TAG_DISCONT_DESCRIPTION));
				in.putExtra(TAG_DISCONT_PLACES,(ArrayList<Place>)discont.get(position).get(TAG_DISCONT_PLACES));
				startActivity(in);
			}

			private Context getApplicationContext() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		// Calling async task to get json
		new GetArticles().execute();
		return rootView;
	}
	
	private Context getApplicationContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

//	@Override
//	public void onRefresh() {
//		new Thread(){
//			public void run() {
//				// TODO Auto-generated method stub
//				SystemClock.sleep(400);
//				
//				getActivity().runOnUiThread(new Runnable() {
//					
//					@Override
//					public void run() {
//						swipeRefreshLayout.setRefreshing(false);
//					}
//				});
//			};	
//		}.start();
//	}
	
	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class GetArticles extends AsyncTask<Void, Void, Void> {

		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Загрузка..");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(urlArticles, ServiceHandler.GET);
			
			Log.d("Response: ", "> " + jsonStr);
			
			if (jsonStr != null) {
				try {
					JSONArray jsonArr = new JSONArray(jsonStr);

					// looping through All Contacts
					for (int i = 0; i < jsonArr.length(); i++) {
						JSONObject obj = jsonArr.getJSONObject(i);
					
						String nameDiscont = obj.getString(TAG_DISCONT_NAME);
						String categoryDiscont = obj.getString(TAG_DISCONT_CATEGORY);
						String imageName = obj.getString(TAG_DISCONT_IMAGE_PATH);
				        String siteUrl = "http://profcom.pro";
				        String descriptionDiscont = obj.getString(TAG_DISCONT_DESCRIPTION);
				        String discontInfo = obj.getString(TAG_DISCONT_DISCOUNT);
				        String latitudeDiscont = obj.getString(TAG_DISCONT_LATITUDE);
				        String longitudeDiscont = obj.getString(TAG_DISCONT_LONGITUDE);
						String siteDiskont = obj.getString(TAG_DISCONT_SITE);
						String phoneDiskont = obj.getString(TAG_DISCONT_PHONE);
						
						if ("Отдых и досуг".equals(categoryDiscont)){
						// tmp hashmap for single contact
						HashMap<String, Object> arrayDiscont = new HashMap<String, Object>();

						// adding each child node to HashMap key => value
						arrayDiscont.put(TAG_DISCONT_NAME, nameDiscont);
						arrayDiscont.put(TAG_DISCONT_CATEGORY, categoryDiscont);
						arrayDiscont.put(TAG_DISCONT_IMAGE_PATH, siteUrl+imageName);
						arrayDiscont.put(TAG_DISCONT_DESCRIPTION, descriptionDiscont);
						arrayDiscont.put(TAG_DISCONT_DISCOUNT, discontInfo);
						arrayDiscont.put(TAG_DISCONT_LATITUDE, latitudeDiscont);
						arrayDiscont.put(TAG_DISCONT_LONGITUDE, longitudeDiscont);
						arrayDiscont.put(TAG_DISCONT_SITE, siteDiskont);
						arrayDiscont.put(TAG_DISCONT_PHONE, phoneDiskont);
						ArrayList<Place> arrayListPlaces = new ArrayList<Place>();
						JSONArray arrayJsonPlace = obj.getJSONArray("places");
						for (int j = 0; j < arrayJsonPlace.length(); j++) {
							Place place = parsePlace(arrayJsonPlace.getJSONObject(j));
							arrayListPlaces.add(place);
						}
						
						arrayDiscont.put(TAG_DISCONT_PLACES, arrayListPlaces);
						
						
						// adding contact to contact list
						discont.add(arrayDiscont);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

			return null;
		}
		
		public Place parsePlace(JSONObject objPlace) throws JSONException {
			Place place = new Place();
			place.latitude = objPlace.getString("latitude");
			place.longitude = objPlace.getString("longitude");
			place.phone = objPlace.getString("phone");
			return place;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			/**
			 * Updating parsed JSON data into ListView
			 * */
			ListAdapter adapter = new SimpleAdapter(
					getActivity(), discont,
					R.layout.list_item_diskont, new String[] {TAG_DISCONT_NAME, TAG_DISCONT_CATEGORY }, 
					new int[] {R.id.category_discont, R.id.name_discont});
			listViewDiscont.setAdapter(adapter);
		}
	}}