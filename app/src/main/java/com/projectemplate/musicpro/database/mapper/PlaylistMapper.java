package com.projectemplate.musicpro.database.mapper;

import android.database.Cursor;

import com.projectemplate.musicpro.config.DatabaseConfig;
import com.projectemplate.musicpro.database.CursorParseUtility;
import com.projectemplate.musicpro.database.IRowMapper;
import com.projectemplate.musicpro.object.Playlist;

public class PlaylistMapper implements IRowMapper<Playlist> {
	@Override
	public Playlist mapRow(Cursor row, int rowNum) {
		Playlist song = new Playlist();
		song.setId(CursorParseUtility.getString(row, DatabaseConfig.KEY_ID));
		song.setName(CursorParseUtility.getString(row, DatabaseConfig.KEY_NAME));
		song.setListSongs(CursorParseUtility.getString(row, DatabaseConfig.KEY_LIST_SONG));
		return song;
	}
}