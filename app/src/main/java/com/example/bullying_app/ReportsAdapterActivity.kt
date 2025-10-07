package com.example.bullying_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bullying_app.model.RelatoResponse

class ReportsAdapterActivity(private val lista: List<RelatoResponse>) :
    RecyclerView.Adapter<ReportsAdapterActivity.ReportViewHolder>() {

    inner class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
        val tvText: TextView = itemView.findViewById(R.id.tvText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_report, parent, false)
        return ReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val relato = lista[position]
        holder.tvAuthor.text = if (relato.anonimo) "Anônimo" else relato.autorNome
        holder.tvText.text = relato.texto
    }

    override fun getItemCount(): Int = lista.size
}
