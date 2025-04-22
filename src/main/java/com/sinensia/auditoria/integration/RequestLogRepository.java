package com.sinensia.auditoria.integration;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinensia.auditoria.model.RequestLog;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long>{

}
