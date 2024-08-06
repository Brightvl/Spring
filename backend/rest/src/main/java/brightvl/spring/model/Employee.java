package brightvl.spring.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table
public class Employee {
    /*
    Когда используется @Data, Lombok генерирует методы equals и hashCode для класса.
    По умолчанию, Lombok включает в эти методы все поля класса.
    Однако, иногда нужно явно указать, какие поля следует учитывать при сравнении объектов и вычислении их хэш-кодов.

    @EqualsAndHashCode.Include. В данном случае, эта аннотация указывает, что поле id должно быть включено
    в логику методов equals и hashCode.*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;

    /**
     * Проекты сотрудника
     */
    @ManyToMany(mappedBy = "employees")
    private Set<Project> projects = new HashSet<>();
}
