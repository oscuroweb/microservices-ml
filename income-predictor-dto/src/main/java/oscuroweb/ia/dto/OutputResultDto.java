package oscuroweb.ia.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import oscuroweb.ia.dto.OutputDto.OutputDtoBuilder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutputResultDto implements Serializable {
	Boolean updated;
}
