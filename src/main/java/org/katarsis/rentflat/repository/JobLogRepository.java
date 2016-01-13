package org.katarsis.rentflat.repository;

import org.katarsis.rentflat.entities.JobLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobLogRepository extends JpaRepository<JobLog, Integer>{

}