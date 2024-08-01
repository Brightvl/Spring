package brightvl.spring.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users_roles")
public class UserRole {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id; // primary key

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "role_name", nullable = false)
  private String roleName;

}
