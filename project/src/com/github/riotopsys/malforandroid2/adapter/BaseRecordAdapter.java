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

package com.github.riotopsys.malforandroid2.adapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.github.riotopsys.malforandroid2.R;
import com.github.riotopsys.malforandroid2.adapter.SupplementaryText.SupplementaryTextFactory;
import com.github.riotopsys.malforandroid2.model.BaseRecord;
import com.google.inject.Inject;
import com.nostra13.universalimageloader.core.ImageLoader;

public class BaseRecordAdapter extends BaseAdapter implements SectionIndexer{
	
	@Inject
	private ImageLoader lazyLoader;
	
	@Inject
	private Comparator<BaseRecord> comparator;
	
	private List<BaseRecord> recordList = new LinkedList<BaseRecord>();
	private Section[] sections = new Section[0];

	private SupplementaryTextFactory textFactory;
	
	@Override
	public int getCount() {
		return recordList.size();
	}

	@Override
	public Object getItem(int position) {
		return recordList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Context ctx = parent.getContext();
		if ( convertView == null ){
			convertView = ((LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.anime_item, null);
		}
		BaseRecord record = recordList.get(position);
		
		((TextView)convertView.findViewById(R.id.title)).setText(Html.fromHtml(record.title));
		
		((TextView)convertView.findViewById(R.id.suplimentary_text)).setText( textFactory.getSupplementaryText(ctx, record) );
		
		ImageView imageView = (ImageView)convertView.findViewById(R.id.thumb_image);
		imageView.setImageBitmap(null);
		lazyLoader.displayImage(record.image_url,imageView ); 
		
		return convertView;
	}
	
	public void addAll( List<BaseRecord> anime ) {
		recordList.clear();
		if ( anime != null ){
			recordList.addAll(anime);
			Collections.sort( recordList, comparator );
			buildSections();
		}
		notifyDataSetChanged();
	}
	
	private static class Section{
		public String tag;
		
		/**
		 * Inclusive
		 */
		public int start; 
		
		/**
		 * Exclusive, or start of next
		 */
		public int end;
		
		@Override
		public String toString() {
			return tag;
		}
	}

	private void buildSections() {
		LinkedList<Section> scratch = new LinkedList<Section>();
		if ( recordList.size() == 0 ){
			return;
		}
		
		Section s = new Section();
		s.tag=makeTag(recordList.get(0));
		s.start = 0;
		s.end = 0;
		scratch.add(s);
		for ( int c = 1; c < recordList.size(); c++){
			String tag = makeTag(recordList.get(c));
			if ( !tag.equals(s.tag)){
				s.end = c;
				s = new Section();
				s.tag=tag;
				s.start = c;
				scratch.add(s);
			}
		}
		sections = scratch.toArray(sections);
	}

	private String makeTag(BaseRecord animeRecord) {
		char result = animeRecord.title.toUpperCase().charAt(0);
		if ( Character.isDigit(result) ){
			result = '#';
		}
		return Character.toString(result);
	}

	@Override
	public int getPositionForSection(int section) {
		if ( section >= sections.length ){
			return sections[sections.length-1].start;
		}
		if ( sections[section] != null ){
			return sections[section].start;
		}
		return 0;
	}

	@Override
	public int getSectionForPosition(int position) {
		for ( int c = 0; c < sections.length; c++  ){
			Section s = sections[c];
			if ( s == null ){
				continue;
			}
			if ( position >= s.start && position < s.end ){
				return c;
			}
		}
		return sections.length-1;
	}

	@Override
	public Object[] getSections() {
		return sections;
	}

	public void setSupplementaryTextFactory(SupplementaryTextFactory textFactory) {
		this.textFactory = textFactory;
	}

}
