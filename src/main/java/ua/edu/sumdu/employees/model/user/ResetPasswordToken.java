package ua.edu.sumdu.employees.model.user;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(
    name = "RESET_PASSWORD_TOKENS",
    uniqueConstraints = {
        @UniqueConstraint(
            name= "UNIQUE_RECORD",
            columnNames = {"USERNAME", "EXPIRATION_DATE", "TOKEN"})
    }
)
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordToken implements Serializable {
    @EmbeddedId
    private ResetPasswordTokenID resetPasswordTokenID;
    @Column(name = "TOKEN")
    private String token;
    @Column(name = "EXPIRATION_DATE", nullable = false)
    private Date expirationDate;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResetPasswordTokenID implements Serializable {
        @OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")
        private User user;

        @Override
        public int hashCode() {
            return user.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return (obj instanceof ResetPasswordTokenID) && user.equals(((ResetPasswordTokenID) obj).getUser());
        }

        @Override
        public String toString() {
            return user.toString();
        }
    }
}
