package br.com.insidesoftwares.audit.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Table(name = "INS_LOG_AUDIT")
@Entity
public class InsideAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID_LOG")
    private UUID id;
    @Column(name = "ORIGIN_SYSTEM")
    private String originSystem;
    @Column(name = "METHOD")
    private String method;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "LOGGED_USER")
    private String user;
    @Column(name = "SUCCESS")
    private boolean success;
    @Column(name = "MESSAGE_ERROR")
    private String messageError;
    @Column(name = "START_DATE_CHANGE")
    private LocalDateTime startDateChange;
    @Column(name = "END_DATE_CHANGE")
    private LocalDateTime endDateChange;
    @Column(name = "PARAMETER")
    private String parameter;
    @Column(name = "RESPONSE")
    private String response;

}
