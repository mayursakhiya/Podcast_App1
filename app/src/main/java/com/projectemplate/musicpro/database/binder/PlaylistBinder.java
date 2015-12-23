package com.projectemplate.musicpro.database.binder;

import android.database.sqlite.SQLiteStatement;

import com.projectemplate.musicpro.database.ParameterBinder;
import com.projectemplate.musicpro.object.Playlist;

public class PlaylistBinder implements ParameterBinder {
	public void bind(SQLiteStatement statement, Object object) {
		Playlist playlist = (Playlist) object;
		statement.bindString(1, playlist.getId());
		statement.bindString(2, playlist.getName());
		statement.bindString(3, playlist.getJsonArraySong());
	}
}
