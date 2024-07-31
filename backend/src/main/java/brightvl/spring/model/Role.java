package brightvl.spring.model;

import lombok.Getter;

@Getter
public enum Role {

  ADMIN("admin"), USER("user");

  private final String name;

  Role(String name) {
    this.name = name;
  }

}
