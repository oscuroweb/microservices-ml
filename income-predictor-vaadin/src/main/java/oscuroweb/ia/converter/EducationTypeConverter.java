package oscuroweb.ia.converter;

import java.util.Arrays;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;

import oscuroweb.ia.type.EducationType;

public class EducationTypeConverter implements Converter<EducationType, String> {
	
	private static final long serialVersionUID = 6917679106652542941L;


	@Override
	public Result<String> convertToModel(EducationType value, ValueContext context) {
		return Result.ok(value.desc());
	}

	@Override
	public EducationType convertToPresentation(String value, ValueContext context) {
		return Arrays.asList(EducationType.values()).stream()
				.filter(item -> item.desc().equals(value))
				.findFirst()
				.orElse(null);
	}
}