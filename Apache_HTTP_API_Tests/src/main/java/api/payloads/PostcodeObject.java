package api.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(fluent = true)
public class PostcodeObject {

	@JsonProperty("name")
	private String name;

	@JsonProperty("job")
	private String job;
}