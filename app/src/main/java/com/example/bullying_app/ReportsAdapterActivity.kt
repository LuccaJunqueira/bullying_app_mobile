package com.example.bullying_app

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bullying_app.model.RelatoResponse
import java.text.SimpleDateFormat
import java.util.*

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

        // 🕒 Formata a data/hora de envio
        val dataFormatada = try {
            val formatoEntrada = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val formatoSaida = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
            val data = formatoEntrada.parse(relato.dataEnvio)
            formatoSaida.format(data!!)
        } catch (e: Exception) {
            relato.dataEnvio
        }

        // 👤 Autor (ou "Anônimo")
        holder.tvAuthor.text =
            if (relato.anonimo) "Anônimo" else relato.autorNome ?: "Desconhecido"

        // 📝 Descrição do relato
        holder.tvDescription.text = relato.descricao

        // 📅 Data e hora
        holder.tvDate.text = "Enviado em: $dataFormatada"

        // 🖱️ Clique no card para abrir detalhes
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ReportDetailActivity::class.java)
            intent.putExtra("autor", if (relato.anonimo) "Anônimo" else relato.autorNome)
            intent.putExtra("data", dataFormatada)
            intent.putExtra("descricao", relato.descricao)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = lista.size
}
