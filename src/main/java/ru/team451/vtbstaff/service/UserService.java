package ru.team451.vtbstaff.service;

import javassist.NotFoundException;
import ru.team451.vtbstaff.domain.Achievement;
import ru.team451.vtbstaff.domain.Role;
import ru.team451.vtbstaff.domain.AppUser;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    AppUser getUser(String username);
    List<AppUser> getUsers();
    Role getRole(String name) throws RoleNotFoundException;
    Achievement saveAchievement(Achievement achievement);
    void addAchievementToUser(String username, String achieveName);
    Achievement getAchievement(String name) throws NotFoundException;
    void deleteRoleFromUser(String username, String roleName);
    void deleteUser(String username);
    void deleteRole(String roleName);
    AppUser updateUser(AppUser user);

}
