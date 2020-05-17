package ua.edu.sumdu.employees.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

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
}
