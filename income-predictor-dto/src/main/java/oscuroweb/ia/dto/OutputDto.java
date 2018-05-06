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
public class OutputDto implements Serializable {
	
	private static final long serialVersionUID = -6564165486219991295L;
	
	/** label: >50K, <=50K. */
	private String label;
}
