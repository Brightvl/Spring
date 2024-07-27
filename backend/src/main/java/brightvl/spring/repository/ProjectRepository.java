package brightvl.spring.repository;

import brightvl.spring.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностями Project.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
