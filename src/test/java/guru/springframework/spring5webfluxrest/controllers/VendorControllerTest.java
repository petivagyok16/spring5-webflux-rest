package guru.springframework.spring5webfluxrest.controllers;

import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;

public class VendorControllerTest {

	WebTestClient webTestClient;
	VendorRepository vendorRepository;
	VendorController vendorController;

	@Before
	public void setUp() throws Exception {
		this.vendorRepository = Mockito.mock(VendorRepository.class);
		this.vendorController = new VendorController(this.vendorRepository);
		this.webTestClient = WebTestClient.bindToController(this.vendorController).build();
	}

	@Test
	public void list() {
		BDDMockito.given(this.vendorController.list())
						.willReturn(
										Flux.just(Vendor.builder().id("someId").firstName("firstName").lastName("lastName").build(),
															Vendor.builder().id("someId2").firstName("firstName2").lastName("lastName2").build()));

		this.webTestClient.get()
						.uri("/api/v1/vendors")
						.exchange()
						.expectBodyList(Vendor.class)
						.hasSize(2);
	}

	@Test
	public void findById() {
		BDDMockito.given(this.vendorController.findById("someId"))
						.willReturn(Mono.just(Vendor.builder().id("someId").firstName("firstName").lastName("lastName").build()));

		this.webTestClient.get()
						.uri("/api/v1/vendors/someId")
						.exchange()
						.expectBody(Vendor.class);
	}
}