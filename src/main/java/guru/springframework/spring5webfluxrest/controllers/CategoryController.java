package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CategoryController {

	private final CategoryRepository categoryRepository;

	@Autowired
	public CategoryController(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@GetMapping("/api/v1/categories")
	public Flux<Category> list() {
		return this.categoryRepository.findAll();
	}

	@GetMapping("/api/v1/categories/{id}")
	public Mono<Category> getById(@PathVariable String id) {
		return this.categoryRepository.findById(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/api/v1/categories")
	public Mono<Void> create(@RequestBody Publisher<Category> categoryStream) {
		return this.categoryRepository.saveAll(categoryStream).then();
	}

	@PutMapping("/api/v1/categories/{id}")
	public Mono<Category> update(@PathVariable String id, @RequestBody Category category) {
		category.setId(id);
		return this.categoryRepository.save(category);
	}

	@PatchMapping("/api/v1/categories/{id}")
	Mono<Category> patch(@PathVariable String id, @RequestBody Category category) {

		Category foundCategory = categoryRepository.findById(id).block();

		if (foundCategory.getDescription() != category.getDescription()){
			foundCategory.setDescription(category.getDescription());
			return categoryRepository.save(foundCategory);
		}

		return Mono.just(foundCategory);
	}

}
