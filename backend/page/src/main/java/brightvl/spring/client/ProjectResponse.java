package brightvl.spring.client;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ProjectResponse {

    private Long id;
    private String name;
    private Set<EmployeeResponse> employees = new HashSet<>();
}
