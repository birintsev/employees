package ua.edu.sumdu.employees.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.employees.config.EmployeesProperties;
import ua.edu.sumdu.employees.model.user.ResetPasswordToken;
import ua.edu.sumdu.employees.model.user.User;
import ua.edu.sumdu.employees.repository.ResetPasswordTokenRepository;

import java.sql.Date;

@Service
@AllArgsConstructor
public class ResetPasswordTokenServiceImpl implements ResetPasswordTokenService {
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    private final TokenGenerator tokenGenerator;
    private final EmployeesProperties employeesProperties;

    @Override
    public ResetPasswordToken createResetPasswordTokenFor(User user)
        throws UnsupportedOperationException {
        // todo rewrite in SQL using merge
        /*String query = ... ;*/
        ResetPasswordToken resetPasswordToken = getFor(user);
        if (resetPasswordToken != null) {
            resetPasswordToken.setToken(tokenGenerator.generate());
            resetPasswordToken.setExpirationDate(
                new Date(
                    System.currentTimeMillis()
                    + (employeesProperties.getResetPasswordTokenDuration().getNano() / 1000000)
                )
            );
        } else {
            resetPasswordToken = new ResetPasswordToken(
                new ResetPasswordToken.ResetPasswordTokenID(user),
                tokenGenerator.generate(),
                new Date(
                    System.currentTimeMillis()
                    + (employeesProperties.getResetPasswordTokenDuration().getNano() / 1000000)
                )
            );
        }
        return resetPasswordTokenRepository.saveAndFlush(resetPasswordToken);
    }

    @Override
    public ResetPasswordToken getFor(User user) {
        return resetPasswordTokenRepository.findById(
                new ResetPasswordToken.ResetPasswordTokenID(user)
        ).orElse(null);
    }
}
