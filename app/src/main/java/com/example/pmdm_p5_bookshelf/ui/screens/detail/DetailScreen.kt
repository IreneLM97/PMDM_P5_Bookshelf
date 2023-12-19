package com.example.pmdm_p5_bookshelf.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pmdm_p5_bookshelf.R
import com.example.pmdm_p5_bookshelf.model.Book
import com.example.pmdm_p5_bookshelf.model.Thumbnails
import com.example.pmdm_p5_bookshelf.model.VolumeInfo
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
        book.volumeInfo.bookAuthors,
        book.volumeInfo.bookPublished
    )

    Card(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(10.dp))
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(50.dp)),
                contentDescription = book.volumeInfo.title,
                contentScale = ContentScale.FillWidth,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.ic_loading_img),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(book.volumeInfo.imageLinks?.httpsThumbnail)
                    .crossfade(true)
                    .build()
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = book.volumeInfo.title,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.my_dark_gray)
                ),
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .padding(horizontal = dimensionResource(R.dimen.padding_small))
            )

            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopStart
            ) {
                Text(
                    text = stringResource(
                        id = R.string.book_authors,
                        book.volumeInfo.bookAuthors
                    ),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 20.sp,
                        color = colorResource(id = R.color.my_dark_gray)
                    ),
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(R.dimen.padding_small))
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopStart
            ) {
                Text(
                    text = stringResource(
                        id = R.string.book_published,
                        book.volumeInfo.bookPublished
                    ),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 20.sp,
                        color = colorResource(id = R.color.my_dark_gray)
                    ),
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(R.dimen.padding_small))
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Line()

            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopStart
            ) {
                Text(
                    text = stringResource(
                        id = R.string.book_description,
                        book.volumeInfo.bookDescription
                    ),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 20.sp,
                        color = colorResource(id = R.color.my_dark_gray)
                    ),
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(R.dimen.padding_small))
                )
            }

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

/**
 * Dibuja una línea horizontal.
 */
@Composable
private fun Line() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(colorResource(R.color.my_dark_gray))
    )
}

@Preview(showBackground = true)
@Composable
fun BookDetailsPreview() {
    BookDetails(
        book = Book(
            volumeInfo = VolumeInfo(
                title = "Book",
                imageLinks = Thumbnails(
                    thumbnail = "http://books.google.com/books/content?id=GRbPMeXsKGoC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
                )
            )
        ),
        onSendButtonClicked = {}
    )
}