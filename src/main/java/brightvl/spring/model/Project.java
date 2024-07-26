package brightvl.spring.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Модель данных для проекта.
 */
@Getter
@Setter
@Entity // Указывает, что класс является сущностью JPA
@Table(name = "project")
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
    @Column(name = "name", nullable = false) // Связь с колонкой в таблице, имя колонки и ограничение на значение (не может быть null)
    private String name;
}