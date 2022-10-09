package ru.team451.vtbstaff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.team451.vtbstaff.domain.Achievement;
import ru.team451.vtbstaff.domain.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findRoleByName(String username);
    void deleteRoleByName(String name);
}
