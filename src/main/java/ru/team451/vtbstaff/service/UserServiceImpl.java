package ru.team451.vtbstaff.service;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.team451.vtbstaff.domain.Achievement;
import ru.team451.vtbstaff.domain.Role;
import ru.team451.vtbstaff.domain.AppUser;
import ru.team451.vtbstaff.repository.AchievementRepo;
import ru.team451.vtbstaff.repository.RoleRepo;
import ru.team451.vtbstaff.repository.UserRepo;

import javax.management.relation.RoleInfoNotFoundException;
import javax.management.relation.RoleNotFoundException;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final AchievementRepo achievementRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepo.findUserByUsername(username);
        if (appUser == null){
            log.error("User {} not found in DB", username);
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
        else{
            log.info("User {} found in DB", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }
    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Saving new {} to the DB", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new {} to the DB", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to the user {}", roleName, username);
        AppUser user = userRepo.findUserByUsername(username);
        Role role = roleRepo.findRoleByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public AppUser getUser(String username) {
        return userRepo.findUserByUsername(username);
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();         // fix later, page to page
    }

    @Override
    public Role getRole(String name) throws RoleNotFoundException {
        Role role =  roleRepo.findRoleByName(name);
        if (role == null){
            log.error("Role {} not found in DB", role);
            throw new RoleNotFoundException(String.format("Role %s not found", name));
        }
        return role;
    }

    @Override
    public Achievement saveAchievement(Achievement achievement) {
        log.info("Saving new achievenent {} to the DB", achievement.getName());
        return achievementRepo.save(achievement);
    }

    @Override
    public void addAchievementToUser(String username, String achieveName) {
        log.info("Adding achievement {} to the user {}", achieveName, username);
        AppUser user = userRepo.findUserByUsername(username);
        Achievement achievement = achievementRepo.findAchievementByName(achieveName);
        user.getAchievements().add(achievement);
    }

    @Override
    public Achievement getAchievement(String name) throws NotFoundException {
        Achievement achievement =  achievementRepo.findAchievementByName(name);
        if (achievement == null){
            log.error("Achievement {} not found in DB", name);
            throw new NotFoundException(String.format("Achievement %s not found", name));
        }
        return achievement;
    }

    @Override
    public void deleteRoleFromUser(String username, String roleName) {
    }

    @Override
    public void deleteUser(String username) {
    }

    @Override
    public void deleteRole(String roleName) {
    }

    @Override
    public AppUser updateUser(AppUser user) {
        return null;
    }

}
