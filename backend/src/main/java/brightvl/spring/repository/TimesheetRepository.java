package brightvl.spring.repository;

import brightvl.spring.model.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {
    List<Timesheet> findByEmployeeId(long employeeId);

    List<Timesheet> findByProjectId(Long projectId);
}