/**
 * Copyright 2013 C. A. Fitzgerald
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.github.riotopsys.malforandroid2.activity;

import java.util.List;

import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.github.riotopsys.malforandroid2.GlobalState;
import com.github.riotopsys.malforandroid2.R;
import com.github.riotopsys.malforandroid2.adapter.ListPagerAdapter;
import com.github.riotopsys.malforandroid2.database.ReadNameValuePairs;
import com.github.riotopsys.malforandroid2.database.ReadNameValuePairs.Callback;
import com.github.riotopsys.malforandroid2.fragment.AnimeDetailFragment;
import com.github.riotopsys.malforandroid2.fragment.LoginFragment;
import com.github.riotopsys.malforandroid2.fragment.PlacardFragment;
import com.github.riotopsys.malforandroid2.model.NameValuePair;
import com.github.riotopsys.malforandroid2.server.BootReciever;
import com.github.riotopsys.malforandroid2.server.ServerInterface;
import com.google.inject.Inject;

public class HubActivity extends BaseDetailActivity implements Callback<String>, OnQueryTextListener {

	private static String TAG = HubActivity.class.getSimpleName();

	@InjectView(R.id.list_pager)
	private ViewPager listPager;

	@Inject
	private ListPagerAdapter adapter;
	
	@Inject 
	private GlobalState state;
	
	@Inject
	private LoginFragment login;
	
	private SearchView searchView;
	private MenuItem searchItem;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.main);

		listPager.setAdapter(adapter);
		listPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.standard_padding));
		
		new ReadNameValuePairs<String>(getHelper(), this).execute("USER","PASS");
		
		if ( !state.isSyncScheduled()){
			//somehow we hit this point with out starting the sync, so we'll do it now
			BootReciever.scheduleSync(state, this);
		}
		
		if ( detailFrame != null ){
			transitionDetail();
		}
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == R.id.refresh_menu_item) {
			ServerInterface.getAnimeList(this);
			if ( !state.loginSet() ){
				login.show(getSupportFragmentManager(), null);
			}
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.base, menu);
		
		searchItem =  menu.findItem(R.id.menu_search);
		
		searchView = (SearchView) searchItem.getActionView();
		searchView.setOnQueryTextListener(this);
		
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public void onNameValuePairsReady(List<NameValuePair<String>> data) {
		for (NameValuePair<String> pair : data) {
			if ("USER".equals(pair.name)) {
				state.setUser(pair.value);
			}
			if ("PASS".equals(pair.name)) {
				state.setPass(pair.value);
			}
		}
		if ( !state.loginSet() ){
			login.show(getSupportFragmentManager(), null);
		}
		
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		searchItem.collapseActionView();
		ServerInterface.searchAnime(this, query);
		listPager.setCurrentItem(adapter.getCount()-1,true);
		return true;
	}
	
	protected void transitionDetail() {
		if ( detailFrame != null ){
			transitionDetailTofragment();
		} else {
			transitionDetailToActivity();
		}
	}
	
	private void transitionDetailToActivity() {
		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra("ITEM", currentDetail);
		startActivity(intent);
		purgeFakeBackStack();
	}

	private void transitionDetailTofragment() {
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		Fragment fragment;
		if (currentDetail != null) {
			fragment = new AnimeDetailFragment();
			Bundle args = new Bundle();
			args.putInt("id", currentDetail.id);
			fragment.setArguments(args);
		} else {
			fragment = new PlacardFragment();
		}
		transaction.replace(R.id.detail_frame, fragment);
		transaction.commit();
	}

}
