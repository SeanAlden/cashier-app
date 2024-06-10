package project.c14210052.proyekakhir_paba.FragmentClass

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import project.c14210052.proyekakhir_paba.databinding.RecyclerFaqBinding
class FaqAdapter(private val faqList: List<FaqItem>) : RecyclerView.Adapter<FaqAdapter.FaqViewHolder>() {
    private var filteredFaqList = faqList

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

    override fun getItemCount(): Int = filteredFaqList.size

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int){
        holder.bind(filteredFaqList[position])
    }

    fun filterList(query: String) {
        filteredFaqList = if (query.isEmpty()) {
            faqList
        } else {
            faqList.filter {
                it.question.contains(query, ignoreCase = true) || it.answer.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }
}