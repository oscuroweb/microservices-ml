package oscuroweb.ia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import oscuroweb.ia.dto.InputDto;
import oscuroweb.ia.dto.InputResultDto;
import oscuroweb.ia.dto.OutputDto;
import oscuroweb.ia.dto.OutputResultDto;
import oscuroweb.ia.service.SparkService;

@RestController
public class ServicesController {
	
	@Autowired
	SparkService sparkService;

	
	@PostMapping("/evaluate")
	public @ResponseBody OutputDto evaluate(@RequestBody InputDto input) {
		return sparkService.evaluate(input);
	}
	
	@PostMapping("/addResult")
	public @ResponseBody OutputResultDto addResult(@RequestBody InputResultDto inputDto) {
		return null;
	}
}
