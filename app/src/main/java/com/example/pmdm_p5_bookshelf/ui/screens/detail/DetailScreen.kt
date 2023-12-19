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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pmdm_p5_bookshelf.R
import com.example.pmdm_p5_bookshelf.model.Book
import com.example.pmdm_p5_bookshelf.ui.BookshelfViewModel

/**
 * Muestra la pantalla de detalle que presenta la información de un libro seleccionado.
 *
 * @param viewModel ViewModel que maneja la lógica de la pantalla de detalles del libro.
 * @param onBackPressed Función lambda para manejar el evento de retroceder a la pantalla anterior.
 * @param onSendButtonClicked Función lambda para manejar el evento de enviar información del libro a otra aplicación.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: BookshelfViewModel,
    onBackPressed: () -> Unit,
    onSendButtonClicked: (String) -> Unit
) {
    // Estado actual de la interfaz de usuario para los detalles del libro desde el ViewModel
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
        // Diseño de la pantalla de detalles
        detailUiState.currentBook?.let {
            BookDetails(
                book = it,
                onSendButtonClicked = onSendButtonClicked,
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

/**
 * Muestra los detalles de un libro en la interfaz de usuario.
 *
 * @param book Información del libro a mostrar.
 * @param onSendButtonClicked Función lambda para manejar el evento de enviar información del libro a otra aplicación.
 * @param modifier Modificador para personalizar la apariencia.
 */
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

    // Estructura para mostrar los detalles del libro
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
            // Sección de la imagen del libro
            Spacer(Modifier.height(10.dp))
            ImageSection(book = book)

            // Sección del título del libro
            Spacer(modifier = Modifier.height(24.dp))
            TextSection(
                text = book.volumeInfo.title,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.my_dark_gray)
                ),
                fontStyle = FontStyle.Italic
            )

            // Sección de los autores del libro
            Spacer(modifier = Modifier.height(24.dp))
            TextSection(
                text = stringResource(
                    id = R.string.book_authors,
                    book.volumeInfo.bookAuthors
                ),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.my_dark_gray)
                )
            )

            // Sección de la fecha de publicación del libro
            Spacer(modifier = Modifier.height(24.dp))
            TextSection(
                text = stringResource(
                    id = R.string.book_published,
                    book.volumeInfo.bookPublished
                ),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.my_dark_gray)
                )
            )

            // Linea horizontal
            Spacer(modifier = Modifier.height(24.dp))
            Line()

            // Sección de la descripción del libro
            Spacer(modifier = Modifier.height(24.dp))
            TextSection(
                text = stringResource(
                    id = R.string.book_description,
                    book.volumeInfo.bookDescription
                ),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.my_dark_gray)
                ),
                fontStyle = FontStyle.Italic
            )

            // Botón para enviar a otra aplicación
            SendButton(
                onSendButtonClicked = onSendButtonClicked,
                bookSummary = bookSummary
            )
        }
    }
}

/**
 * Muestra la sección de la imagen del libro.
 *
 * @param book Información del libro que contiene la URL de la imagen.
 */
@Composable
private fun ImageSection(
    book: Book
) {
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(50.dp)),
        contentDescription = stringResource(id = R.string.book_image),
        contentScale = ContentScale.FillWidth,
        error = painterResource(id = R.drawable.ic_broken_image),
        placeholder = painterResource(id = R.drawable.ic_loading_img),
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(book.volumeInfo.imageLinks?.httpsThumbnail)
            .crossfade(true)
            .build()
    )
}

/**
 * Muestra una sección de texto con estilo y formato opcionales.
 *
 * @param text Texto a mostrar en la sección.
 * @param style Estilo del texto a aplicar.
 * @param fontStyle Estilo de fuente adicional, si es necesario.
 */
@Composable
private fun TextSection(
    text: String,
    style: TextStyle,
    fontStyle: FontStyle? = null
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            text = text,
            style = style,
            fontStyle = fontStyle,
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_small))
        )
    }
}

/**
 * Muestra un botón para enviar información a otra aplicación.
 *
 * @param onSendButtonClicked Función lambda que maneja el evento de click del botón.
 * @param bookSummary Resumen de información del libro para enviar.
 */
@Composable
private fun SendButton(
    onSendButtonClicked: (String) -> Unit,
    bookSummary: String
) {
    Row(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_big))
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onSendButtonClicked(bookSummary) },
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.my_dark_blue)),
        ) {
            Text(stringResource(R.string.send_button))
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