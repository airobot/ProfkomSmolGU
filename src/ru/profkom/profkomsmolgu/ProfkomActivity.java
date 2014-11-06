package ru.profkom.profkomsmolgu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfkomActivity extends Activity {

	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profkom_fragment);
		// Создание кнопки назад.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent intent = getIntent();

		TextView textView = (TextView) findViewById(R.id.o_profkom);
		textView.setText(Html.fromHtml(getString(R.string.o_profkome)));

		Button b_s_p = (Button) findViewById(R.id.btn_site_profkom);
		Button b_vk = (Button) findViewById(R.id.btn_vk);
		Button b_ins = (Button) findViewById(R.id.btn_ins);
		Button b_tw = (Button) findViewById(R.id.btn_tw);

		b_s_p.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent myWebLink = new Intent(
						android.content.Intent.ACTION_VIEW);
				myWebLink.setData(Uri.parse("http://www.profcom.pro/"));
				startActivity(myWebLink);
			}
		});

		b_vk.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent myWebLink = new Intent(
						android.content.Intent.ACTION_VIEW);
				myWebLink.setData(Uri.parse("http://vk.com/v_profkome"));
				startActivity(myWebLink);
			}
		});

		b_ins.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent myWebLink_ins = new Intent(
						android.content.Intent.ACTION_VIEW);
				myWebLink_ins.setData(Uri
						.parse("http://instagram.com/profkomsmolgu"));
				startActivity(myWebLink_ins);
			}
		});

		b_tw.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent myWebLink_tw = new Intent(
						android.content.Intent.ACTION_VIEW);
				myWebLink_tw.setData(Uri
						.parse("https://twitter.com/Profkom_SmolGU"));
				startActivity(myWebLink_tw);
			}
		});
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