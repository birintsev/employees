package ua.edu.sumdu.employees.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ua.edu.sumdu.employees.model.*;
import ua.edu.sumdu.employees.repository.AuthorityRepository;
import ua.edu.sumdu.employees.repository.UserDetailsRepository;

import java.sql.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;

@EnableCaching
@SpringBootApplication(scanBasePackages = "ua.edu.sumdu.employees")
@EntityScan(basePackages={"ua.edu.sumdu.employees.model"})
@EnableJpaRepositories(basePackages={"ua.edu.sumdu.employees.repository"})
@Configuration
@EnableWebSecurity
@EnableSpringDataWebSupport
public class EmployeesApplication extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    public void addFormatters(
            FormatterRegistry registry,
            PasswordEncoder passwordEncoder,
            @Value("${employees.defaults.user.isactive}") Boolean isActive,
            @Value("${employees.defaults.user.defaultauthority}") String defaultAuthority) {
        registry.addConverter(UserDTO.class, User.class, source -> {
            User user = new User(source.getUsername(), passwordEncoder.encode(source.getPassword()), Optional.ofNullable(isActive).orElse(true), new HashSet<>());
            Authority authority = new Authority(new AuthorityID());
            authority.setUser(user);
            authority.setAuthority(
                    Optional.ofNullable(defaultAuthority).orElse(DefaultAuthorities.RO_USER.getAuthority())
            );
            user.addAuthority(authority);
            return user;
        });

        registry.addFormatterForFieldType(Date.class, new Formatter<Date>() {
            @Override
            public Date parse(String text, Locale locale) {
                return Date.valueOf(text);
            }

            @Override
            public String print(Date date, Locale locale) {
                return date.toString();
            }
        });
    }

    // todo fix errors on startup: stacktrace:
    //org.hibernate.tool.schema.spi.CommandAcceptanceException: Error executing DDL "alter table emp add constraint FKfehivfm7m674r8qrrnug1of2q foreign key (mgr) references emp" via JDBC Statement
    //...
    //Caused by: oracle.jdbc.OracleDatabaseException: ORA-02275: такое ссылочное ограничение уже есть в таблице
    public static void main(String[] args) {
        SpringApplication.run(EmployeesApplication.class, args);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/employees")
                .hasAnyAuthority(
                    DefaultAuthorities.ADMINISTRATOR.toString(),
                    DefaultAuthorities.RO_USER.toString()
                ).and()
                .formLogin()
                /*.loginPage("/login")*/.permitAll()
            .and()
                .logout()
                .permitAll()
        .and().httpBasic();
        http
            .authorizeRequests()
            .antMatchers("/registration")
            .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // default admin account creation
    @Autowired
    public void createDefaultUser(
            @Value(value = "${employees.root.username}") String password,
            @Value(value = "${employees.root.password}") String username,
            /*@Qualifier(value = "UserDetailsService")*/ UserDetailsRepository userDetailsRepository,
            AuthorityRepository authorityRepository,
            PasswordEncoder passwordEncoder) {
        User user = new User(username, passwordEncoder.encode(password), true, new HashSet<>());
        Authority authority = new Authority(new AuthorityID());

        authority.setUser(user);
        authority.setAuthority(DefaultAuthorities.ADMINISTRATOR.getAuthority());
        user.addAuthority(authority);
        if (!userDetailsRepository.existsById(username)) {
            userDetailsRepository.save(user);
        }
        if (!authorityRepository.existsById(authority.getAuthorityID())) {
            authorityRepository.save(authority);
        }
    }

    @Autowired
    public void configureGlobal(
            AuthenticationManagerBuilder builder,
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) throws Exception {
        builder
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);
    }
}
