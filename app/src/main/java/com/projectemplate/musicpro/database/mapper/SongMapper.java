package com.projectemplate.musicpro.database.mapper;

import android.database.Cursor;

import com.projectemplate.musicpro.config.DatabaseConfig;
import com.projectemplate.musicpro.database.CursorParseUtility;
import com.projectemplate.musicpro.database.IRowMapper;
import com.projectemplate.musicpro.object.Song;

public class SongMapper implements IRowMapper<Song> {
	@Override
	public Song mapRow(Cursor row, int rowNum) {
		Song song = new Song();
		song.setId(CursorParseUtility.getString(row, DatabaseConfig.KEY_ID));
		song.setName(CursorParseUtility.getString(row, DatabaseConfig.KEY_NAME));
		song.setUrl(CursorParseUtility.getString(row, DatabaseConfig.KEY_URL));
		song.setImage(CursorParseUtility.getString(row, DatabaseConfig.KEY_IMAGE));
		song.setArtist(CursorParseUtility.getString(row, DatabaseConfig.KEY_ARTIST));
		song.setPosition(CursorParseUtility.getInt(row, DatabaseConfig.KEY_POSITION));
		return song;
	}
}