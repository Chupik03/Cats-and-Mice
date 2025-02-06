package `in`.eyehunt.sqliteandroidexample.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.text.style.ClickableSpan
import android.util.Log
import ru.egordresvyanskii.gameforcats.Statistica
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DbStatistica(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSIOM) {

    companion object {
        private val DB_NAME = "StatisticaDB"
        private val DB_VERSIOM = 1;
        private val TABLE_NAME = "score"
        private val CURRENT_DATE = "date"
        private val PERCENTAGE_OF_CLIKCS = "PERCENTAGE_OF_CLIKCS"
        private val CLICK_COUNTER_MOUSE = "CLICK_COUNTER_MOUSE"
        private val CLICK_COUNTER = "CLICK_COUNTER"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME(" +
                "$CURRENT_DATE TEXT, " +
                "$CLICK_COUNTER INTEGER, " +
                "$CLICK_COUNTER_MOUSE INTEGER, " +
                "$PERCENTAGE_OF_CLIKCS DOUBLE)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Called when the database needs to be upgraded
    }

    //Inserting (Creating) data
    fun addResultat(stat: Statistica): Boolean {
        //Create and/or open a database that will be used for reading and writing.
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CURRENT_DATE, stat.date)
        values.put(CLICK_COUNTER, stat.CountClick)
        values.put(CLICK_COUNTER_MOUSE, stat.CountClickOnMouse)
        values.put(PERCENTAGE_OF_CLIKCS, "%.1f".format(stat.PercentageClicks))
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        Log.v("InsertedID", "$_success")
        return (Integer.parseInt("$_success") != -1)
    }

    fun getAllScore(): ArrayList<Statistica> {
        val db = this.readableDatabase
        var scoreList = ArrayList<Statistica>()

        val c = db.query(TABLE_NAME,
            arrayOf(CURRENT_DATE, CLICK_COUNTER, CLICK_COUNTER_MOUSE, PERCENTAGE_OF_CLIKCS),
            null, null, null, null, null, null)
        c.moveToFirst()
        do{
            val stat = Statistica()
            stat.date = c.getString(0)
            stat.CountClick = c.getInt(1)
            stat.CountClickOnMouse = c.getInt(2)
            stat.PercentageClicks = c.getDouble(3)
            scoreList.add(0, stat)
        }while (c.moveToNext())
        if(scoreList.size > 10){
            scoreList.removeAt(scoreList.size-1)
            db.execSQL("DELETE FROM $TABLE_NAME WHERE $CURRENT_DATE=(SELECT MIN($CURRENT_DATE) FROM $TABLE_NAME)")
        }
        c.close()
        return scoreList
    }

    fun isRecordExists(): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME,
            arrayOf(CURRENT_DATE, CLICK_COUNTER, CLICK_COUNTER_MOUSE, PERCENTAGE_OF_CLIKCS),
            null, null, null, null, null, null)
        val recordExists = cursor.moveToFirst()
        cursor.close()
        db.close()
        return recordExists
    }

}