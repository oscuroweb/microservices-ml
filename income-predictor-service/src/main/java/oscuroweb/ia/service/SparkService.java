package oscuroweb.ia.service;

import oscuroweb.ia.dto.InputDto;
import oscuroweb.ia.dto.OutputDto;

public interface SparkService {
	
	public OutputDto evaluate(InputDto input);
}
