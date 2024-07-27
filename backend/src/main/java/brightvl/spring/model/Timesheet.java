// src/main/java/brightvl/spring/model/Timesheet.java
package brightvl.spring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Модель данных для табеля учета рабочего времени.
 */
@Getter
@Setter
@Entity
@Table
public class Timesheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * id проекта.
     */
    @Column(nullable = false)
    private Long projectId; // храним id проекта к которому принадлежит заметка
    /**
     * Количество минут, затраченных на проект.
     */
    private int minutes;

    // создаст create_at
    /**
     * Дата создания табеля.
     */
    private LocalDate createdAt;

    /**
     * Работник создавший заметку
     */
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
