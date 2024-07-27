package brightvl.spring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Модель данных для проекта.
 */
@Getter
@Setter
@Entity // Указывает, что класс является сущностью JPA
public class Project {
    /**
     * Идентификатор проекта.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоматическая генерация значений для идентификатора
    private Long id;
    /**
     * Название проекта.
     */
    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "project_employee",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")

    )
    private Set<Employee> employees = new HashSet<>();

}