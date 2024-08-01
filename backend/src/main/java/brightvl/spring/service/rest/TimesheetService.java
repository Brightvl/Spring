package brightvl.spring.service.rest;

import brightvl.spring.model.Timesheet;
import brightvl.spring.repository.TimesheetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления таймшитами.
 */
@Service
public class TimesheetService {

    private final TimesheetRepository timesheetRepository;


    public TimesheetService(TimesheetRepository repository) {
        this.timesheetRepository = repository;

    }

    /**
     * Поиск таймшита по идентификатору.
     *
     * @param id идентификатор таймшита
     * @return найденный таймшит, если существует
     */
    public Optional<Timesheet> findById(Long id) {
        return timesheetRepository.findById(id);
    }

    /**
     * Получить все табели.
     *
     * @return список всех табелей.
     */
    public List<Timesheet> findAll() {
        return timesheetRepository.findAll();
    }


    /**
     * Создание нового таймшита.
     *
     * @param timesheet создаваемый таймшит
     * @return созданный таймшит
     */
    public Timesheet create(Timesheet timesheet) {
        return timesheetRepository.save(timesheet);
    }

    /**
     * Удаление таймшита по идентификатору.
     *
     * @param id идентификатор таймшита
     */
    public void delete(Long id) {
        timesheetRepository.deleteById(id);
    }

    public List<Timesheet> findByEmployeeId(Long employeeId) {
        return timesheetRepository.findByEmployeeId(employeeId);
    }

    public List<Timesheet> findByProjectId(Long projectId) {
        return timesheetRepository.findByProjectId(projectId);
    }

}
