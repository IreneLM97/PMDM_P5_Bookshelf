package com.example.pmdm_p5_bookshelf.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pmdm_p5_bookshelf.R
import com.example.pmdm_p5_bookshelf.model.Book
import com.example.pmdm_p5_bookshelf.model.Thumbnails
import com.example.pmdm_p5_bookshelf.model.VolumeInfo
import com.example.pmdm_p5_bookshelf.ui.theme.PMDM_P5_BookshelfTheme

@Composable
fun HomeScreen(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (homeUiState) {
        is HomeUiState.Loading -> LoadingScreen(modifier.size(200.dp))
        is HomeUiState.Success ->
            BooksListScreen(
                books = homeUiState.books,
                modifier = modifier
                    .padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        top = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_medium)
                    ),
                contentPadding = contentPadding
            )

        else -> ErrorScreen(retryAction, modifier)
    }
}


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading),
        modifier = modifier
    )
}

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.loading_failed))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun BookCard(
    book: Book,
    modifier: Modifier = Modifier
) {
    // Variable que controla el estado de expansión/cierre de la información del libro
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.book_title, book.volumeInfo.title),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.padding_medium)),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )

            // Muestra botón de expansión/contracción
            BookItemButton(
                expanded = expanded,
                onClick = {
                    expanded = !expanded
                },
            )

            // Si el libro está expandido, se muestra su información expandida
            if (expanded) {
                BookInfo(book = book)
            }

            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(book.volumeInfo.imageLinks?.httpsThumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img)
            )
        }
    }
}

@Composable
fun BookInfo(
    book: Book,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(
            R.string.book_authors,
            book.volumeInfo.allAuthors
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            .padding(bottom = dimensionResource(R.dimen.padding_small)),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start
    )
    Text(
        text = stringResource(
            R.string.book_date,
            book.volumeInfo.published
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            .padding(bottom = dimensionResource(R.dimen.padding_small)),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start
    )
}

@Composable
private fun BookItemButton(
    expanded: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .padding(top = dimensionResource(id = R.dimen.padding_medium))
    ) {
        // Muestra icono de flecha
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_button_content_description),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
private fun BooksListScreen(
    books: List<Book>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(
            items = books
        ) { book ->
            BookCard(
                book = book,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    PMDM_P5_BookshelfTheme {
        LoadingScreen(
            Modifier
                .fillMaxSize()
                .size(200.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    PMDM_P5_BookshelfTheme {
        ErrorScreen({}, Modifier.fillMaxSize())
    }
}

@Preview(showBackground = true)
@Composable
fun BooksListScreenPreview() {
    PMDM_P5_BookshelfTheme {
        val mockData = List(10) {
            Book(
                volumeInfo = VolumeInfo(
                    title = "Book",
                    authors = listOf( "Juan", "Pedro" ),
                    publishedDate = "10/12/2010",
                    imageLinks = Thumbnails (thumbnail = "")
                )
            )
        }
        BooksListScreen(mockData, Modifier.fillMaxSize())
    }
}
