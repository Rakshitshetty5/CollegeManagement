package com.example.rakshit.CollegeManagementSystem.utils;

import com.example.rakshit.CollegeManagementSystem.enums.Permission;
import com.example.rakshit.CollegeManagementSystem.enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.rakshit.CollegeManagementSystem.enums.Permission.*;
import static com.example.rakshit.CollegeManagementSystem.enums.Role.*;

public class PermissionMapping {

    private static final Map<Role, Set<Permission>> map = Map.of(
            USER, Set.of(USER_VIEW, STUDENT_VIEW),
            CREATOR, Set.of(STUDENT_CREATE, STUDENT_UPDATE, USER_UPDATE),
            ADMIN, Set.of(STUDENT_CREATE, STUDENT_DELETE, STUDENT_VIEW, STUDENT_UPDATE, USER_CREATE, USER_UPDATE)
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Role role) {
        return map.get(role).stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());
    }
}
