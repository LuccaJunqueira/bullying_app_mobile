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

class ReportsAdapterActivity(
    // 🔹 Agora a lista é "var", pois será atualizada depois
    var lista: List<RelatoResponse>
) : RecyclerView.Adapter<ReportsAdapterActivity.ReportViewHolder>() {

    // 🧱 ViewHolder: guarda as referências dos componentes de cada item da lista
    inner class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAuthor: TextView = itemView.findViewById(R.id.tvAuthor)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
    }

    // 🔹 Cria o layout de cada item (item_report.xml)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_report, parent, false)
        return ReportViewHolder(view)
    }

    // 🔹 Preenche os dados de cada item da lista
    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val relato = lista[position]

        // 🕒 Formata a data/hora do relato
        val dataFormatada = try {
            val formatoEntrada = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val formatoSaida = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
            val data = formatoEntrada.parse(relato.dataEnvio)
            formatoSaida.format(data!!)
        } catch (e: Exception) {
            relato.dataEnvio
        }

        // 👤 Mostra "Anônimo" se o relato foi enviado dessa forma
        val userType = (holder.itemView.context as? ReportsActivity)?.intent?.getStringExtra("userType")
        holder.tvAuthor.text =
            if (userType == "ALUNO" && relato.anonimo) "Anônimo" else relato.autorNome ?: "Desconhecido"

        // 📝 Mostra a descrição
        holder.tvDescription.text = relato.descricao

        // 📅 Mostra a data formatada
        holder.tvDate.text = "Enviado em: $dataFormatada"

        // 🖱️ Clique no item abre detalhes do relato
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ReportDetailActivity::class.java)
            intent.putExtra("autor", if (relato.anonimo) "Anônimo" else relato.autorNome)
            intent.putExtra("data", dataFormatada)
            intent.putExtra("descricao", relato.descricao)
            context.startActivity(intent)
        }
    }

    // 🔢 Retorna o total de itens da lista
    override fun getItemCount(): Int = lista.size

    // 🔄 Novo método: atualiza a lista de relatos sem recriar o adapter
    fun updateData(novaLista: List<RelatoResponse>) {
        lista = novaLista
        notifyDataSetChanged() // avisa o RecyclerView que os dados mudaram
    }
}
