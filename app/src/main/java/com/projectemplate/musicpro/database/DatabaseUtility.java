package com.projectemplate.musicpro.database;

import java.util.List;

import android.content.Context;

import com.projectemplate.musicpro.config.DatabaseConfig;
import com.projectemplate.musicpro.database.binder.PlaylistBinder;
import com.projectemplate.musicpro.database.binder.SongBinder;
import com.projectemplate.musicpro.database.mapper.PlaylistMapper;
import com.projectemplate.musicpro.database.mapper.SongMapper;
import com.projectemplate.musicpro.object.Playlist;
import com.projectemplate.musicpro.object.Song;

public final class DatabaseUtility {
	private PrepareStatement statement;

	public DatabaseUtility(Context context) {
		statement = new PrepareStatement(context);
	}

	private static String STRING_SQL_INSERT_INTO_TABLE_FAVORITE = "INSERT OR REPLACE INTO "
			+ DatabaseConfig.TABLE_FAVORITE + "(" + DatabaseConfig.KEY_ID + "," + DatabaseConfig.KEY_NAME + " ,"
			+ DatabaseConfig.KEY_URL + " ," + DatabaseConfig.KEY_IMAGE + " ," + DatabaseConfig.KEY_ARTIST + " ,"
			+ DatabaseConfig.KEY_POSITION + ") VALUES (?, ?, ?, ?, ?, ?)";

	public List<Song> getAllFavorite() {
		return statement.select(DatabaseConfig.TABLE_FAVORITE, "*", "", new SongMapper());
	}

	public boolean insertFavorite(Song song) {
		return statement.insert(STRING_SQL_INSERT_INTO_TABLE_FAVORITE, song, new SongBinder());
	}

	public boolean deleteFavorite(Song song) {
		return statement.query("DELETE FROM " + DatabaseConfig.TABLE_FAVORITE + " where " + DatabaseConfig.KEY_ID
				+ "='" + song.getId() + "'" + "and" + " " + DatabaseConfig.KEY_NAME + "='" + song.getName() + "'"
				+ "and" + " " + DatabaseConfig.KEY_ARTIST + "='" + song.getArtist() + "'", null);
	}

	private static String STRING_SQL_INSERT_INTO_TABLE_PLAYLIST = "INSERT OR REPLACE INTO "
			+ DatabaseConfig.TABLE_PLAYLIST + "(" + DatabaseConfig.KEY_ID + "," + DatabaseConfig.KEY_NAME + " ,"
			+ DatabaseConfig.KEY_LIST_SONG + ") VALUES (?, ?, ?)";

	public List<Playlist> getAllPlaylist() {
		return statement.select(DatabaseConfig.TABLE_PLAYLIST, "*", "", new PlaylistMapper());
	}

	public boolean insertPlaylist(Playlist playlist) {
		return statement.insert(STRING_SQL_INSERT_INTO_TABLE_PLAYLIST, playlist, new PlaylistBinder());
	}

	public boolean deletePlaylist(Playlist playlist) {
		return statement.query("DELETE FROM " + DatabaseConfig.TABLE_PLAYLIST + " where " + DatabaseConfig.KEY_ID
				+ "='" + playlist.getId() + "'", null);
	}
}
