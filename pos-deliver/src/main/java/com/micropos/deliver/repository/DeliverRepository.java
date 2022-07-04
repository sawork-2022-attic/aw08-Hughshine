package com.micropos.deliver.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.micropos.deliver.model.Deliver;


@Repository
public interface DeliverRepository extends CrudRepository<Deliver, Integer> {
    @Override
    default Iterable<Deliver> findAll() {
        List<Deliver> result = new ArrayList<Deliver>();
        var deliver = new Deliver();
        deliver.setId(114514);
        deliver.setOrderid(114514);
        result.add(deliver);
        return result;
    }
}
