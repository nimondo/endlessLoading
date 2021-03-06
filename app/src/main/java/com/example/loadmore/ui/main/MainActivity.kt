package com.example.loadmore.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.example.loadmore.utils.Common.showSnackbarLong
import com.example.loadmore.GridRecyclerView.GridRecyclerViewActivity
import com.example.loadmore.LinearRecyclerView.LinearRecyclerViewActivity
import com.example.loadmore.R
import com.example.loadmore.StaggeredRecyclerView.StaggeredRecyclerViewActivity
import com.example.loadmore.databinding.ActivityMainBinding
import com.example.loadmore.kdi.mainActivityModule
import com.example.loadmore.ui.post.PostActivity
import com.example.loadmore.utils.Utils
import com.example.loadmore.utils.listener.HttpErrorListener
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton


class MainActivity  : AppCompatActivity() , KodeinAware, HttpErrorListener {
    private val _parentKodein by closestKodein()
    override val kodein: Kodein by retainedKodein {
        extend(_parentKodein)
        bind<Context>("ActivityContext") with singleton { this@MainActivity }
        bind<Activity>() with singleton { this@MainActivity }
        import(mainActivityModule)
    }
    private lateinit var loading: MaterialDialog
    private lateinit var binding: ActivityMainBinding
    private val viewModelFactory: ViewModelProvider.Factory by instance()
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding.viewModel = viewModel
        binding.linearRvBtn.setOnClickListener {
            val intent = Intent(this,
                PostActivity::class.java)
                startActivity(intent)
        }
//        linear_rv_btn.setOnClickListener {
//
//        }

        grid_rv_btn.setOnClickListener {
            val intent = Intent(this,
                GridRecyclerViewActivity::class.java)
            startActivity(intent)
        }

        staggered_rv_btn.setOnClickListener {
            val intent = Intent(this,
                StaggeredRecyclerViewActivity::class.java)
            startActivity(intent)
        }

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

}
