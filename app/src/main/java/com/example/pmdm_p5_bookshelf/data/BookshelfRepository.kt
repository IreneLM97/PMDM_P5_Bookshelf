package com.example.pmdm_p5_bookshelf.data

import com.example.pmdm_p5_bookshelf.model.Book
import com.example.pmdm_p5_bookshelf.network.BookshelfApiService

/**
 * Interfaz que define las operaciones disponibles para acceder a la lista de libros.
 */
interface BookshelfRepository {
    /**
     * Obtiene una lista de libros según la consulta proporcionada.
     *
     * @param query Consulta para buscar libros.
     * @return Lista de libros si la solicitud es exitosa, o null si hay algún error.
     */
    suspend fun getBooks(query: String): List<Book>?
}

/**
 * Implementación predeterminada de BookshelfRepository.
 *
 * @param bookshelfApiService Servicio utilizado para la comunicación con la API de la biblioteca de libros.
 */
class DefaultBookshelfRepository(
    private val bookshelfApiService: BookshelfApiService
) : BookshelfRepository {
    /**
     * Implementación de la función getBooks que se define en la interfaz BookshelfRepository.
     */
    override suspend fun getBooks(query: String): List<Book>? {
        return try {
            // Llamada al servicio bookshelfApiService para obtener libros
            val response = bookshelfApiService.getBooks(query)

            // Respuesta exitosa
            if (response.isSuccessful) {
                // Obtenemos cuerpo de la respuesta y devolvemos la lista de libros (si existe) o lista vacía
                response.body()?.books ?: emptyList()
            // Respuesta no exitosa
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}