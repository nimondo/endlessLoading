package com.example.loadmore.kdi

import androidx.lifecycle.ViewModelProvider
import com.example.loadmore.base.navigator.ActivityNavigator
import com.example.loadmore.base.navigator.Navigator
import com.example.loadmore.data.repositories.PostRepository
import com.example.loadmore.utils.factory.MainViewModelFactory
import com.example.loadmore.utils.factory.PostViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val mainActivityModule = Kodein.Module("Main Activity Module", false) {
    import(commonModule)
    bind<ViewModelProvider.Factory>() with singleton {
        MainViewModelFactory(
            instance("ActivityContext")
        )
    }
//    bind() from singleton {
//        StaticDataRepository(
//            instance(),
//            instance(),
//            instance(),
//            instance()
//        )
//    }

}
val postActivityModule = Kodein.Module("Main Activity Module", false) {
    import(commonModule)
    bind<ViewModelProvider.Factory>() with singleton {
        PostViewModelFactory(
            instance(),
            instance("ActivityContext")
        )
    }
        bind() from singleton {
        PostRepository(
            instance(),
            instance(),
            instance()
        )
    }
}
val commonModule = Kodein.Module("Common Module", false) {
    bind<Navigator>() with singleton { ActivityNavigator(instance()) }
//    bind() from singleton { UserRepository(instance(), instance(), instance(), instance()) }
}