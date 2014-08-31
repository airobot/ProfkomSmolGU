package ru.profkom.profkomsmolgu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.smolgu.profkomsmolgu.singleactivity.SingleEventsActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

	// URL to get contacts JSON
	private static String urlEvents = "http://profcom.pro/api/v1/events";

	// JSON Node names
	private static final String TAG_PUSH_ID = "id";
	private static final String TAG_PUSH_TITLE = "title";

	// Hashmap for ListView
	ArrayList<HashMap<String, String>> contests;

	NotificationManager nm;
	
	ArrayList<Integer> localIds;

	@Override
	public void onCreate() {
		super.onCreate();
		
		final Handler handler = new Handler();
		Timer myTimer = new Timer(); // Создаем таймер
		myTimer.schedule(new TimerTask() { // Определяем задачу
					@Override
					public void run() {
						if(localIds == null){
							localIds = getIdsEvents();
							return;
						}
						ArrayList<Integer> actualIds = getIdsEvents();
						if(actualIds == null){
							return;
						}
						boolean showIds = false;
						for (int i = 0; i < actualIds.size(); i++) {
							int foAcIds = actualIds.get(i);
							boolean yesIds = false;
							for (int j = 0; j < localIds.size(); j++) {
								int foLoIds = localIds.get(j);
								if(foAcIds == foLoIds){
									yesIds = true;
									break;
								}
							}
							if(!yesIds){
								showIds = true;
								localIds.add(foAcIds);
							}
						}
						if(showIds){
							handler.post(new Runnable() {
								
								@Override
								public void run() {
									sendNotif();
								}
							});
						}
					}
					
				}, 0L, 30000); // интервал - 60000 миллисекунд, 0
									// миллисекунд до первого запуска.
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

	}

	public ArrayList<Integer> getIdsEvents() {
		// Creating service handler class instance
		ServiceHandler sh = new ServiceHandler();
		// Making a request to url and getting response
		String jsonStr = sh.makeServiceCall(urlEvents, ServiceHandler.GET);
		if(jsonStr != null){
			try {
				JSONArray arEv = new JSONArray(jsonStr);
				ArrayList<Integer> resArrayIds = new ArrayList<Integer>();
				for (int i = 0; i < arEv.length(); i++) {
					JSONObject obj = arEv.getJSONObject(i);
					int arrIds = obj.getInt(TAG_PUSH_ID);
					String arrTitle = obj.getString(TAG_PUSH_TITLE);
 					resArrayIds.add(arrIds);
				}
				return resArrayIds;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		// запрос json
		new PushTest().execute();
		// sendNotif();
		return super.onStartCommand(intent, flags, startId);
	}

	void sendNotif() {
		// 1-я часть
		Notification notif = new Notification(R.drawable.logo64,
				"Добавлено новое мероприятие", System.currentTimeMillis());
		// 3-я часть
		Intent intent = new Intent(this, MainActivity.class);
		// intent.putExtra(MainActivity.FILE_NAME, "somefile");
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);

		// 2-я часть
		notif.setLatestEventInfo(this, "Новое мероприятие",
				"Новое мероприятие уже внутри.", pIntent);

		// ставим флаг, чтобы уведомление пропало после нажатия
		notif.flags |= Notification.FLAG_AUTO_CANCEL;

		// отправляем
		nm.notify(1, notif);
	}

	public IBinder onBind(Intent arg0) {
		return null;
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class PushTest extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh
					.makeServiceCall(urlEvents, ServiceHandler.GET);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONArray jsonArr = new JSONArray(jsonStr);

					// looping through All Contacts
					for (int i = 0; i < jsonArr.length(); i++) {
						JSONObject obj = jsonArr.getJSONObject(i);

						String idPush = obj.getString(TAG_PUSH_ID);

						// tmp hashmap for single contact
						HashMap<String, String> arrayContests = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						arrayContests.put(TAG_PUSH_ID, idPush);
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

		}
	}
}
