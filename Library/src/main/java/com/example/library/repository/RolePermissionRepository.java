package com.example.library.repository;

import com.example.library.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

}
