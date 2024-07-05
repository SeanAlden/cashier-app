package project.c14210052_c14210182.proyekakhir_paba

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.FirebaseApp
import project.c14210052_c14210182.proyekakhir_paba.api.NewsItem
import project.c14210052_c14210182.proyekakhir_paba.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // melakukan deklarasi variabel untuk binding (menggunakan binding)
    // dan tampilan halaman 2
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // menginisialisasi firebase
        FirebaseApp.initializeApp(this)

        // inisialisasi layout menggunakan tampilan binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // inisialisasi view pager 2 dan juga adapter untuk view pager 2
        viewPager = binding.viewPager
        viewPager.adapter = ViewPagerAdapter(this)

        // mengatur navigasi untuk setiap tombol pada footer
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> viewPager.currentItem = 0
                R.id.profile -> viewPager.currentItem = 1
                R.id.faq -> viewPager.currentItem = 2
                else -> false
            }
            true
        }

        // untuk mengatur swipe pada halaman fragment
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // mengupdate simbol fragment yang terilih pada footer berdasarkan halaman fragment sekarang
                when (position) {
                    0 -> binding.bottomNavigationView.selectedItemId = R.id.home
                    1 -> binding.bottomNavigationView.selectedItemId = R.id.profile
                    2 -> binding.bottomNavigationView.selectedItemId = R.id.faq
                }
            }
        })
    }

    // adapter untuk menghandle fragment pada view pager 2
    private inner class ViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
        private val fragments = listOf(
            fHome(),
            fProfile(),
            fFaq()
        )

        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment = fragments[position]
    }

}