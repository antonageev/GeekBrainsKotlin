package com.antonageev.geekbrainskotlin.dsl

import com.antonageev.geekbrainskotlin.data.NoteRepository
import com.antonageev.geekbrainskotlin.data.provider.FirestoreProvider
import com.antonageev.geekbrainskotlin.data.provider.RemoteDataProvider
import com.antonageev.geekbrainskotlin.ui.main.MainViewModel
import com.antonageev.geekbrainskotlin.ui.note.NoteViewModel
import com.antonageev.geekbrainskotlin.ui.splash.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { NoteRepository(get()) }
    single<RemoteDataProvider> { FirestoreProvider(get(), get()) }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseAuth.getInstance() }
}

val splashModule = module {
    viewModel { SplashViewModel(get()) }
}

val mainModule = module {
    viewModel { MainViewModel(get()) }
}

val noteModule = module {
    viewModel { NoteViewModel(get()) }
}