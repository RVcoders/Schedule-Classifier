package com.rvcoder.scheduleclassifier;

import com.rvcoder.scheduleclassifier.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class FrontActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_front);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	public void tutorial(View v)
	{
		Intent intent = new Intent(this,Tutorial.class);
		startActivity(intent);
	}
	public void continu(View v)
	{
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
	}

}
