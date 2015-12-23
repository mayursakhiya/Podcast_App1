package com.projectemplate.musicpro.database;

import android.database.Cursor;

public interface IRowMapper<E> {
	E mapRow(Cursor row, int rowNum);
}