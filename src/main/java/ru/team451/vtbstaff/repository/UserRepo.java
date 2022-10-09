package ru.team451.vtbstaff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.team451.vtbstaff.domain.AppUser;

public interface UserRepo extends JpaRepository<AppUser, Long> {
    AppUser findUserByUsername(String username);
    void deleteAppUserByUsername(String username);
}
