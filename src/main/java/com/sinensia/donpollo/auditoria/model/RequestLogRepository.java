package com.sinensia.donpollo.auditoria.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long>{

}
