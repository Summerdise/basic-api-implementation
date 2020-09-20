package com.thoughtworks.rslist.Repository;

import com.thoughtworks.rslist.Entity.RsEventEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface RsEventRepository extends CrudRepository<RsEventEntity,Integer> {
    @Override
    List<RsEventEntity> findAll();
    @Transactional
    void deleteAllByUserId(int userId);
}
