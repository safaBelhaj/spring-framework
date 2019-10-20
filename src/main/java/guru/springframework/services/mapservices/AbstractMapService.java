package guru.springframework.services.mapservices;

import guru.springframework.domain.DomainObject;

import java.util.*;

public abstract class AbstractMapService {

    protected Map<Integer, DomainObject> domainMap;


    public AbstractMapService(){ //Constructor
        domainMap=new HashMap<>();
    }

    public List<DomainObject> listAll(){
        return new ArrayList<>(domainMap.values());
    }

    public DomainObject saveOrUpdate(DomainObject domainObject){
        if(domainObject != null){
            if(domainObject.getId() == null){
                domainObject.setId(getNextKey());
            }
            domainMap.put(domainObject.getId(),domainObject);
            return domainObject;
        }else{
            throw new RuntimeException("Object can't be null!!");
        }

    }
    public Integer getNextKey(){
        if(domainMap.size()==0){
            return 1;
        }
        return Collections.max(domainMap.keySet())+1;
    }
    public void delete(Integer id){
        domainMap.remove(id);
    }
    public  DomainObject  getById(Integer id){
        return domainMap.get(id);
    }
}
