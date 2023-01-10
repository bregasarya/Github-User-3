package com.example.githubuser.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.data.ConstantObject.Companion.EXTRA_ID
import com.example.githubuser.data.ConstantObject.Companion.EXTRA_URL
import com.example.githubuser.data.ConstantObject.Companion.EXTRA_USERNAME
import com.example.githubuser.data.ConstantObject.Companion.TAB_TITLES
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        val actionbar = supportActionBar
        actionbar?.title = "Detail User"

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        val username = intent.getStringExtra(EXTRA_USERNAME) ?: "null"
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatar_url = intent.getStringExtra(EXTRA_URL) ?: "null"

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel.setUserDetail(username)
        Log.d("Detail", "username: $username")

        viewModel.getDetailUser().observe(this, {
            detailBinding.apply {
                Glide.with(this@DetailUserActivity)
                    .load(it.avatarUrl)
                    .centerCrop()
                    .into(imgItemAvatar)

                tvItemUsername.text = it.login
                tvItemName.text = it.name
                tvItemLocationValue.text = it.location
                tvItemCompanyValue.text = it.company
                tvFollowerValue.text = "${it.followers}"
                tvFollowingValue.text = "${it.following}"
            }
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null) {
                    if (count > 0) {
                        detailBinding.togFavorit.isChecked = true
                        _isChecked = true
                    } else {
                        detailBinding.togFavorit.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        detailBinding.togFavorit.setOnClickListener{
            _isChecked = !_isChecked
            if (_isChecked) {
                viewModel.insertFavoriteUser(username, id, avatar_url)
            } else {
                viewModel.deleteFavoriteUser(id)
            }
            detailBinding.togFavorit.isChecked = _isChecked
        }


        val followAdapter = SectionsPagerAdapter(this, bundle)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = followAdapter
        val tabs: TabLayout = findViewById(R.id.tabs_follows)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}