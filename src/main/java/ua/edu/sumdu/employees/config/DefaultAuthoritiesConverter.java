package ua.edu.sumdu.employees.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.edu.sumdu.employees.model.user.DefaultAuthorities;

import java.util.Optional;

@ConfigurationPropertiesBinding
@Component
public class DefaultAuthoritiesConverter implements Converter<String, DefaultAuthorities> {
    @Override
    public DefaultAuthorities convert(String source) {
        return Optional.of(DefaultAuthorities.valueOf(source)).get();
    }
}
