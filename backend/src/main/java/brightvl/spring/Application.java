package brightvl.spring;


import brightvl.spring.model.Project;
import brightvl.spring.model.Role;
import brightvl.spring.model.User;
import brightvl.spring.model.UserRole;
import brightvl.spring.repository.ProjectRepository;
import brightvl.spring.repository.UserRepository;
import brightvl.spring.repository.UserRoleRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity(securedEnabled = true)
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        ProjectRepository projectRepo = ctx.getBean(ProjectRepository.class);

        for (int i = 1; i <= 5; i++) {
            Project project = new Project();
            project.setName("Project #" + i);
            projectRepo.save(project);
        }

        UserRepository userRepository = ctx.getBean(UserRepository.class);
        UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);

        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword("$2a$12$LbAPCsHn8ZN5MUDqDmIX7e9n1YlDkCxEt0lW3Q2WuW0M1vteo8jvG"); // admin
        admin = userRepository.save(admin);

        User user = new User();
        user.setLogin("user");
        user.setPassword("$2a$12$.dlnBAYq6sOUumn3jtG.AepxdSwGxJ8xA2iAPoCHSH61Vjl.JbIfq"); // user
        user = userRepository.save(user);

        User anonymous = new User();
        anonymous.setLogin("anon");
        anonymous.setPassword("$2a$12$CMqutMGl/1/gYngtfHTi8.7xIFZJG/3A708y8y3cRaQNkzQTmI2O."); // anon
        anonymous = userRepository.save(anonymous);

        assignRoleToUser(userRoleRepository, admin, Role.ADMIN);
        assignRoleToUser(userRoleRepository, admin, Role.USER);
        assignRoleToUser(userRoleRepository, user, Role.USER);
    }

    private static void assignRoleToUser(UserRoleRepository userRoleRepository, User user, Role role) {
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRoleName(role.getName());
        userRoleRepository.save(userRole);
    }

}