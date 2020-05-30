package ua.edu.sumdu.employees.model.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "Authority")
@Table(
    name = "AUTHORITIES",
    uniqueConstraints = {
        @UniqueConstraint(name = "AUTH_USERNAME", columnNames = {"USERNAME", "AUTHORITY"})
    }
)
public class Authority implements GrantedAuthority {
    @EmbeddedId
    private AuthorityID authorityID;

    /*@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")
    @Id
    private User username;
    @Column(length = 50, nullable = false)
    @Id
    private String authority;*/

    @Override
    public String getAuthority() {
        return this.authorityID.getAuthority();
    }

    public void setAuthority(String authority) {
        this.authorityID.setAuthority(authority);
    }

    public User getUser() {
        return this.authorityID.getUser();
    }

    public void setUser(User user) {
        this.authorityID.setUser(user);
    }

    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AuthorityID implements Serializable {
        @ManyToOne
        @JoinColumn(name = "USERNAME")
        private User user;
        @Column(length = 50, nullable = false)
        private String authority;

        @Override
        public int hashCode() {
            return Objects.hash(user.getUsername(), authority);
        }

        @Override
        public boolean equals(Object obj) {
            return
                obj != null
                && obj.getClass() == AuthorityID.class
                && user.getUsername().equals(((AuthorityID)obj).getUser().getUsername())
                && authority.equals(((AuthorityID)obj).getAuthority());
        }

        @Transactional(readOnly = true)
        public User getUser() {
            return user;
        }
    }
}
