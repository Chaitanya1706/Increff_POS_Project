package com.increff.pos.dao;

import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ProductDao extends AbstractDao{
    private static String delete_id = "delete from ProductPojo p where id=:id";

    // todo -> remove delete flow
    private static String select_id = "select p from ProductPojo p where id=:id";
    private static String select_all = "select p from ProductPojo p";

    private static String get_id = "select p from ProductPojo p where barcode=:barcode";
    private static String get_product_from_brandId = "select p from ProductPojo p where brand_category=:id";


    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(ProductPojo p) throws ApiException{
        try{
            em.persist(p);
        }
        catch(Exception e){
            throw new ApiException("Barcode already exist");
        }

    }

    // todo -> make abstract methods

    public ProductPojo select(int id) {
        TypedQuery<ProductPojo> query = getQuery(select_id, ProductPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<ProductPojo> selectAll() {
        TypedQuery<ProductPojo> query = getQuery(select_all, ProductPojo.class);
        return query.getResultList();
    }

    public ProductPojo getProductFromBarcode(String barcode){
        TypedQuery<ProductPojo> query = getQuery(get_id, ProductPojo.class);
        query.setParameter("barcode", barcode);
        return getSingle(query);
    }

    public List<ProductPojo> getProductFromBrandId(Integer id){
        TypedQuery<ProductPojo> query = getQuery(get_product_from_brandId, ProductPojo.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public void update(ProductPojo p) {
    }
}
