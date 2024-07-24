package com.migration.mariadb.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.migration.mariadb.user.dto.UserDto;

@Repository
public interface EgovSuperUserRepository extends JpaRepository<EgovSuperUser, String> {
    @Query("SELECT new com.migration.mariadb.user.dto.UserDto(u.userId, u.name, u.gender, u.birth, c.phone, u.email) FROM EgovSuperUser u left join CommonUser c on u.userJoinId = c.userJoinId")
    List<UserDto> findAllUserDto();

    @Query("SELECT new com.migration.mariadb.user.dto.UserDto(u.userId, u.name, u.gender, u.birth, c.phone, u.email) FROM EgovSuperUser u left join CommonUser c on u.userJoinId = c.userJoinId where u.userId = :userId")
    UserDto findByUserId(String userId);
}
