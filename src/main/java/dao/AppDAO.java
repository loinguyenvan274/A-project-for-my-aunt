package dao;

import model.DonHang;
import model.KhachHang;
import model.KhachHangNo;
import model.SanPham;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class AppDAO  {
  private static final SessionFactory sessionFactory ;

  static {
   sessionFactory = HibernateUtil.getSessionFactory();
  }

    public boolean isAttributeExists(Class ObjectType,String attributeName,String value) {
        String hql = "SELECT 1 FROM "+ObjectType.getSimpleName()+" WHERE "+attributeName+" = :" + attributeName;
        try (Session session = sessionFactory.openSession()) {
            Query<?> query = session.createQuery(hql);
            query.setParameter(attributeName, value);
            return query.uniqueResult() != null;
        }
    }
    public <T> void updateItem(T object) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.merge(object);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error update "+object.getClass().getSimpleName()+": " + e.getMessage());
        }
    }
//    public void updateinfoKhachHang(KhachHang khachHang){
//        String sqlQuery = "UPDATE KhachHang " +
//                          "SET ten = :ten, diaChi = :diaChi, gmail = :gmail, sdt = :sdt " +
//                          "WHERE id = :id";
//        Session session =  null;
//        Transaction tx = null;
//        try {
//            session = sessionFactory.openSession();
//            tx = session.beginTransaction();
//            session.createQuery(sqlQuery).setParameter("id",khachHang.getIdKhachHang())
//                    .setParameter("ten",khachHang.getTen())
//                    .setParameter("sdt",khachHang.getSdt())
//                    .setParameter("gmail",khachHang.getGmail())
//                    .setParameter("diaChi",khachHang.getDiaChi())
//                    .executeUpdate();
//            tx.commit();
//        }catch (Exception e){
//            System.out.println("Error update KhachHang table " + e.getMessage() );
//            tx.rollback();
//        }
//        if(session != null) {
//            session.close();
//        }
//
//    }


    public void deleteItem(Class classType,String key){
        Session session = null;
        Transaction tx = null;
        try{
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.remove(session.find(classType,key));
            tx.commit();
        }catch (Exception e){
            System.out.println("Error delete item in KhachHang table " + e.getMessage() );
            tx.rollback();
        }

        if(session != null){
            session.close();
        }
    }

//    public void deleteKhachHang(String idKhachHang){
//      String sqlQuerly = "DELETE FROM KhachHang WHERE id = :id";
//      Session session = null;
//      Transaction tx = null;
//      try{
//          session = sessionFactory.openSession();
//          tx = session.beginTransaction();
//          session.createQuery(sqlQuerly).setParameter("id",idKhachHang).executeUpdate();
//          tx.commit();
//      }catch (Exception e){
//          System.out.println("Error delete item in KhachHang table " + e.getMessage() );
//          tx.rollback();
//      }
//      if(session != null){
//          session.close();
//      }
//    }
    public <T> void saveItem(T Item) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(Item);
            session.flush();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Lỗi xảy ra: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public <T> List<T> getItems(Class objectClass){
      List<T> items;
      String queryStr = "FROM "+objectClass.getSimpleName();
        try (Session session = sessionFactory.openSession()){
            items = session.createQuery(queryStr,objectClass).list();
        }
        return  items;
    }

    public <T> List<T> getItemsEqual(Class<T> objectClass, String fieldName, Object value) {
        List<T> items;
        try (Session session = sessionFactory.openSession()) {
            items = session.createQuery("FROM " + objectClass.getSimpleName() + " WHERE " + fieldName + " = :value", objectClass)
                    .setParameter("value", value)
                    .list();
        }
        return items;
    }

    public <T> List getItemsLike(Class objectClass, String findItem, String findField){
      List<T> Items;
      try  (Session session = sessionFactory.openSession()){
          Items = session.createQuery("FROM "+objectClass.getSimpleName()+" Where LOWER("+ findField +") LIKE LOWER(:findItem)",objectClass)
                  .setParameter("findItem","%"+findItem+"%")
                  .list();
      }
      return Items;
    }

    public KhachHang getKhachHang(String idKhachHang){
      KhachHang khachHang;
      try(Session session = sessionFactory.openSession()){
          khachHang = session.createQuery("FROM KhachHang WHERE id = :id",KhachHang.class).setParameter("id",idKhachHang).getSingleResultOrNull();
      }
      return  khachHang;
    }

    public void close(){
      if(sessionFactory!=null){
          sessionFactory.close();
          System.out.println("closed session Factory");
      }
    }



}
