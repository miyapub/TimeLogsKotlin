package org.zhudou.api.timelogskotlin

import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import org.zhudou.api.timelogskotlin.R


/**
 * Created by morgan on 2017/8/20.
 */
class DbHelper(baseContext: Context) {

    private lateinit var db:SQLiteDatabase
    init {
        var dbName=baseContext.getString(R.string.app_db_name)
        this.db= SQLiteDatabase.openOrCreateDatabase(baseContext.filesDir.path+"$dbName.db",null);
        //Toast.makeText(baseContext,"初始化", Toast.LENGTH_SHORT).show()
    }
    fun exec(sql:String){
        this.db.execSQL(sql)
    }
    //是否存在表
    fun isTableExist(tableName: String): Boolean {
        val sql = "select count(*) as c from sqlite_master where type ='table' and name ='$tableName' "
        var flag=false
        var cursor = this.db.rawQuery(sql, null)
        if (cursor.moveToNext()) {
            val count = cursor.getInt(0)
            if (count > 0) {
                //存在
                flag=true
            }
        }
        cursor.close()
        return flag
    }

    fun cursor(sql: String): Cursor? {
        return this.db.rawQuery(sql, null)
    }

    //是否存在字段
    private fun isFieldExist(tableName: String, columnName: String): Boolean {
        var result = false
        var cursor: Cursor? = null
        try {
            //查询一行
            cursor = this.db.rawQuery("SELECT * FROM $tableName LIMIT 0", null)
            result = cursor != null && cursor.getColumnIndex(columnName) != -1
        } catch (e: Exception) {
            //Log.e(TAG, "checkColumnExists1..." + e.message)
        } finally {
            if (null != cursor && !cursor.isClosed) {
                cursor.close()
            }
        }
        return result
    }

    fun closeDB(){
        this.db.close()
    }
}