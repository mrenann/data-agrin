package com.mrenann.dataagrin.core.data.firestore.service

import com.google.firebase.firestore.FirebaseFirestore

class FirebaseService(
    private val firestore: FirebaseFirestore
) {
    fun activitiesCollection() = firestore.collection("activities")
}