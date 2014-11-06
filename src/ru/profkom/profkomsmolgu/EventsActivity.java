package ru.profkom.profkomsmolgu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.smolgu.profkomsmolgu.singleactivity.SingleEventsActivity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class EventsActivity extends Fragment{

	private ProgressDialog pDialog;

	// URL to get contacts JSON
	private static String urlEvents = "http://profcom.pro/api/v1/events";

	// JSON Node names
	private static final String TAG_TITLE_EVENTS = "title";
	private static final String TAG_PREVIEW_EVENTS = "preview";
	private static final String TAG_DESCRIPTION_EVENTS = "description";
	private static final String TAG_IMAGE_EVENTS = "image_path";
	private static final String TAG_TIME_EVENTS = "created_at";
	
	ListView listViewEvents;
	
	// Hashmap for ListView
	ArrayList<HashMap<String, String>> events;	
	
	//private SwipeRefreshLayout swipeRefreshLayout;

	public EventsActivity() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.main_list_events, container, false);
		
		
		events = new ArrayList<HashMap<String, String>>();

		listViewEvents = (ListView)rootView.findViewById(R.id.list_events);
		// Убираем разделители между элементами списка.
				ColorDrawable devidrColor = new ColorDrawable(
				      this.getResources().getColor(android.R.color.transparent));
				listViewEvents.setDivider(devidrColor);
				listViewEvents.setDividerHeight(1);

		// Listview on item click listener
		listViewEvents.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// getting values from selected ListItem

				String titleEvents = ((TextView) view.findViewById(R.id.title_events)).getText().toString();
				String descriptionEvents = ((TextView) view.findViewById(R.id.preview_events)).getText().toString();
				

				// Starting single contact activity
				Intent intentEvents = new Intent(getActivity().getApplicationContext(), SingleEventsActivity.class);
				intentEvents.putExtra(TAG_TITLE_EVENTS, events.get(position).get(TAG_TITLE_EVENTS));
				intentEvents.putExtra(TAG_IMAGE_EVENTS, events.get(position).get(TAG_IMAGE_EVENTS));
				intentEvents.putExtra(TAG_DESCRIPTION_EVENTS, events.get(position).get(TAG_DESCRIPTION_EVENTS));
				intentEvents.putExtra(TAG_TIME_EVENTS, events.get(position).get(TAG_TIME_EVENTS));
				startActivity(intentEvents);
			}
		});
		// Calling async task to get json
		new GetArticles().execute();
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
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
			ServiceHandler serviceHandlerEvents = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStrEvents = serviceHandlerEvents.makeServiceCall(urlEvents, ServiceHandler.GET);
			
			Log.d("Response: ", "> " + jsonStrEvents);
			
			if (jsonStrEvents != null) {
				try {
					JSONArray jsonArr = new JSONArray(jsonStrEvents);

					// looping through All Contacts
					for (int i = 0; i < jsonArr.length(); i++) {
						JSONObject JsonObjectEvents = jsonArr.getJSONObject(i);
					
						String descriptionEvents = JsonObjectEvents.getString(TAG_DESCRIPTION_EVENTS);
						String previewEvents = JsonObjectEvents.getString(TAG_PREVIEW_EVENTS);
						String titleEvents = JsonObjectEvents.getString(TAG_TITLE_EVENTS);
						String imageEvents = JsonObjectEvents.getString(TAG_IMAGE_EVENTS);
						String timeEvents = JsonObjectEvents.getString(TAG_TIME_EVENTS);
				        String imageBaseDirectory = "http://profcom.pro";
						
						// tmp hashmap for single contact
						HashMap<String, String> arrayEvents = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						arrayEvents.put(TAG_TITLE_EVENTS, titleEvents);
						arrayEvents.put(TAG_PREVIEW_EVENTS, previewEvents);
						arrayEvents.put(TAG_DESCRIPTION_EVENTS, descriptionEvents);
						arrayEvents.put(TAG_IMAGE_EVENTS, imageBaseDirectory+imageEvents);
						arrayEvents.put(TAG_TIME_EVENTS, timeEvents);
						// adding contact to contact list
						events.add(arrayEvents);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

			return null;
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
			
			Collections.sort(events, new Comparator<HashMap<String, String>>()
					{
					   @Override
					    public int compare(HashMap<String, String> a, HashMap<String, String> b)
					    {
					        return b.get(TAG_TIME_EVENTS).compareTo(a.get(TAG_TIME_EVENTS));
					    }   
					});		
			
			ListAdapter adapter = new SimpleAdapter(
					getActivity(), events,
					R.layout.list_item_events, new String[] {TAG_TITLE_EVENTS, TAG_PREVIEW_EVENTS }, 
					new int[] {R.id.title_events, R.id.preview_events });
			listViewEvents.setAdapter(adapter);
		}

	}
}
