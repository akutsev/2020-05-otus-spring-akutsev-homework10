package ru.otus.akutsev.books.controllertests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.akutsev.books.controller.BooksRestController;
import ru.otus.akutsev.books.model.Author;
import ru.otus.akutsev.books.model.Book;
import ru.otus.akutsev.books.model.Genre;
import ru.otus.akutsev.books.service.BookService;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BooksRestController.class)
public class BookRestControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;

	@Test
	public void getAllBooksTest() throws Exception {
		String bookName1 = "Crime and punishment";
		String bookName2 = "Idiot";

		Book book1 = new Book(1, bookName1, new Author(), new Genre());
		Book book2 = new Book(2, bookName2, new Author(), new Genre());

		given(bookService.getAll()).willReturn(List.of(book1, book2));

		mockMvc.perform(get("/api/books")
		.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$.[0].name", is("Crime and punishment")))
		.andExpect(jsonPath("$.[1].name", is("Idiot")));
	}

}
