package com.increff.pos.dao;

import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.service.ApiException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao{
    private static String delete_id = "delete from OrderItemPojo p where id=:id";

    // todo -> remove delete flow
    private static String select_id = "select p from OrderItemPojo p where id=:id";
    private static String select_all = "select p from OrderItemPojo p";
    private static String select_by_order_id = "select p from OrderItemPojo p where orderId=:id";
    private static String get_from_barcode = "select p from OrderItemPojo p where orderId=:oid and productId=:pid";


    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(OrderItemPojo p) throws ApiException{

        em.persist(p);

    }

    // todo -> make abstract methods

    public int delete(int id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public OrderItemPojo select(int id) {
        TypedQuery<OrderItemPojo> query = getQuery(select_id, OrderItemPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<OrderItemPojo> selectAll() {
        TypedQuery<OrderItemPojo> query = getQuery(select_all, OrderItemPojo.class);
        return query.getResultList();
    }

    public List<OrderItemPojo> selectByOrderId(Integer id) {
        TypedQuery<OrderItemPojo> query = getQuery(select_by_order_id, OrderItemPojo.class);
        query.setParameter("id", id);
        return query.getResultList();
    }


    public OrderItemPojo getFromBarcode(int oid,int pid) {
        TypedQuery<OrderItemPojo> query = getQuery(get_from_barcode, OrderItemPojo.class);
        query.setParameter("oid", oid);
        query.setParameter("pid", pid);
        return getSingle(query);
    }

    public void update(OrderItemPojo p) {
    }
}
