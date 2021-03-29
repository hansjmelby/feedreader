package com.nav.feedreader;

import com.nav.feedreader.domain.Add;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.UUID;

@Repository
public class AddsRepository{

    private  HashMap<UUID, Add> addsStore = new HashMap<UUID,Add>();

    public void persist(Add add){
        addsStore.put(add.getUuid(),add);
    }

    public HashMap<UUID,Add> getall(){
        return addsStore;
    }
}
