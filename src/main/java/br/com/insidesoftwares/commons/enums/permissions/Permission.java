package br.com.insidesoftwares.commons.enums.permissions;

import br.com.insidesoftwares.commons.enums.system.SystemCode;
import br.com.insidesoftwares.commons.specification.PermissionRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum Permission implements PermissionRole {

    //Defaul Permissions
    ADMIN_VIEW("ADMIN_VIEW", "Permission to view", null, ProfileType.getAdmimProfile()),
    ADMIN_WRITE("ADMIN_WRITE", "Permission to write", null, ProfileType.getAdmimProfile()),
    INTEGRATION_VIEW("INTEGRATION_VIEW", "Permission to view", null, ProfileType.getIntegrationProfile()),
    INTEGRATION_WRITE("INTEGRATION_WRITE", "Permission to write", null, ProfileType.getIntegrationProfile()),
    MAINTENANCE_VIEW("MAINTENANCE_VIEW", "Permission to view", null, ProfileType.getMaintenanceProfile()),
    MAINTENANCE_WRITE("MAINTENANCE_WRITE", "Permission to write", null, ProfileType.getMaintenanceProfile()),
    REPORT_VIEW("REPORT_VIEW", "Permission to view", null, ProfileType.getReportProfile()),
    REPORT_WRITE("REPORT_WRITE", "Permission to write", null, ProfileType.getReportProfile()),
    AUDIT_VIEW("AUDIT_VIEW", "Permission to view", null, ProfileType.getAuditProfile()),
    AUDIT_WRITE("AUDIT_WRITE", "Permission to write", null, ProfileType.getAuditProfile()),
    TECHNICIAN_VIEW("TECHNICIAN_VIEW", "Permission to view", null, ProfileType.getTechProfile()),
    TECHNICIAN_WRITE("TECHNICIAN_WRITE", "Permission to write", null, ProfileType.getTechProfile()),


    //Permissions Created
    AUTHENTICATION_TOKEN_REFRESH("AUTHENTICATION_TOKEN_REFRESH", "Refresh authentication token", SystemCode.IAC, ProfileType.getAllProfile()),
    CHANGE_PASSWORD_REQUIRED("CHANGE_PASSWORD_REQUIRED", "Permission to change password required", SystemCode.IAC, ProfileType.getAllProfile()),
    CHANGE_PASSWORD("CHANGE_PASSWORD", "Permission to change password", SystemCode.IAC, ProfileType.getAllProfile()),
    LOGOUT_USER("LOGOUT_USER", "Logout user", SystemCode.IAC, ProfileType.getAllProfile()),
    ;

    private final String role;
    private final String description;
    private final SystemCode systemCode;
    private final List<ProfileType> profileTypes;

    @Override
    public String role() {
        return getRole();
    }

    public static List<Permission> permissionsBySystem(final SystemCode systemCode){
        return Arrays.stream(Permission.values())
                .filter(permission -> Objects.nonNull(permission.getSystemCode()))
                .filter(permission -> permission.getSystemCode().equals(systemCode))
                .collect(Collectors.toList());
    }
}
