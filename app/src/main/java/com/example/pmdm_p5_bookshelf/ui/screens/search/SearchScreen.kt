package com.example.pmdm_p5_bookshelf.ui.screens.search

import android.view.KeyEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pmdm_p5_bookshelf.R
import com.example.pmdm_p5_bookshelf.model.Book
import com.example.pmdm_p5_bookshelf.ui.BookshelfViewModel
import com.example.pmdm_p5_bookshelf.ui.theme.PMDM_P5_BookshelfTheme

/**
 * Muestra la pantalla de búsqueda que permite al usuario buscar libros y ver los resultados.
 *
 * @param viewModel ViewModel que maneja la lógica de la pantalla de búsqueda de libros.
 * @param onBookClick Función lambda para manejar la acción de hacer click en un libro.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: BookshelfViewModel,
    onBookClick: (Book) -> Unit
) {
    // Diseño de la estructura básica de la pantalla
    Scaffold(
        topBar = {
            // Barra superior personalizada
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.bookshelf))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorResource(R.color.my_dark_blue)
                )
            )
        }
    ) { innerPadding ->
        // Diseño de la pantalla de búsqueda
        when (val searchUiState = viewModel.searchUiState.collectAsStateWithLifecycle().value) {
            SearchUiState.Error -> ErrorScreen(
                retryAction = { viewModel.getBooks() }
            )
            SearchUiState.Loading -> LoadingScreen()
            is SearchUiState.Success -> LazyGridScreen(
                books = searchUiState.books,
                onBooksSearch = viewModel::getBooks,
                onBookClick = onBookClick,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

/**
 * Muestra una pantalla de carga con un indicador de progreso mientras se espera la carga de datos.
 */
@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

/**
 * Muestra una pantalla de error con la opción de volver a intentarlo.
 *
 * @param retryAction Acción a realizar al hacer click en el botón de reintentar.
 */
@Composable
fun ErrorScreen(
    retryAction: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen de sin conexión
            Image(
                painter = painterResource(id = R.drawable.ic_fail_conection),
                contentDescription = stringResource(R.string.connection_error)
            )
            // Icono de reintentar
            IconButton(
                onClick = retryAction
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = stringResource(R.string.refresh),
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}

/**
 * Muestra una pantalla con una cuadrícula perezosa que contiene una lista de libros.
 *
 * @param books Lista de libros a mostrar en la cuadrícula.
 * @param onBooksSearch Función lambda para realizar una búsqueda de libros.
 * @param onBookClick Función lambda para manejar el click en un libro.
 * @param modifier Modificador para personalizar la apariencia.
 */
@Composable
fun LazyGridScreen(
    books: List<Book>,
    onBooksSearch: (String) -> Unit,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    // Estado para el texto de búsqueda
    var query by rememberSaveable { mutableStateOf("") }

    // Columna principal que contiene los elementos de la pantalla
    Column(
        modifier = modifier
    ) {
        // Campo de texto para ingresar la consulta de búsqueda
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            singleLine = true,
            placeholder = {
                Text(text = stringResource(R.string.search_label))
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                // Maneja evento al pulsar el icono Search del teclado
                onSearch = {
                    focusManager.clearFocus() // Oculta el teclado
                    onBooksSearch(query) // Realiza la búsqueda con la consulta ingresada
                }
            ),
            modifier = Modifier
                .onKeyEvent { e ->
                    // Maneja el evento al presionar la tecla "Enter" en el teclado
                    if (e.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                        focusManager.clearFocus() // Oculta el teclado
                        onBooksSearch(query) // Realiza la búsqueda con la consulta ingresada
                    }
                    false
                }
                .fillMaxWidth()
                .padding(start = 8.dp, end = 15.dp, top = 8.dp)
        )

        // Verifica si la lista de libros está vacía
        if (books.isEmpty()) {
            // Muestra un mensaje cuando no hay resultados de búsqueda
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(R.string.no_results))
            }
        } else {
            // Muestra la cuadrícula de libros cuando hay resultados de búsqueda
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(4.dp)
            ) {
                items(
                    items = books
                ) { book ->
                    BookCard(
                        book = book,
                        onBookClick = onBookClick
                    )
                }
            }
        }
    }
}

/**
 * Muestra una tarjeta que representa un libro del resultado de la búsqueda.
 *
 * @param book Información del libro a mostrar en la tarjeta.
 * @param onBookClick Función lambda para manejar el click en el libro.
 * @param modifier Modificador para personalizar la apariencia.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookCard(
    book: Book,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    // Tarjeta que representa un libro
    Card(
        onClick = {
            onBookClick(book)
        },
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(0.7f),
    ) {
        // Imagen asíncrona del libro dentro de la tarjeta
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.ic_loading_img),
            contentDescription = stringResource(R.string.book_image),
            contentScale = ContentScale.FillBounds,
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(book.volumeInfo.imageLinks?.httpsThumbnail)
                .crossfade(true)
                .build()
        )
    }
}

/**
 * Muestra una vista previa de la pantalla de carga.
 */
@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    PMDM_P5_BookshelfTheme {
        LoadingScreen()
    }
}

/**
 * Muestra una vista previa de la pantalla de error.
 */
@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    PMDM_P5_BookshelfTheme {
        ErrorScreen()
    }
}
