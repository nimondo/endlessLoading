package com.example.loadmore.ui.post

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.example.loadmore.R
import com.example.loadmore.BR
import com.example.loadmore.Constant
import com.example.loadmore.data.model.Post
import com.example.loadmore.databinding.ItemPostListBinding
import com.example.loadmore.databinding.ProgressLoadingBinding
import kotlinx.android.synthetic.main.progress_loading.view.*

class PostListAdapter(val viewModel: PostViewModel) :
    Adapter<RecyclerView.ViewHolder>() {
    private lateinit var postList: List<Post>
    override fun getItemViewType(position: Int): Int {
        return if (postList[position] == null) Constant.VIEW_TYPE_LOADING
        else super.getItemViewType(position)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):  ViewHolder {
        val binding: ItemPostListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_post_list,
            parent,
            false
        )
        return if (viewType == Constant.VIEW_TYPE_ITEM) {
            ViewHolder(binding)
        }else{
            val progressingBinding:ProgressLoadingBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.progress_loading, parent,
                false
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                progressingBinding.progressbar.indeterminateDrawable.colorFilter = BlendModeColorFilter(Color.WHITE, BlendMode.SRC_ATOP)
            } else {
                progressingBinding.progressbar.indeterminateDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)
            }
            LoadingViewHolder(progressingBinding)
            ViewHolder(progressingBinding)
//            ViewHolder(binding)
        }
//        return ViewHolder(binding)
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
    class LoadingViewHolder(itemView: ProgressLoadingBinding) : RecyclerView.ViewHolder(itemView.root)
}