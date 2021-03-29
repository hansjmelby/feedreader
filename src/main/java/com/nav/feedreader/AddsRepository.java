package com.nav.feedreader;

import com.nav.feedreader.domain.Add;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class AddsRepository{

    private  HashMap<UUID, Add> addsStore = new HashMap<UUID,Add>();

    public void persist(Add add){
        addsStore.put(add.getUuid(),add);
    }

    public HashMap<UUID,Add> getall(){
        return addsStore;
    }

    public List<Add> findByDescription(String contains){
        List<Add> response = new ArrayList<>();
        addsStore.forEach((uuid, add) -> {
            response.add(add);
        });
        return response.stream().filter(add -> add.getDesctipion().toLowerCase().contains(contains)).collect(Collectors.toList());

    }
}
