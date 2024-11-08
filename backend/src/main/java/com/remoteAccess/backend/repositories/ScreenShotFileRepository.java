package com.remoteAccess.backend.repositories;

import com.remoteAccess.backend.models.ScreenShotFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenShotFileRepository extends JpaRepository<ScreenShotFile, Integer> {
    // Additional query methods can be added here if needed
}

