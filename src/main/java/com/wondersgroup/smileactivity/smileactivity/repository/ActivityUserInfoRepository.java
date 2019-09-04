package com.wondersgroup.smileactivity.smileactivity.repository;

import com.wondersgroup.smileactivity.smileactivity.entity.ActivityUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityUserInfoRepository extends JpaRepository<ActivityUserInfo,String>,JpaSpecificationExecutor<ActivityUserInfo> {
}
