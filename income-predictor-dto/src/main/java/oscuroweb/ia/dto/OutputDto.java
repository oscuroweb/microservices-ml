package oscuroweb.ia.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oscuroweb.ia.dto.InputDto.InputDtoBuilder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutputDto implements Serializable {
	/** label: >50K, <=50K. */
	String label;
}
