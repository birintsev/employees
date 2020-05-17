package ua.edu.sumdu.employees.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityID implements Serializable {
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
