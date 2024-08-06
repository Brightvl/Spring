package brightvl.spring.client;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
public class EmployeeResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private Set<ProjectResponse> projects = new HashSet<>();
}
