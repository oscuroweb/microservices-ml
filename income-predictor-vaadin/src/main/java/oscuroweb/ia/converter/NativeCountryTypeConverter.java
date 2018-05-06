package oscuroweb.ia.converter;

import java.util.Arrays;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;

import oscuroweb.ia.type.NativeCountryType;

public class NativeCountryTypeConverter implements Converter<NativeCountryType, String> {
	
	private static final long serialVersionUID = 6917679106652542941L;


	@Override
	public Result<String> convertToModel(NativeCountryType value, ValueContext context) {
		return Result.ok(value.desc());
	}

	@Override
	public NativeCountryType convertToPresentation(String value, ValueContext context) {
		return Arrays.asList(NativeCountryType.values()).stream()
				.filter(item -> item.desc().equals(value))
				.findFirst()
				.orElse(null);
	}
}