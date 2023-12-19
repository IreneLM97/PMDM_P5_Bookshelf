package com.example.pmdm_p5_bookshelf.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pmdm_p5_bookshelf.R
import com.example.pmdm_p5_bookshelf.model.Book
import com.example.pmdm_p5_bookshelf.ui.BookshelfViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: BookshelfViewModel,
    onBackPressed: () -> Unit,
    onSendButtonClicked: (String) -> Unit
) {
    val detailUiState by viewModel.detailUiState.collectAsState()

    // Diseño de la estructura básica de la pantalla
    Scaffold(
        topBar = {
            // Barra superior personalizada
            TopAppBar(
                title = {
                    detailUiState.currentBook?.volumeInfo?.let { Text(text = it.title) }
                },
                // Icono de navegación que corresponde a una flecha hacia atrás
                navigationIcon = {
                    IconButton(
                        onClick = onBackPressed
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                },
                // Color personalizado para la barra superior
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(R.color.my_dark_blue)
                ),
            )
        }
    ) { innerPadding ->
        detailUiState.currentBook?.let {
            BookDetails(
                book = it,
                onSendButtonClicked = onSendButtonClicked,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

@Composable
fun BookDetails(
    book: Book,
    onSendButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Resumen de la información del libro
    val bookSummary = stringResource(
        R.string.book_summary,
        book.volumeInfo.title,
        book.volumeInfo.allAuthors,
        book.volumeInfo.published
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = book.volumeInfo.title,
                style = MaterialTheme.typography.titleLarge
            )
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(book.volumeInfo.imageLinks?.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = book.volumeInfo.title,
                contentScale = ContentScale.FillWidth,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = book.volumeInfo.allAuthors,
                style = MaterialTheme.typography.titleMedium
            )

            // Botón para enviar a otra aplicación
            Row(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_big))
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        onSendButtonClicked(bookSummary)
                    },
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.my_dark_blue)),
                ) {
                    Text(stringResource(R.string.send_button))
                }
            }
        }
    }


}