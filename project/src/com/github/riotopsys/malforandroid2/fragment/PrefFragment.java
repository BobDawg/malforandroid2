/**
 * 
 */
package com.github.riotopsys.malforandroid2.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import com.github.riotopsys.malforandroid2.R;

/**
 * @author Robert LaFont Jr
 *
 */
public class PrefFragment extends PreferenceFragment {

	/* (non-Javadoc)
	 * @see android.preference.PreferenceFragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Load preferences from preferences.xml
		addPreferencesFromResource(R.xml.preferences);
	}
	
}
