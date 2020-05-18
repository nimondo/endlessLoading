package com.example.loadmore.kdi

import androidx.lifecycle.ViewModelProvider
import com.example.loadmore.base.navigator.ActivityNavigator
import com.example.loadmore.base.navigator.Navigator
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

val mainActivityModule = Kodein.Module("Main Activity Module", false) {
    import(commonModule)
//    bind() from singleton {
//        StaticDataRepository(
//            instance(),
//            instance(),
//            instance(),
//            instance()
//        )
//    }

}
val commonModule = Kodein.Module("Common Module", false) {
    bind<Navigator>() with singleton { ActivityNavigator(instance()) }
//    bind() from singleton { UserRepository(instance(), instance(), instance(), instance()) }
}