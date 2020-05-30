package ua.edu.sumdu.employees.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ua.edu.sumdu.employees.model.user.DefaultAuthorities;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.Duration;

@ConfigurationProperties(prefix = "employees")
@Getter
@Setter
@NoArgsConstructor
public class EmployeesProperties { // todo use Credentials class
    @NotBlank
    @Length(min = 5, max = 50)
    private String rootUsername;
    @NotBlank
    @Length(min = 5, max = 50)
    private String rootPassword;
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")
    private String rootEmail;
    private Boolean defaultsUserIsActive = true;
    private DefaultAuthorities defaultsUserDefaultAuthority = DefaultAuthorities.RO_USER;
    private Duration resetPasswordTokenDuration = Duration.ofDays(1);
}
