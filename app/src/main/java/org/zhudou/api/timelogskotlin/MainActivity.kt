package org.zhudou.api.timelogskotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var dbHelper=DbHelper(this)
        if(!dbHelper.isTableExist("logs")){
            dbHelper.exec("create table logs(_id integer primary key autoincrement,title text,begin_time text,end_time text,status text)")
        }

        var c=dbHelper.cursor("select * from logs where status='doing' order by _id limit 0,1")
        if (c != null) {
            if (c.moveToNext()){
                val intent = Intent(this@MainActivity, TimeActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this@MainActivity, LogsActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
