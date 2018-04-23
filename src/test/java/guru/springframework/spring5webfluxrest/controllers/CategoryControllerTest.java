package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class CategoryControllerTest {

	WebTestClient webTestClient;
	CategoryRepository categoryRepository;
	CategoryController categoryController;

	@Before
	public void setUp() throws Exception {
		this.categoryRepository = Mockito.mock(CategoryRepository.class);
		this.categoryController = new CategoryController(this.categoryRepository);
		this.webTestClient = WebTestClient.bindToController(this.categoryController).build();
	}

	@Test
	public void list() {
		BDDMockito.given(this.categoryRepository.findAll())
						.willReturn(Flux.just(Category.builder().description("Cat1").build(),
										Category.builder().description("Cat2").build()));

		this.webTestClient.get()
						.uri("/api/v1/categories")
						.exchange()
						.expectBodyList(Category.class)
						.hasSize(2);
	}

	@Test
	public void getById() {
		BDDMockito.given(this.categoryRepository.findById("someId"))
						.willReturn(Mono.just(Category.builder().description("Cat").build()));

		this.webTestClient.get()
						.uri("/api/v1/categories/someId")
						.exchange()
						.expectBody(Category.class);
	}
}