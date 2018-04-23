package guru.springframework.spring5webfluxrest.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Vendor {

	@Id
	public String id;

	public String firstName;
	public String lastName;
}
