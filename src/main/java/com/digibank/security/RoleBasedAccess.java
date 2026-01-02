package com.digibank.security;

import com.digibank.models.Role;
import com.digibank.models.User;

import java.util.*;

/**
 * Role-Based Access Control (RBAC) Implementation
 * Manages permissions for different user roles
 */
public class RoleBasedAccess {
    private Map<Role, Set<Permission>> rolePermissions;

    public RoleBasedAccess() {
        this.rolePermissions = new HashMap<>();
        initializeDefaultPermissions();
    }

    /**
     * Initialize default permissions for each role
     */
    private void initializeDefaultPermissions() {
        // ADMIN - Full access
        Set<Permission> adminPerms = new HashSet<>(Arrays.asList(Permission.values()));
        rolePermissions.put(Role.ADMIN, adminPerms);

        // CITY_MANAGER - Infrastructure control
        Set<Permission> managerPerms = new HashSet<>(Arrays.asList(
            Permission.CONTROL_LIGHTS,
            Permission.CONTROL_TRAFFIC,
            Permission.VIEW_SENSORS,
            Permission.EXECUTE_ROUTINES,
            Permission.VIEW_REPORTS,
            Permission.VIEW_SECURITY_LOGS
        ));
        rolePermissions.put(Role.CITY_MANAGER, managerPerms);

        // RESIDENT - Basic services
        Set<Permission> residentPerms = new HashSet<>(Arrays.asList(
            Permission.PROCESS_PAYMENT,
            Permission.VIEW_TRANSACTIONS
        ));
        rolePermissions.put(Role.RESIDENT, residentPerms);

        // PUBLIC_SAFETY - Security access
        Set<Permission> safetyPerms = new HashSet<>(Arrays.asList(
            Permission.VIEW_SECURITY_LOGS,
            Permission.VIEW_SENSORS,
            Permission.VIEW_REPORTS
        ));
        rolePermissions.put(Role.PUBLIC_SAFETY, safetyPerms);

        // UTILITY_WORKER - Maintenance access
        Set<Permission> utilityPerms = new HashSet<>(Arrays.asList(
            Permission.VIEW_SENSORS,
            Permission.VIEW_REPORTS
        ));
        rolePermissions.put(Role.UTILITY_WORKER, utilityPerms);

        System.out.println("[RBAC] Default permissions initialized for " +
                          rolePermissions.size() + " roles");
    }

    /**
     * Check if user has specific permission
     * @param user User to check
     * @param permission Permission to verify
     * @return true if user has permission
     */
    public boolean hasPermission(User user, Permission permission) {
        Set<Permission> permissions = rolePermissions.get(user.getRole());

        if (permissions == null) {
            return false;
        }

        return permissions.contains(permission);
    }

    /**
     * Check access for a resource (throws exception if unauthorized)
     * @param user User requesting access
     * @param resource Resource name
     * @throws UnauthorizedException if user lacks permission
     */
    public void checkAccess(User user, String resource) throws UnauthorizedException {
        Permission permission = Permission.forResource(resource);

        if (permission == null) {
            throw new UnauthorizedException("Unknown resource: " + resource);
        }

        if (!hasPermission(user, permission)) {
            System.out.println("[RBAC] ✗ Access DENIED for user: " + user.getUsername() +
                              " (role: " + user.getRole() + ") to resource: " + resource);
            throw new UnauthorizedException(
                "User '" + user.getUsername() + "' (role: " + user.getRole() +
                ") lacks permission for: " + resource
            );
        }

        System.out.println("[RBAC] ✓ Access GRANTED for user: " + user.getUsername() +
                          " (role: " + user.getRole() + ") to resource: " + resource);
    }

    /**
     * Assign role to user
     */
    public void assignRole(User user, Role role) {
        user.setRole(role);
        System.out.println("[RBAC] Role assigned: " + role + " to user: " + user.getUsername());
    }

    /**
     * Get all permissions for a role
     */
    public Set<Permission> getPermissions(Role role) {
        return new HashSet<>(rolePermissions.getOrDefault(role, Collections.emptySet()));
    }
}
