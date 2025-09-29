package com.example.bullying_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bullying_app.databinding.ItemReportBinding
import com.example.bullying_app.model.RelatoResponse

class ReportsAdapter : RecyclerView.Adapter<ReportsAdapter.ReportViewHolder>() {

    private val items = mutableListOf<RelatoResponse>()

    fun submitList(list: List<RelatoResponse>?) {
        items.clear()
        if (list != null) items.addAll(list)
        notifyDataSetChanged()
    }

    inner class ReportViewHolder(private val binding: ItemReportBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RelatoResponse) {
            binding.tvAuthor.text = item.autor_nome
            binding.tvText.text = item.texto
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = ItemReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}
