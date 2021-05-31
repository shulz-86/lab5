package shulz.lab5.hibernate;

import java.util.List;

public class Main {

    private static final ProductDao dao = new ProductDao();
    public static void main(String[] args) {

        testCreate();
        testGetById();
        testFindAll();
        testDelete();

        System.exit(1);
    }

    private static void testDelete() {
        dao.deleteById(1L);
        Product product = dao.findById(1L);
        assert product == null;
    }

    private static void testFindAll() {
        List<Product> all = dao.findAll();
        assert all != null && all.size() == 1;
    }

    private static void testGetById() {
        Product product = dao.findById(1L);

        assert 1L == product.getId();
        assert "Огурец".equals(product.getTitle());
        assert 123 == product.getPrice();
    }

    private static void testCreate() {
        Product product = new Product();
        product.setId(1L);
        product.setTitle("Огурец");
        product.setPrice(123);
        dao.saveOrUpdate(product);
    }
}
