package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/api/v1/vendors")
	Mono<Void> create(@RequestBody Publisher<Vendor> vendorStream) {
		return vendorRepository.saveAll(vendorStream).then();
	}

	@PutMapping("api/v1/vendors/{id}")
	Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor){
		vendor.setId(id);
		return vendorRepository.save(vendor);
	}

	@PatchMapping("api/v1/vendors/{id}")
	Mono<Vendor> patch(@PathVariable String id, @RequestBody Vendor vendor){

		Vendor foundVendor = vendorRepository.findById(id).block();

		if (!foundVendor.getFirstName().equals(vendor.getFirstName())){
			foundVendor.setFirstName(vendor.getFirstName());

			return vendorRepository.save(foundVendor);
		}
		return Mono.just(foundVendor);
	}

}
