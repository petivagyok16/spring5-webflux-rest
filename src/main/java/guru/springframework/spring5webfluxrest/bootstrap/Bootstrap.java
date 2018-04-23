package guru.springframework.spring5webfluxrest.bootstrap;

import guru.springframework.spring5webfluxrest.domain.Category;
import guru.springframework.spring5webfluxrest.domain.Vendor;
import guru.springframework.spring5webfluxrest.repositories.CategoryRepository;
import guru.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

	private final CategoryRepository categoryRepository;
	private final VendorRepository vendorRepository;

	public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
		this.categoryRepository = categoryRepository;
		this.vendorRepository = vendorRepository;
	}

	@Override
	public void run(String... args) throws Exception {

		if (this.categoryRepository.count().block() == 0) {
			// Load data
			System.out.println("#### Loading Data on Bootstrap ####");

			this.categoryRepository.save(Category.builder()
			.description("Fruits").build()).block();

			this.categoryRepository.save(Category.builder()
							.description("Nuts").build()).block();

			this.categoryRepository.save(Category.builder()
							.description("Breads").build()).block();


			this.categoryRepository.save(Category.builder()
							.description("Meats").build()).block();

			this.categoryRepository.save(Category.builder()
							.description("Eggs").build()).block();

			System.out.println("Loaded Categories: " + this.categoryRepository.count().block());

			this.vendorRepository.save(Vendor.builder()
							.firstName("Joe")
							.lastName("Buck").build()).block();

			this.vendorRepository.save(Vendor.builder()
							.firstName("Michael")
							.lastName("Weston").build()).block();

			this.vendorRepository.save(Vendor.builder()
							.firstName("Jessie")
							.lastName("Waters").build()).block();

			this.vendorRepository.save(Vendor.builder()
							.firstName("Bill")
							.lastName("Nershi").build()).block();

			this.vendorRepository.save(Vendor.builder()
							.firstName("Jimmy")
							.lastName("Buffett").build()).block();

			System.out.println("Loaded Vendors: " + this.vendorRepository.count().block());
		}
	}
}
