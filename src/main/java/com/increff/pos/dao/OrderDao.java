package com.increff.pos.dao;

import com.increff.pos.pojo.OrderPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderDao extends AbstractDao{
    private static String select_id = "select b from OrderPojo b where id=:id";
    private static String select_all = "select b from OrderPojo b";
//    private static String select_all_filtered = "select b from OrderPojo b where b.date>=:s and b.data<=:e";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderPojo o) {
        em.persist(o);
    }



    public OrderPojo select(int id) {
        try {
            TypedQuery<OrderPojo> query = getQuery(select_id, OrderPojo.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }
    }

    public List<OrderPojo> selectAll() {
        TypedQuery<OrderPojo> query = getQuery(select_all, OrderPojo.class);
        return query.getResultList();
    }

//    public List<OrderPojo> selectAllFiltered(String s, String e) {
//        TypedQuery<OrderPojo> query = getQuery(select_all_filtered, OrderPojo.class);
//        query.setParameter("s", s);
//        query.setParameter("e", e);
//        return query.getResultList();
//    }


    public void update(OrderPojo p) {
    }
}
