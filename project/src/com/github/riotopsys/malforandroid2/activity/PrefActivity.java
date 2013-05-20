package com.github.riotopsys.malforandroid2.activity;

import android.app.Activity;
import android.os.Bundle;
import com.github.riotopsys.malforandroid2.fragment.PrefFragment;

public class PrefActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefFragment()).commit();
	}

}
