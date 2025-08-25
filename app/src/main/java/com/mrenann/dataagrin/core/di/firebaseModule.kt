package com.mrenann.dataagrin.core.di

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.mrenann.dataagrin.core.data.firestore.service.FirebaseService
import org.koin.dsl.module

val firebaseModule = module {
    single { Firebase.firestore }
    single { FirebaseService(get()) }
}
