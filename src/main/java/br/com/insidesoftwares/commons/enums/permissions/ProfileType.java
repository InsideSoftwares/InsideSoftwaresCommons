package br.com.insidesoftwares.commons.enums.permissions;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum ProfileType {
    ADMIN,
    TECHNICIAN,
    MAINTENANCE,
    REPORT,
    AUDIT,
    INTEGRATION;

    public static List<ProfileType> getAllProfile(){
        return getProfiles(ProfileType.values());
    }

    public static List<ProfileType> getAdmimProfile(){
        return getProfiles(ProfileType.ADMIN);
    }

    public static List<ProfileType> getTechProfile(){
        return getProfiles(ProfileType.ADMIN, ProfileType.TECHNICIAN, ProfileType.MAINTENANCE);
    }

    public static List<ProfileType> getMaintenanceProfile(){
        return getProfiles(ProfileType.ADMIN, ProfileType.MAINTENANCE);
    }

    public static List<ProfileType> getReportProfile(){
        return getProfiles(ProfileType.ADMIN, ProfileType.MAINTENANCE, ProfileType.REPORT, ProfileType.AUDIT);
    }

    public static List<ProfileType> getAuditProfile(){
        return getProfiles(ProfileType.ADMIN, ProfileType.AUDIT);
    }

    public static List<ProfileType> getIntegrationProfile(){
        return getProfiles(ProfileType.ADMIN, ProfileType.INTEGRATION);
    }

    private static List<ProfileType> getProfiles(ProfileType... profileType){
        return Arrays.asList(profileType);
    }
}
