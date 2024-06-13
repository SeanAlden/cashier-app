package project.c14210052.proyekakhir_paba

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class adapterProfile (
    private val listUsers:ArrayList<Users>
): RecyclerView.Adapter<adapterProfile.ListViewHolder>(){
    private lateinit var auth: FirebaseAuth
    private lateinit var onItemClickCallBack : OnItemClickCallBack

    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var _tvFullName: TextView = itemView.findViewById(R.id.tvFullNameProfile)
        var _tvEmail: TextView = itemView.findViewById(R.id.tvEmailProfile)
        var _btnSignOut: Button = itemView.findViewById(R.id.btnSignOutItem)
        var _editProfile: ImageButton = itemView.findViewById(R.id.ibEditProfile)
        var _deleteProfile: ImageButton = itemView.findViewById(R.id.ibDeleteProfile)
    }

    interface OnItemClickCallBack{
        fun editProfile(data: Users)
        fun signOut(data: Users)
        fun deleteProfile(data: Users)
    }

    fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack){
        this.onItemClickCallBack = onItemClickCallBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_profile,parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        auth = Firebase.auth
        val asetUsers = listUsers[position]
        holder._tvFullName.setText(asetUsers.fullname)
        holder._tvEmail.setText(asetUsers.email)
        holder._btnSignOut.setOnClickListener {
            onItemClickCallBack.signOut(listUsers[position])
        }
        holder._editProfile.setOnClickListener {
            onItemClickCallBack.editProfile((listUsers[position]))
        }
        holder._deleteProfile.setOnClickListener {
            onItemClickCallBack.deleteProfile(listUsers[position])
        }
    }
}

