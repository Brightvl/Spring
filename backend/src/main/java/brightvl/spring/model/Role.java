package brightvl.spring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @ManyToMany(mappedBy = "roles")
  private Set<User> users;
}

//@Getter
//public enum Role {
//
//  ADMIN("admin"), USER("user");
//
//  private final String name;
//
//  Role(String name) {
//    this.name = name;
//  }
//
//}
