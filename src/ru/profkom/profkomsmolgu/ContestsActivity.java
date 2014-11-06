package ru.profkom.profkomsmolgu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.smolgu.profkomsmolgu.singleactivity.SingleContestsActivity;
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

public class ContestsActivity  extends Fragment{

	private ProgressDialog pDialog;

	// URL to get contacts JSON
	private static String urlContests = "http://profcom.pro/api/v1/contests";

	// JSON Node names
	private static final String TAG_DESCRIPTION_CONTESTS = "description";
	private static final String TAG_PREVIEW_CONTESTS = "preview";
	private static final String TAG_TITLE_CONTESTS = "title";
	private static final String TAG_IMAGE_CONTESTS = "image_path";
	private static final String TAG_TIME = "created_at";
	
	ListView listViewContests;
	
	// Hashmap for ListView
	ArrayList<HashMap<String, String>> contests;
	
	//private SwipeRefreshLayout swipeRefreshLayout;
	

	public ContestsActivity() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.main_list_contests, container, false);
		
		
		contests = new ArrayList<HashMap<String, String>>();

		listViewContests = (ListView)rootView.findViewById(R.id.list_contests);
		
		// Убираем разделители между элементами списка.
				ColorDrawable devidrColor = new ColorDrawable(
				      this.getResources().getColor(android.R.color.transparent));
				listViewContests.setDivider(devidrColor);
				listViewContests.setDividerHeight(1);
		
		// Listview on item click listener
		listViewContests.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// getting values from selected ListItem
				// Starting single contact activity
				Intent in = new Intent(getActivity().getApplicationContext(), SingleContestsActivity.class);
				in.putExtra(TAG_TITLE_CONTESTS, contests.get(position).get(TAG_TITLE_CONTESTS));
				in.putExtra(TAG_IMAGE_CONTESTS, contests.get(position).get(TAG_IMAGE_CONTESTS));
				in.putExtra(TAG_DESCRIPTION_CONTESTS, contests.get(position).get(TAG_DESCRIPTION_CONTESTS));
				in.putExtra(TAG_TIME, contests.get(position).get(TAG_TIME));
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
			String jsonStr = sh.makeServiceCall(urlContests, ServiceHandler.GET);
			
			Log.d("Response: ", "> " + jsonStr);
			
			if (jsonStr != null) {
				try {
					JSONArray jsonArr = new JSONArray(jsonStr);

					// looping through All Contacts
					for (int i = 0; i < jsonArr.length(); i++) {
						JSONObject obj = jsonArr.getJSONObject(i);
					
						String contentContests = obj.getString(TAG_DESCRIPTION_CONTESTS);
						String previewContests = obj.getString(TAG_PREVIEW_CONTESTS);
						String titleContests = obj.getString(TAG_TITLE_CONTESTS);
						String imageNameContests = obj.getString(TAG_IMAGE_CONTESTS);
				        String siteUrlContests = "http://profcom.pro";
						String timeContests = obj.getString(TAG_TIME);
						
						// tmp hashmap for single contact
						HashMap<String, String> arrayContests = new HashMap<String, String>();
						
						// adding each child node to HashMap key => value
						arrayContests.put(TAG_TITLE_CONTESTS, titleContests);
						arrayContests.put(TAG_PREVIEW_CONTESTS, previewContests);
						arrayContests.put(TAG_DESCRIPTION_CONTESTS, contentContests);
						arrayContests.put(TAG_IMAGE_CONTESTS, siteUrlContests + imageNameContests);
						arrayContests.put(TAG_TIME, timeContests);
						
						// adding contact to contact list
						contests.add(arrayContests);
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
			
			Collections.sort(contests, new Comparator<HashMap<String, String>>()
					{
					   @Override
					    public int compare(HashMap<String, String> a, HashMap<String, String> b)
					    {
					        return b.get(TAG_TIME).compareTo(a.get(TAG_TIME));
					    }   
					});		
		
			//ImageDownload();
			ListAdapter adapter = new SimpleAdapter(
					getActivity(), contests,
					R.layout.list_item_contests, new String[] {TAG_TITLE_CONTESTS, TAG_PREVIEW_CONTESTS }, 
					new int[] {R.id.title_contests, R.id.preview_contests});
			listViewContests.setAdapter(adapter);
		}
	}
}