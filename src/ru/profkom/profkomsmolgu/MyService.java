package ru.profkom.profkomsmolgu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;

public class MyService extends Service {

	// URL to get contacts JSON
	private static String urlEvents = "http://profcom.pro/api/v1/events";
	private static String urlAkchii = "http://profcom.pro/api/v1/discounts";

	// JSON Node names
	private static final String TAG_PUSH_ID = "id";
	private static final String TAG_PUSH_CATEGORY = "category";

	// Hashmap for ListView
	ArrayList<HashMap<String, String>> contests;

	NotificationManager nm;

	ArrayList<Integer> localEventsIds;
	ArrayList<Integer> localAkchiiIds;

	@Override
	public void onCreate() {
		super.onCreate();
		EventsNotification();
		AkchiiNotification();
	}

	public ArrayList<Integer> getIdsEvents() {
		// Creating service handler class instance
		ServiceHandler sh = new ServiceHandler();
		// Making a request to url and getting response
		String jsonStr = sh.makeServiceCall(urlEvents, ServiceHandler.GET);
		if (jsonStr != null) {
			try {
				JSONArray arEv = new JSONArray(jsonStr);
				ArrayList<Integer> resArrayIds = new ArrayList<Integer>();
				for (int i = 0; i < arEv.length(); i++) {
					JSONObject obj = arEv.getJSONObject(i);
					int arrIds = obj.getInt(TAG_PUSH_ID);
					resArrayIds.add(arrIds);	
				}
				return resArrayIds;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}

	public ArrayList<Integer> getIdsAkchii() {
		// Creating service handler class instance
		ServiceHandler sh = new ServiceHandler();
		// Making a request to url and getting response
		String jsonStr = sh.makeServiceCall(urlAkchii, ServiceHandler.GET);
		if(jsonStr != null){
			try {
				JSONArray arEv = new JSONArray(jsonStr);
				ArrayList<Integer> resArrayIds = new ArrayList<Integer>();
				for (int i = 0; i < arEv.length(); i++) {
					JSONObject obj = arEv.getJSONObject(i);
					int arrIds = obj.getInt(TAG_PUSH_ID);
					String arrCat = obj.getString(TAG_PUSH_CATEGORY);
					if (arrCat.equals("Акции")){
						resArrayIds.add(arrIds);	
					}
				}
				return resArrayIds;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		// sendNotif();
		return super.onStartCommand(intent, flags, startId);
	}

	void sendNotifEvents() {
		// 1-я часть
		Notification notif = new Notification(R.drawable.logo64,
				"Добавлено новое мероприятие", System.currentTimeMillis());
		// 3-я часть
		Intent intent = new Intent(this, MainActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
				Intent.FLAG_ACTIVITY_NEW_TASK);

		// 2-я часть
		notif.setLatestEventInfo(this, "Новое мероприятие",
				"Новое мероприятие в Профкоме СмолГУ", pIntent);

		// ставим флаг, чтобы уведомление пропало после нажатия
		notif.flags |= Notification.FLAG_AUTO_CANCEL;
		notif.defaults |= Notification.DEFAULT_SOUND;

		// отправляем
		nm.notify(2, notif);
	}

	void sendNotifAkchii() {
		// 1-я часть
		Notification notif = new Notification(R.drawable.logo64,
				"Акции! Всем Акции!", System.currentTimeMillis());
		// 3-я часть
		Intent intent = new Intent(this, DiscontActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
				Intent.FLAG_ACTIVITY_NEW_TASK);

		// 2-я часть
		notif.setLatestEventInfo(this, "Акция",
				"Новая Акция от Профком СмолГУ", pIntent);

		// ставим флаг, чтобы уведомление пропало после нажатия
		notif.flags |= Notification.FLAG_AUTO_CANCEL;
		notif.defaults |= Notification.DEFAULT_SOUND;
		// отправляем
		nm.notify(1, notif);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public void EventsNotification() {
		final Handler handler = new Handler();
		Timer myTimer = new Timer(); // Создаем таймер
		myTimer.schedule(new TimerTask() { // Определяем задачу
					@Override
					public void run() {
						if (localEventsIds == null) {
							localEventsIds = getIdsEvents();
							return;
						}
						ArrayList<Integer> actualIds = getIdsEvents();
						if (actualIds == null) {
							return;
						}
						boolean showIds = false;
						for (int i = 0; i < actualIds.size(); i++) {
							int foAcIds = actualIds.get(i);
							boolean yesIds = false;
							for (int j = 0; j < localEventsIds.size(); j++) {
								int foLoIds = localEventsIds.get(j);
								if (foAcIds == foLoIds) {
									yesIds = true;
									break;
								}
							}
							if (!yesIds) {
								showIds = true;
								localEventsIds.add(foAcIds);
							}
						}
						if (showIds) {
							handler.post(new Runnable() {

								@Override
								public void run() {
									sendNotifEvents();
								}
							});
						}
					}

				}, 0L, 10000); // интервал - 60000 миллисекунд, 0
								// миллисекунд до первого запуска.
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}

	public void AkchiiNotification() {
		final Handler handler = new Handler();
		Timer myTimer = new Timer(); // Создаем таймер
		myTimer.schedule(new TimerTask() { // Определяем задачу
					@Override
					public void run() {
						if (localAkchiiIds == null) {
							localAkchiiIds = getIdsAkchii();
							return;
						}
						ArrayList<Integer> actualAkchiiIds = getIdsAkchii();
						if (actualAkchiiIds == null) {
							return;
						}
						boolean showIds = false;
						for (int i = 0; i < actualAkchiiIds.size(); i++) {
							int foAcIds = actualAkchiiIds.get(i);
							boolean yesIds = false;
							for (int j = 0; j < localAkchiiIds.size(); j++) {
								int foLoIds = localAkchiiIds.get(j);
								if (foAcIds == foLoIds) {
									yesIds = true;
									break;
								}
							}
							if (!yesIds) {
								showIds = true;
								localAkchiiIds.add(foAcIds);
							}
						}
						if (showIds) {
							handler.post(new Runnable() {

								@Override
								public void run() {
									sendNotifAkchii();
								}
							});
						}
					}

				}, 0L, 10000); // интервал - 60000 миллисекунд, 0
								// миллисекунд до первого запуска.
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}

}
