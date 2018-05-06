package oscuroweb.ia.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import oscuroweb.ia.config.AppConfiguration;
import oscuroweb.ia.dto.IncomeDto;
import oscuroweb.ia.dto.OutputDto;
import oscuroweb.ia.dto.OutputResultDto;

@Slf4j
@Component
public class RestClient {

	@Value("${endpoint.evaluate}")
	private String WS_URI_SRV_EVALUATE;

	@Value("${endpoint.addResult}")
	private String WS_URI_SRV_ADDRESULT;

	@Autowired
	AppConfiguration config;

	public OutputDto evaluateData(IncomeDto income) {
		log.info("Start evaluate: {}", income.toString());

		OutputDto output = config.restTemplate().postForObject(WS_URI_SRV_EVALUATE, income, OutputDto.class);
		return output;
	}


	public OutputResultDto addResult(IncomeDto incomeResult) {
		log.info("Start add result: {}", incomeResult);
		OutputResultDto output =
				config.restTemplate().postForObject(WS_URI_SRV_ADDRESULT, incomeResult, OutputResultDto.class);
		return output;
	}
}
