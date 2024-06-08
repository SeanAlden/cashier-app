package project.c14210052.proyekakhir_paba

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.c14210052.proyekakhir_paba.databinding.RecyclerFaqBinding

class FaqAdapter(private val faqList: List<FaqItem>) : RecyclerView.Adapter<FaqAdapter.FaqViewHolder>() {
    inner class FaqViewHolder(private val binding: RecyclerFaqBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(faqItem: FaqItem) {
            binding.question.text = faqItem.question
            binding.answer.text = faqItem.answer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val binding = RecyclerFaqBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FaqViewHolder(binding)
    }

    override fun getItemCount(): Int = faqList.size

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int){
        holder.bind(faqList[position])
    }
}