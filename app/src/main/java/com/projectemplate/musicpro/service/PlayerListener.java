package com.projectemplate.musicpro.service;

public interface PlayerListener {
	public void onSeekChanged(String lengthTime, String currentTime, int progress);

	public void onChangeSong(int indexSong);
}
