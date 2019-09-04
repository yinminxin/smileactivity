package com.wondersgroup.smileactivity.smileactivity.repository;

import com.wondersgroup.smileactivity.smileactivity.entity.ActivityInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityInfoRepository extends JpaRepository<ActivityInfo,String>,JpaSpecificationExecutor<ActivityInfo> {
}
