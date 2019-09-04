package com.wondersgroup.smileactivity.smileactivity.repository;

import com.wondersgroup.smileactivity.smileactivity.entity.TbVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TbVideoRepository extends JpaRepository<TbVideo,String>,JpaSpecificationExecutor<TbVideo> {
}
