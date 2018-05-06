package oscuroweb.ia.converter;

import java.util.Arrays;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;

import oscuroweb.ia.type.WorkClassType;

public class WorkClassTypeConverter implements Converter<WorkClassType, String> {
	
	private static final long serialVersionUID = 6917679106652542941L;


	@Override
	public Result<String> convertToModel(WorkClassType value, ValueContext context) {
		return Result.ok(value.desc());
	}

	@Override
	public WorkClassType convertToPresentation(String value, ValueContext context) {
		return Arrays.asList(WorkClassType.values()).stream()
				.filter(item -> item.desc().equals(value))
				.findFirst()
				.orElse(null);
	}
}