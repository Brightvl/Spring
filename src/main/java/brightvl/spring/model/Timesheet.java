package brightvl.spring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "timesheet")
public class Timesheet {

    /**
     * Идентификатор табеля.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * id проекта.
     */
    private Long projectId;
    /**
     * Количество минут, затраченных на проект.
     */
    private int minutes;
    /**
     * Дата создания табеля.
     */
    private LocalDate createdAt;

}