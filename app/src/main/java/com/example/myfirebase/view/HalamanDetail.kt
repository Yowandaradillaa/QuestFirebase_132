package com.example.myfirebase.view

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.myfirebase.modeldata.DetailSiswa

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSiswaSceen(
    navigateToEditItem: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(
        factory = PenyediaViewModel.Factory
    )
) {
    LaunchedEffect(Unit) {
        Log.d("DetailSiswaScreen", "Screen appeared, refreshing data...")
        viewModel.loadSiswa()
    }

    Scaffold(
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiDetail.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(
            dimensionResource(id = R.dimen.padding_large)
        )
    ) { innerPadding ->

        val coroutineScope = rememberCoroutineScope()

        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = stringResource(R.string.edit_siswa),
        )

        BodyDetailDataSiswa(
            statusUIDetail = viewModel.statusUIDetail,
            onDelete = {
                coroutineScope.launch {
                    viewModel.hapusSatuSiswa()
                    navigateBack()
                }
            },
            modifier = modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        )
    }
}
