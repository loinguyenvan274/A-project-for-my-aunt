package dao;

//import org.hibernate.SessionFactory;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//
//public class HibernateUtil {
//
//    private static SessionFactory sessionFactory;
//
//    static {
//       setUp();
//    }
//
//    protected static void setUp() {
//        final StandardServiceRegistry registry =
//                new StandardServiceRegistryBuilder()
//                        .build();
//        try {
//            sessionFactory =
//                    new MetadataSources(registry)
//                            .addAnnotatedClass(KhachHang.class)
//                            .addAnnotatedClass(SanPham.class)
//                            .addAnnotatedClass(DonHang.class)
//                            .addAnnotatedClass(DonHangSanPham.class)
//                            .addAnnotatedClass(KhachHangNo.class)
//                            .buildMetadata()
//                            .buildSessionFactory();
//        }
//        catch (Exception e) {
//            StandardServiceRegistryBuilder.destroy(registry);
//        }
//    }
//
//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }
//}
import model.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Tạo ServiceRegistry
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml") // Tên file cấu hình
                    .build();

            // Tạo MetadataSources
            MetadataSources metadataSources = new MetadataSources(standardRegistry)
                            .addAnnotatedClass(KhachHang.class)
                            .addAnnotatedClass(SanPham.class)
                            .addAnnotatedClass(DonHang.class)
                            .addAnnotatedClass(DonHangSanPham.class)
                            .addAnnotatedClass(KhachHangNo.class);

            // Tạo SessionFactory
            return metadataSources.buildMetadata().buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
