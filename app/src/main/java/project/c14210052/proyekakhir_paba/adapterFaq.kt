package project.c14210052.proyekakhir_paba

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.c14210052.proyekakhir_paba.databinding.RecyclerFaqBinding
class adapterFaq(private val faqList: List<Faq>) : RecyclerView.Adapter<adapterFaq.FaqViewHolder>() {
    private var _filteredFaqList = faqList

    inner class FaqViewHolder(private val binding: RecyclerFaqBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(faqItem: Faq) {
            binding.question.text = faqItem.question
            binding.answer.text = faqItem.answer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val binding = RecyclerFaqBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FaqViewHolder(binding)
    }

    override fun getItemCount(): Int = _filteredFaqList.size

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int){
        holder.bind(_filteredFaqList[position])
    }

    fun filterList(query: String) {
        _filteredFaqList = if (query.isEmpty()) {
            faqList
        } else {
            faqList.filter {
                it.question.contains(query, ignoreCase = true) || it.answer.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }
}