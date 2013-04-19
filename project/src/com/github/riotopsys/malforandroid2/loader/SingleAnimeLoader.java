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

package com.github.riotopsys.malforandroid2.loader;

import java.sql.SQLException;

import android.content.Context;
import android.util.Log;

import com.github.riotopsys.malforandroid2.model.AnimeRecord;
import com.j256.ormlite.dao.Dao;

public class SingleAnimeLoader extends DBLoader<AnimeRecord> {

	private static final String TAG = SingleAnimeLoader.class.getSimpleName();
	private int id;

	public SingleAnimeLoader(Context context, int id) {
		super(context);
		this.id = id;
	}

	@Override
	public AnimeRecord loadInBackground() {
		try {
			Dao<AnimeRecord, Integer> dao = getHelper().getDao(AnimeRecord.class);
			
			return dao.queryForId(id);
		} catch (SQLException e) {
			Log.e(TAG, "", e);
		}
		return null;
	}

}
