package com.projectemplate.musicpro.database;

import android.database.sqlite.SQLiteStatement;

public interface ParameterBinder {
	void bind(SQLiteStatement st, Object object);
}
