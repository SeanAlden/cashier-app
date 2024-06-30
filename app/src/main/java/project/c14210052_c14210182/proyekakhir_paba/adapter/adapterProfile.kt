package project.c14210052_c14210182.proyekakhir_paba.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import project.c14210052_c14210182.proyekakhir_paba.R
import project.c14210052_c14210182.proyekakhir_paba.dataClass.Users

class adapterProfile (
    private val listUsers:ArrayList<Users>
): RecyclerView.Adapter<adapterProfile.ListViewHolder>(){
    private lateinit var auth: FirebaseAuth
    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var _tvUserName: TextView = itemView.findViewById(R.id.tvUsernameProfile)
        var _tvFullName: TextView = itemView.findViewById(R.id.tvFullNameProfile)
        var _tvEmail: TextView = itemView.findViewById(R.id.tvEmailProfile)
        var _btnSignOut: Button = itemView.findViewById(R.id.btnSignOutItem)
        var _editProfile: ImageButton = itemView.findViewById(R.id.ibEditProfile)
        var _deleteProfile: ImageButton = itemView.findViewById(R.id.ibDeleteProfile)
    }

    interface OnItemClickCallback{
        fun editProfile(data:Users)
        fun signOut(data: Users)
        fun deleteProfile(data:Users)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_profile,parent,false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }

    override fun onBindViewHolder(holder: adapterProfile.ListViewHolder, position: Int) {
        auth = Firebase.auth
        var asetUsers = listUsers[position]
        holder._tvUserName.setText(asetUsers.username)
        holder._tvFullName.setText(asetUsers.fullname)
        holder._tvEmail.setText(asetUsers.email)
        holder._btnSignOut.setOnClickListener {
            onItemClickCallback.signOut(listUsers[position])
        }
        holder._editProfile.setOnClickListener {
            onItemClickCallback.editProfile(listUsers[position])
        }
        holder._deleteProfile.setOnClickListener {
            onItemClickCallback.deleteProfile(listUsers[position])
        }
    }

}