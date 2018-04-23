package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


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

	@Test
	public void testCreateCategory() {
		BDDMockito.given(this.categoryRepository.saveAll(any(Publisher.class)))
						.willReturn(Flux.just(Category.builder().description("asdf").build()));

		Mono<Category> catToSaveMono = Mono.just(Category.builder().description("Some cat").build());

		this.webTestClient.post()
						.uri("/api/v1/categories")
						.body(catToSaveMono, Category.class)
						.exchange()
						.expectStatus()
						.isCreated();
	}

	@Test
	public void testUpdate() {
		given(categoryRepository.save(any(Category.class)))
						.willReturn(Mono.just(Category.builder().build()));

		Mono<Category> catToUpdateMono = Mono.just(Category.builder().description("Some Cat").build());

		webTestClient.put()
						.uri("/api/v1/categories/asdfasdf")
						.body(catToUpdateMono, Category.class)
						.exchange()
						.expectStatus()
						.isOk();
	}

	@Test
	public void testPatchWithChanges() {
		given(categoryRepository.findById(anyString()))
						.willReturn(Mono.just(Category.builder().build()));

		given(categoryRepository.save(any(Category.class)))
						.willReturn(Mono.just(Category.builder().build()));

		Mono<Category> catToUpdateMono = Mono.just(Category.builder().description("New Description").build());

		webTestClient.patch()
						.uri("/api/v1/categories/asdfasdf")
						.body(catToUpdateMono, Category.class)
						.exchange()
						.expectStatus()
						.isOk();

		verify(categoryRepository).save(any());
	}

	@Test
	public void testPatchNoChanges() {
		given(categoryRepository.findById(anyString()))
						.willReturn(Mono.just(Category.builder().build()));

		given(categoryRepository.save(any(Category.class)))
						.willReturn(Mono.just(Category.builder().build()));

		Mono<Category> catToUpdateMono = Mono.just(Category.builder().build());

		webTestClient.patch()
						.uri("/api/v1/categories/asdfasdf")
						.body(catToUpdateMono, Category.class)
						.exchange()
						.expectStatus()
						.isOk();

		verify(categoryRepository, never()).save(any());
	}
}