package com.example.dorecomic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dorecomic.model.Chapter

class ChapterDetailViewModel: ViewModel() {
    private val mutableChapterItem = MutableLiveData<List<Chapter>>()
    val listChapter: LiveData<List<Chapter>> get() = mutableChapterItem

    fun passListChapter(item: List<Chapter>){
        mutableChapterItem.value = item
    }
}