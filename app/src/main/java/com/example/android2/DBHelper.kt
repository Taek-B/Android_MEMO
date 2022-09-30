package com.example.android2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, "listdb", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        //앱이 설치된 후 SQLiteOpenHelper 클래스가 이용되는 순간 한번 호출
        db?.execSQL("create table LIST_DB(" +
                "_id integer primary key autoincrement," +
                "edit not null)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //생성자에게 지정한 DB버전 정보가 변경 될때 마다 호출
    }
}