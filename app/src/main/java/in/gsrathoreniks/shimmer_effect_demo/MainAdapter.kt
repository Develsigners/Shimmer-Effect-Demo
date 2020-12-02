package `in`.gsrathoreniks.shimmer_effect_demo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


private const val TAG = "MainAdapter"

class MainAdapter(
    private val users: ArrayList<User>
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.findViewById<TextView>(R.id.textViewUserName).text = user.name
            itemView.findViewById<TextView>(R.id.textViewUserEmail).text = user.email

            Glide.with(itemView.findViewById<ImageView>(R.id.imageViewAvatar).context)
                .load(user.avatar)
                .into(itemView.findViewById(R.id.imageViewAvatar))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_recyclerview_row, parent,
                false
            )
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(users[position])

    fun addData(list: List<User>) {
        users.addAll(list)
    }

    companion object {
    }

}