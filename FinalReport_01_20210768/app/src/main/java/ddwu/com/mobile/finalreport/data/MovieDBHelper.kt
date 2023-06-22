package ddwu.com.mobile.finalreport.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log

class MovieDBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    val TAG = "MovieDBHelper"

    companion object {
        const val DB_NAME = "movie_db"
        const val TABLE_NAME = "movie_table"
        const val COL_IMAGE = "movie_img"
        const val COL_MOVIE = "movie"
        const val COL_DIRECTOR = "director"
        const val COL_STAR = "star"
        const val COL_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ( ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "$COL_MOVIE TEXT, $COL_DIRECTOR TEXT, $COL_STAR TEXT, $COL_DATE TEXT )"
        Log.d(TAG, CREATE_TABLE)
        db?.execSQL(CREATE_TABLE)

        /*샘플 데이터 - 필요할 경우 실행*/
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '스파이더맨', '조아킴', '샤메익 무어', '2023-06-21')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '엘리멘탈', '피터 손', '레아 루이스', '2023-06-14')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '범죄도시3', '이상용', '마동석', '2023-05-31')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '명탐정코난', '미야시타', '타카야마', '2023-06-23')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '플래시', '안드레스', '에즈라', '2023-06-14')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '트랜스포머', '스티븐', '안소니', '2023-06-06')")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {
        val DROP_TABLE ="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }
}


