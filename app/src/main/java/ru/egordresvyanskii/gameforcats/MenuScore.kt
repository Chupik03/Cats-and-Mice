package ru.egordresvyanskii.gameforcats

import android.app.Activity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import `in`.eyehunt.sqliteandroidexample.db.DbStatistica

class MenuScore: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_statistics)
        val dbStatistica = DbStatistica(this)

        if(dbStatistica.isRecordExists()){
            findViewById<TextView>(R.id.tvInfo).visibility = View.INVISIBLE
            findViewById<ConstraintLayout>(R.id.clLabel).visibility = View.VISIBLE
            val adapter = StatisticaAdapter(this, dbStatistica.getAllScore())
            val rcView = findViewById<RecyclerView>(R.id.rvStatistica)
            rcView.layoutManager = LinearLayoutManager(applicationContext)
            rcView.adapter = adapter
        }

        findViewById<Button>(R.id.bt_back).setOnClickListener{
            finish()
        }
    }
}