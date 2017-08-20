package common

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by morgan on 2017/8/18.
 */
class ClientAction {
    fun push(name:String,msg:String){
        Thread(Runnable {
            var app_name=name
            var app_msg=msg
            val path = "http://api.zhudou.org/client_action.php"
            var result = ""
            var url: URL? = null
            var conn: HttpURLConnection? = null
            try {
                url = URL(path)
                try {
                    conn = url.openConnection() as HttpURLConnection
                    conn.requestMethod = "POST"
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0(compatible;MSIE 9.0;Windows NT 6.1;Trident/5.0)")
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")//请求的类型  表单数据
                    val data = "app_name=$app_name&app_msg=$app_msg"
                    conn.setRequestProperty("Content-Length", data.length.toString() + "")//数据的长度
                    conn.doOutput = true//设置向服务器写数据
                    val bytes = data.toByteArray()
                    conn.outputStream.write(bytes)//把数据以流的方式写给服务器
                    val code = conn.responseCode
                    if (code == 200) {
                        var inputStream: InputStream? = null
                        inputStream = conn.inputStream
                        result = readFromStream(inputStream)
                        //Toast.makeText(this,"ok",Toast.LENGTH_SHORT).show()
                    } else {
                        //Toast.makeText(this,"404",Toast.LENGTH_SHORT).show()
                    }
                } catch (e: IOException) {
                    //e.printStackTrace()
                    //Toast.makeText(this,e.printStackTrace().toString(),Toast.LENGTH_SHORT).show()
                }
            } catch (e: MalformedURLException) {
                //Toast.makeText(this,e.printStackTrace().toString(),Toast.LENGTH_SHORT).show()
            }
        }).start()
    }

    @Throws(IOException::class)
    fun readFromStream(`is`: InputStream): String {
        val baos = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var len = 0
        var flag=true
        while(flag){
            len = `is`.read(buffer)
            if(len!=-1){
                baos.write(buffer, 0, len)
            }else{
                flag=false
            }
        }
        `is`.close()
        val result = baos.toString()
        baos.close()
        return result
    }
}