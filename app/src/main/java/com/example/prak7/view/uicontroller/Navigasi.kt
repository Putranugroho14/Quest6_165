package com.example.prak7.view.uicontroller

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prak7.R
import com.example.prak7.model.DataJK.JenisK
import com.example.prak7.view.FormSiswa // <-- Import yang benar
import com.example.prak7.view.TampilSiswa
import com.example.prak7.viewmodel.SiswaViewModel


enum class Navigasi {
    Formulir,
    Detail
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SiswaApp(
    modifier: Modifier = Modifier,
    viewModel: SiswaViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    Scaffold { isiRuang ->
        // Ganti 'statusUI' menjadi 'uiState' agar sesuai dengan TampilSiswa
        val uiState = viewModel.statusUI.collectAsState().value
        NavHost(
            navController = navController,
            startDestination = Navigasi.Formulir.name,
            modifier = Modifier.padding(isiRuang)
        ) {
            composable(route = Navigasi.Formulir.name) {
                val konteks = LocalContext.current
                FormSiswa(
                    pilihanJK = JenisK.map{id -> konteks.resources.getString(id)},
                    onSubmitBtnClick = {
                        viewModel.setSiswa(it)
                        navController.navigate(Navigasi.Detail.name)
                    }
                )
            }
            composable(route = Navigasi.Detail.name) {
                TampilSiswa(
                    statusUiSiswa = uiState,
                    onBackBtnClick = { cancelAndBackToFormSiswa(navController) }
                )
            }
        }
    }
}

private fun cancelAndBackToFormSiswa(
    navController: NavHostController
) {
    navController.popBackStack(Navigasi.Formulir.name, inclusive = false)
}