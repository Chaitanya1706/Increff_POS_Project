package com.increff.pos.dao;

import com.increff.pos.pojo.InventoryPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class InventoryDao extends AbstractDao{
    
    private static String select_id = "select i from InventoryPojo i where id=:id";
    private static String select_all = "select i from InventoryPojo i";
    private static String check_barcode = "select i from InventoryPojo i where productId=:id";


    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(InventoryPojo b) {
        em.persist(b);
    }

    public InventoryPojo getFromId(Integer id) {
        try {
            TypedQuery<InventoryPojo> query = getQuery(check_barcode, InventoryPojo.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }
    }

    public InventoryPojo select(int id) {
        try {
            TypedQuery<InventoryPojo> query = getQuery(select_id, InventoryPojo.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }
    }

    public List<InventoryPojo> selectAll() {
        TypedQuery<InventoryPojo> query = getQuery(select_all, InventoryPojo.class);
        return query.getResultList();
    }

    public void update(InventoryPojo p) {
    }
}
