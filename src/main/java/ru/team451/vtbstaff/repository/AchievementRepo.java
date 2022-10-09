package ru.team451.vtbstaff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.team451.vtbstaff.domain.Achievement;

public interface AchievementRepo extends JpaRepository<Achievement, Long> {
    Achievement findAchievementByName(String name);
    void deleteAchievementByName(String name);
}
