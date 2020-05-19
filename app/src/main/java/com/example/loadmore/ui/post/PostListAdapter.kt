package com.example.loadmore.ui.post

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.loadmore.R
import com.example.loadmore.BR
import com.example.loadmore.data.model.Post
import com.example.loadmore.databinding.ItemPostListBinding

class PostListAdapter(val viewModel: PostViewModel) :
    RecyclerView.Adapter<PostListAdapter.ViewHolder>() {
    private lateinit var postList: List<Post>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: ItemPostListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_post_list,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostListAdapter.ViewHolder, position: Int) {
        holder.bind(viewModel, postList[position])
    }
    override fun getItemCount(): Int {
        return if (::postList.isInitialized) postList.size else 0
    }

    fun updatePostList(animalAllList: List<Post>) {
        this.postList = animalAllList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemPostListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            viewModel: PostViewModel,
            posts: Post
        ) {
            binding.setVariable(BR.viewModel, viewModel)
            Log.i("adapterArticles", posts.toString())
            binding.setVariable(BR.post, posts)
        }
    }
}