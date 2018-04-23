package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VendorController {

	public final VendorRepository vendorRepository;

	@Autowired
	public VendorController(VendorRepository vendorRepository) {
		this.vendorRepository = vendorRepository;
	}

	@GetMapping("/api/v1/vendors")
	public Flux<Vendor> list() {
		return this.vendorRepository.findAll();
	}

	@GetMapping("/api/v1/vendors/{id}")
	public Mono<Vendor> findById(@PathVariable String id) {
		return this.vendorRepository.findById(id);
	}

}
