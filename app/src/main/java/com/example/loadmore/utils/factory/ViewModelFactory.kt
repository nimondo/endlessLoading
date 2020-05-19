package com.example.loadmore.utils.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.loadmore.data.repositories.PostRepository
import com.example.loadmore.ui.main.MainViewModel
import com.example.loadmore.ui.post.PostViewModel

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val context: Context
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(context) as T
    }
}
@Suppress("UNCHECKED_CAST")
class PostViewModelFactory(
    private val repository: PostRepository,
    private val context: Context
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PostViewModel(repository,context) as T
    }
}