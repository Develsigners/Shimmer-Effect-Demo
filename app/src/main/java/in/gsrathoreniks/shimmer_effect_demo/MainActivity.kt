package `in`.gsrathoreniks.shimmer_effect_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.facebook.shimmer.ShimmerFrameLayout

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MainAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupAPICall()
    }

    private fun setupUI() {
        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupAPICall() {
        AndroidNetworking.initialize(applicationContext)
        AndroidNetworking.get("https://5e510330f2c0d300147c034c.mockapi.io/users")
            .build()
            .getAsObjectList(User::class.java, object : ParsedRequestListener<List<User>> {
                override fun onResponse(users: List<User>) {
                    shimmerFrameLayout.stopShimmerAnimation()
                    shimmerFrameLayout.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    adapter.addData(users)
                    adapter.notifyDataSetChanged()
                }

                override fun onError(anError: ANError) {
                    shimmerFrameLayout.visibility = View.GONE
                    Toast.makeText(this@MainActivity, "Something Went Wrong", Toast.LENGTH_LONG).show()
                }
            })
    }

    override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmerAnimation()
    }

    override fun onPause() {
        shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }
}