package com.example.githubuser.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.ConstantObject.Companion.EXTRA_ID
import com.example.githubuser.data.ConstantObject.Companion.EXTRA_URL
import com.example.githubuser.data.ConstantObject.Companion.EXTRA_USERNAME
import com.example.githubuser.data.local.entity.FavoriteEntity
import com.example.githubuser.data.remote.respone.User
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.ui.detail.DetailUserActivity
import com.example.githubuser.ui.main.ListGitUserAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: ListGitUserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar?.title = "Favorite User"


        adapter = ListGitUserAdapter()
        adapter.notifyDataSetChanged()
        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter.setOnItemClickCallback(object : ListGitUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                val intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                with(intent) {
                    putExtra(EXTRA_USERNAME, user.login)
                    putExtra(EXTRA_ID, user.id)
                    putExtra(EXTRA_URL, user.avatar_url)
                }
                startActivity(intent)
            }
        })

        binding.apply {
            rvFavoriteUser.setHasFixedSize(true)
            rvFavoriteUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavoriteUser.adapter = adapter
        }
        viewModel.getFavUser()?.observe(this, {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
            }
        })
    }
    private fun mapList(users: List<FavoriteEntity>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users) {
            val userMapped = User (
                user.login,
                user.id,
                user.avatar_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }
}