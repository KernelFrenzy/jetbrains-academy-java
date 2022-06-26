package com.xfrenzy47x.repository;

import com.xfrenzy47x.model.CodePlatformPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodePlatformPageRepository extends JpaRepository<CodePlatformPage, String> {
}

