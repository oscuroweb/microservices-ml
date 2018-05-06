package oscuroweb.ia.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutputResultDto implements Serializable {
	
	private static final long serialVersionUID = -593481387719299431L;
	
	private Boolean updated;
}
