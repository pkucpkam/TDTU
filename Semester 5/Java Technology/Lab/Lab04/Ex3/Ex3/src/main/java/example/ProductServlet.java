package example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import com.google.gson.Gson;

@WebServlet("/products")
public class ProductServlet extends HttpServlet{

    private static List<Product> products = new ArrayList<>();

    static {
        Product product = new Product(0, "Iphone", 12300000);
        Product product1 = new Product(1, "Samsung", 2130000);
        Product product2 = new Product(2, "Ipad", 2354000);
        Product product3 = new Product(3, "Android", 1789345);
        Product product4 = new Product(4, "Laptop", 25400000);
        Product product5 = new Product(5, "PC", 6780000);

        products.add(product);
        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        if(id != null) {
            Optional<Product> product = products.stream()
                    .filter(p -> p.getId() == Integer.parseInt(id))
                    .findFirst();
            if(product.isPresent()) {
                int idParam = Integer.parseInt(id);
                products.remove(idParam);
                responseToClient(resp, 200, "Successfully to deleted", products);
            } else {
                responseToClient(resp, 404, "Not found data with id = " + id, "");
            }
        } else {
            responseToClient(resp, 202, "Missing paramater", "");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        if(id != null) {
            Optional<Product> product = products.stream()
                    .filter(p -> p.getId() == Integer.parseInt(id))
                    .findFirst();
            if(product.isPresent()) {
                Product foundProduct = product.get();
                responseToClient(resp, 200, "Found data", foundProduct);
            } else {
                responseToClient(resp, 404, "Not found data with id = " + id, "");
            }
        } else {
            responseToClient(resp, 200, "Success", products);
        }
    }

    private void responseToClient(HttpServletResponse resp, int statusCode, String message, Object data) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(statusCode);

        ResponseApi responseApi = new ResponseApi(statusCode, message, data);

        Gson gson = new Gson();
        String json = gson.toJson(responseApi);
        resp.getWriter().println(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String price = req.getParameter("price");

        Pattern intCheck = Pattern.compile("^[-+]?\\d+$");
        if(!intCheck.matcher(id).matches()) {
            responseToClient(resp, 400, "Id must be an integer", "");
            return;
        }

        try {
            Double.parseDouble(price);
        } catch (NumberFormatException e) {
            responseToClient(resp, 400, "Price must be a number", "");
            return;
        }

        if(name.equals("")) {
            responseToClient(resp, 400, "Name is empty", "");
            return;
        }

        double priceParam = Double.parseDouble(price);
        int idParam = Integer.parseInt(id);

        Optional<Product> findProduct = products.stream()
                .filter(p -> p.getId() == idParam)
                .findFirst();

        if(findProduct.isPresent()) {
            responseToClient(resp, 202, "Failed, id has existed", "");
            return;
        }

        Product product = new Product(idParam, name, priceParam);
        products.add(
                product
        );

        responseToClient(resp, 200, "Successfully to added", products);


    }

    @Override
    public void init() throws ServletException {
        System.out.println("Xin chao");
        products.forEach(System.out::println);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String price = req.getParameter("price");

        Pattern intCheck = Pattern.compile("^[-+]?\\d+$");
        if(!intCheck.matcher(id).matches()) {
            responseToClient(resp, 400, "Id must be an integer", "");
            return;
        }

        try {
            Double.parseDouble(price);
        } catch (NumberFormatException e) {
            responseToClient(resp, 400, "Price must be a number", "");
            return;
        }

        if(name.equals("")) {
            responseToClient(resp, 400, "Name is empty", "");
            return;
        }

        double priceParam = Double.parseDouble(price);
        int idParam = Integer.parseInt(id);

        Optional<Product> findProduct = products.stream()
                .filter(p -> p.getId() == idParam)
                .findFirst();

        if(findProduct.isPresent()) {
            Product product = findProduct.get();
            product.setName(name);
            product.setPrice(priceParam);
            responseToClient(resp, 200, "Successfully to updated", products);
        } else {
            responseToClient(resp, 202, "Failed, id doesn't existed", "");

        }


    }


}

