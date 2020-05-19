package com.example.loadmore.ui.post

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.example.loadmore.utils.Common.showSnackbarLong
import com.example.loadmore.R
import com.example.loadmore.databinding.ActivityMainBinding
import com.example.loadmore.databinding.ActivityPostBinding
import com.example.loadmore.kdi.mainActivityModule
import com.example.loadmore.kdi.postActivityModule
import com.example.loadmore.ui.main.MainViewModel
import com.example.loadmore.utils.Utils
import com.example.loadmore.utils.listener.HttpErrorListener
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class PostActivity : AppCompatActivity() , KodeinAware, HttpErrorListener, PostListener {
    private val _parentKodein by closestKodein()
    override val kodein: Kodein by retainedKodein {
        extend(_parentKodein)
        bind<Context>("ActivityContext") with singleton { this@PostActivity }
        bind<Activity>() with singleton { this@PostActivity }
        import(postActivityModule)
    }
    private lateinit var loading: MaterialDialog
    private lateinit var binding: ActivityPostBinding
    private val viewModelFactory: ViewModelProvider.Factory by instance()
    private lateinit var viewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_post)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PostViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.postListener = this
        viewModel.httpErrorListener= this
        loading = Utils(this).showLoadingDialog()
        val isConnect =  Utils(this).isConnectedToInternet()
        viewModel.getAllArticles(isConnect)
    }
    override fun networkFailure() {
        this.hideLoading()
        Utils(this).errorToast(getString(R.string.network_failure))
    }

    override fun timeOutError() {
        this.hideLoading()
        Utils(this).errorToast(getString(R.string.network_timeout))
    }

    override fun errorMessage() {
        this.hideLoading()
        Utils(this).errorToast(getString(R.string.user_not_exit))
    }

    override fun errorMessage(error: String) {
        hideLoading()
        Utils(this).errorToast(error)
    }

    override fun loadingProgressbar() {
        loading.show()
    }

    override fun hideLoading() {
        loading.dismiss()
    }

    override fun serverNotResponse(error: Int) {
        this.hideLoading()
        showSnackbarLong(error)
    }

    override fun emptyData() {
        binding.noPostFound.visibility = View.VISIBLE
    }

    override fun hideEmptyData() {
        binding.noPostFound.visibility = View.GONE
    }

}
