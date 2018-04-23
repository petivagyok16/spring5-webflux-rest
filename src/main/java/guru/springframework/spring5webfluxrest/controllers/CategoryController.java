package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
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

}
