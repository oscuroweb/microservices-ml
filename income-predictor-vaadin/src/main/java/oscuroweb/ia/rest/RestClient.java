package oscuroweb.ia.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import oscuroweb.ia.config.AppConfiguration;
import oscuroweb.ia.dto.InputDto;
import oscuroweb.ia.dto.InputResultDto;
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

	public OutputDto evaluateData(Double age, String workclass, Double fnlwgt, String education, Double educationNum,
									String maritalStatus, String occupation, String relationship, String race, String sex,
									Double capitalGain, Double capitalLoss, Double hoursPerWeek, String nativeCountry) {
		log.info("Inicio getHistoricData");
		InputDto inputDto = InputDto.builder()
								.age(age)
								.workclass(workclass)
								.fnlwgt(fnlwgt)
								.education(education)
								.educationNum(educationNum)
								.maritalStatus(maritalStatus)
								.occupation(occupation)
								.relationship(relationship)
								.race(race)
								.sex(sex)
								.capitalGain(capitalGain)
								.capitalLoss(capitalLoss)
								.hoursPerWeek(hoursPerWeek)
								.nativeCountry(nativeCountry)
								.build();

		OutputDto mensaje = config.restTemplate().postForObject(WS_URI_SRV_EVALUATE, inputDto, OutputDto.class);
		return mensaje;
	}


	public OutputResultDto addResult(InputResultDto inputDto) {
		OutputResultDto mensaje =
				config.restTemplate().postForObject(WS_URI_SRV_ADDRESULT, inputDto, OutputResultDto.class);
		return mensaje;
	}
}
