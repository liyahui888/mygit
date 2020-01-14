package com.cloud.provider.mapper;

import com.cloud.provider.entity.UserEntity;
import com.cloud.provider.entity.UserSlaveEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserSlaveMapper {

    List<UserSlaveEntity> getAll();
    List<UserSlaveEntity> findByPage(Integer offset, Integer pageSize);
    UserSlaveEntity getOne(Long id);
    int insert(UserSlaveEntity user);
    int insertBatch(List<UserSlaveEntity> user);
    int update(UserSlaveEntity user);
    int delete(Long id);
    int deleteAll();
    int count();

}
