package com.sinensia.donpollo.auditoria.integration;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sinensia.donpollo.auditoria.model.RequestLog;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long>{

}
