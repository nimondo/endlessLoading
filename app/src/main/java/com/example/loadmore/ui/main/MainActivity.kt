package com.example.loadmore.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loadmore.GridRecyclerView.GridRecyclerViewActivity
import com.example.loadmore.LinearRecyclerView.LinearRecyclerViewActivity
import com.example.loadmore.R
import com.example.loadmore.StaggeredRecyclerView.StaggeredRecyclerViewActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linear_rv_btn.setOnClickListener {
            val intent = Intent(this,
                LinearRecyclerViewActivity::class.java)
            startActivity(intent)
        }

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

}
