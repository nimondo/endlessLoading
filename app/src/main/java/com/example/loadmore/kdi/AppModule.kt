package com.example.loadmore.kdi

import android.content.Context
import android.content.res.Resources
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.loadmore.DATABASE_NAME
import com.example.loadmore.LoadMoreApplication
import com.example.loadmore.data.local.AppDatabase
import com.example.loadmore.data.local.dao.PostDao
import com.example.loadmore.utils.IRxSchedulers
import com.example.loadmore.utils.Utils
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

private const val MODULE_NAME = "App Module"

val appModule = Kodein.Module(MODULE_NAME, false) {
    bind<Utils>() with singleton { getUtils(instance("ApplicationContext")) }
    bind<Resources>() with singleton { instance<LoadMoreApplication>().resources }
    bind<IRxSchedulers>() with singleton { getIRxSchedulers() }
    bind<AppDatabase>() with singleton { providesAppDatabase(instance()) }
    bind<PostDao>() with singleton { providesPostDao(instance()) }

}

private fun getUtils(context: Context): Utils = Utils(context)

private fun getIRxSchedulers(): IRxSchedulers = object : IRxSchedulers {
    override fun main(): Scheduler = AndroidSchedulers.mainThread()
    override fun io(): Scheduler = Schedulers.io()
}

private fun providesAppDatabase(context: Context): AppDatabase =
    Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
        /*.addMigrations(MIGRATION_1_2)*/
        .fallbackToDestructiveMigration()
        // .allowMainThreadQueries()
        .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
        .build()
private fun providesPostDao(database: AppDatabase): PostDao = database.postDao()

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Change the table name to the correct one
        database.execSQL("ALTER TABLE user RENAME TO user")
    }
}