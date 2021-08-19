package com.uwaisalqadri.mangaapp.android.ui.mymanga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.uwaisalqadri.mangaapp.android.ui.browse.views.TopBar
import java.lang.reflect.Modifier

class MyMangaFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MyMangaScreen()
            }
        }
    }
    
    @Composable
    fun MyMangaScreen() {
        Column {
            TopBar(name = "My Mangas")
        }
    }
}