package com.example.bullying_app

import android.content.Intent
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
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_report, parent, false)
        return ReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val relato = lista[position]

        holder.tvAuthor.text = relato.autorNome ?: "Anônimo"
        holder.tvDescription.text = relato.descricao
        holder.tvDate.text = "Data: ${relato.dataEnvio}"

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ReportDetailActivity::class.java)
            intent.putExtra("autor", relato.autorNome)
            intent.putExtra("data", relato.dataEnvio)
            intent.putExtra("descricao", relato.descricao)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = lista.size
}
