package oscuroweb.ia.converter;

import java.util.Arrays;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;

import oscuroweb.ia.type.SexType;

public class SexTypeConverter implements Converter<SexType, String> {
	
	private static final long serialVersionUID = 6917679106652542941L;


	@Override
	public Result<String> convertToModel(SexType value, ValueContext context) {
		return Result.ok(value.desc());
	}

	@Override
	public SexType convertToPresentation(String value, ValueContext context) {
		return Arrays.asList(SexType.values()).stream()
				.filter(item -> item.desc().equals(value))
				.findFirst()
				.orElse(null);
	}
}