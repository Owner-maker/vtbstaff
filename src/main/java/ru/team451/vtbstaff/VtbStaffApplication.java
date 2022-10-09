package ru.team451.vtbstaff;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.team451.vtbstaff.domain.Achievement;
import ru.team451.vtbstaff.domain.AppUser;
import ru.team451.vtbstaff.domain.Role;
import ru.team451.vtbstaff.service.UserService;

import java.io.IOException;
import java.util.ArrayList;

import static ru.team451.vtbstaff.domain.RoleEnum.*;

@SpringBootApplication
public class VtbStaffApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(VtbStaffApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService){
		return args -> {
			userService.saveRole(new Role(null, ROLE_USER.name()));
			userService.saveRole(new Role(null, ROLE_ADMIN.name()));
			userService.saveRole(new Role(null, ROLE_MANAGER.name()));
			userService.saveRole(new Role(null, ROLE_CREATOR.name()));
			userService.saveRole(new Role(null, ROLE_HR.name()));

			userService.saveAchievement(new Achievement(null, "Постоялец","Зайти 10 дней подряд на платформу"));
			userService.saveAchievement(new Achievement(null, "Настойчивый","Зайти 30 дней подряд на платформу"));
			userService.saveAchievement(new Achievement(null, "Благодарочка","Получить 5 переводов монет от друзей"));
			userService.saveAchievement(new Achievement(null, "Щедрая душа","Отправить 5 переводов монет друзьям"));
			userService.saveAchievement(new Achievement(null, "Коллекционер","Собрать 3 элемента NFT"));


			userService.saveUser(new AppUser(null, "Misterio John", "mister@gmail.com", "123456",
					new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),null,null));
			userService.saveUser(new AppUser(null, "Roberto Crystall", "cryst@gmail.com", "123456",
					new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),null,null));
			userService.saveUser(new AppUser(null, "Task Taskov", "task@gmail.com", "123456",
					new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),null,null));
			userService.saveUser(new AppUser(null, "Common Man", "man@gmail.com", "123456",
					new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),null,null));
			userService.saveUser(new AppUser(null, "Vasya Petrov", "vasek", "123456",
					new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),null,null));
			userService.saveUser(new AppUser(null, "Masha Listratova", "masha", "123456",
					new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),null,null));

			userService.addRoleToUser("mister@gmail.com", ROLE_USER.name());
			userService.addRoleToUser("mister@gmail.com", ROLE_ADMIN.name());
			userService.addRoleToUser("mister@gmail.com", ROLE_MANAGER.name());
			userService.addRoleToUser("mister@gmail.com", ROLE_CREATOR.name());

			userService.addRoleToUser("cryst@gmail.com", ROLE_MANAGER.name());
			userService.addRoleToUser("cryst@gmail.com", ROLE_USER.name());

			userService.addRoleToUser("task@gmail.com", ROLE_CREATOR.name());
			userService.addRoleToUser("task@gmail.com", ROLE_USER.name());

			userService.addRoleToUser("vasek", ROLE_USER.name());
			userService.addRoleToUser("man@gmail.com", ROLE_USER.name());
			userService.addRoleToUser("masha", ROLE_USER.name());
		};
	}

}
