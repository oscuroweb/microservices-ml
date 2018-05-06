package oscuroweb.ia.service;

import oscuroweb.ia.dto.IncomeDto;
import oscuroweb.ia.dto.OutputDto;

public interface SparkService {
	
	public OutputDto evaluate(IncomeDto input);
}
