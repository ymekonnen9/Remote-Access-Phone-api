package com.remoteAccess.backend.repositories;

import com.remoteAccess.backend.models.LocationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRequestRepository extends JpaRepository<LocationRequest,Long> {
}
