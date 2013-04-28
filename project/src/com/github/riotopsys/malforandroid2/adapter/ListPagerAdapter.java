package com.github.riotopsys.malforandroid2.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.github.riotopsys.malforandroid2.fragment.ItemListFragment;
import com.github.riotopsys.malforandroid2.model.AnimeWatchedStatus;
import com.google.inject.Inject;

public class ListPagerAdapter extends FragmentPagerAdapter {

	private Context ctx;

	@Inject
	public ListPagerAdapter(FragmentManager fm, Context ctx) {
		super(fm);
		this.ctx = ctx;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment f = new ItemListFragment();
		
		Bundle args = new Bundle();
		args.putSerializable("filter", AnimeWatchedStatus.values()[position]);
		
		f.setArguments(args);
		return f;
	}

	@Override
	public int getCount() {
		return AnimeWatchedStatus.values().length;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return ctx.getString(AnimeWatchedStatus.values()[position].getResource());
	}

}