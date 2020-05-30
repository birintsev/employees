package ua.edu.sumdu.employees.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class NewPasswordDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String resetPasswordToken;
    @NotBlank
    private String newPassword;
    @NotBlank
    private String newPasswordConfirmation;
}
