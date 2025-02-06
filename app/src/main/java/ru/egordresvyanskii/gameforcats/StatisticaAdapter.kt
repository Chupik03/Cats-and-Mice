package ru.egordresvyanskii.gameforcats

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class StatisticaAdapter(private val activity: Activity, private val statistica: ArrayList<Statistica>): RecyclerView.Adapter<StatisticaAdapter.StatisticaHolder>(){

    class StatisticaHolder(item: View): RecyclerView.ViewHolder(item) {

        var tvCountClickOnMouse = item.findViewById<TextView>(R.id.tvCountClickOnMouse)
        var tvCountClick = item.findViewById<TextView>(R.id.tvCountClick)
        var tvPercentageClicks = item.findViewById<TextView>(R.id.tvPercentageClicks)
        var tvCurrentDate = item.findViewById<TextView>(R.id.tvCurrentDate)

    }
    // Создание нового держателя, и надувание для него представления из разметки
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticaHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.score_item, parent, false)
        return StatisticaHolder(view)
    }

    // Привязка данных к держателю
    override fun onBindViewHolder(holder: StatisticaHolder, position: Int) {
        val stat = statistica[position]
        holder.tvCurrentDate.text = "${stat.date}"
        holder.tvCountClick.text = "${stat.CountClick}"
        holder.tvCountClickOnMouse.text = "${stat.CountClickOnMouse}"
        holder.tvPercentageClicks.text = "${stat.PercentageClicks}%"
    }
    override fun getItemCount(): Int {
        return statistica.size
    }

}