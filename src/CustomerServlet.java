import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json");
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");
            ResultSet rst = connection.prepareStatement("select * from Customer").executeQuery();
            String allRec="";
            while (rst.next()){
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                double salary = rst.getDouble(4);
                System.out.println(id+""+name+""+address+""+salary);
                String customer="{\"id\":\""+id+"\",\"name\":\""+name+"\",\"address\":\""+address+"\",\"salary\":"+salary+"},";
                allRec=allRec+customer;
            }
            String finalJson="[" +allRec.substring(0,allRec.length()-1)+"]";
            PrintWriter writer = resp.getWriter();
            writer.write(finalJson);
            resp.setContentType("application/json");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        System.out.println("sfdd");
        PrintWriter writer = resp.getWriter();
//        writer.write("response went");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerID = req.getParameter("customerID");
        String customerName = req.getParameter("customerName");
        String customerAddress = req.getParameter("customerAddress");
        String customerSalary = req.getParameter("customerSalary");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO Customer values(?,?,?,?)");
            pstm.setObject(1,customerID );
            pstm.setObject(2,customerName);
            pstm.setObject(3,customerAddress);
            pstm.setObject(4,customerSalary);

            boolean b = pstm.executeUpdate() > 0;
            PrintWriter writer = resp.getWriter();
            if (b){
                writer.write("Customer Added...!");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String customerID = req.getParameter("cusID");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("DELETE from Customer where id=?");
            pstm.setObject(1,customerID );
            boolean b = pstm.executeUpdate() > 0;
            PrintWriter writer = resp.getWriter();
            if (b){
                writer.write("Customer Deleted...!");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//    @Override
//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String customerID = req.getParameter("customerID");
//        String customerName = req.getParameter("customerName");
//        String customerAddress = req.getParameter("customerAddress");
//        String customerSalary = req.getParameter("customerSalary");
//
//        System.out.println("customerID: " + customerID);
//        System.out.println("customerName: " + customerName);
//        System.out.println("customerAddress: " + customerAddress);
//        System.out.println("customerSalary: " + customerSalary);
//
//        // Continue with your existing code...
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");
//            PreparedStatement pstm = connection.prepareStatement("UPDATE Customer set name=?,address=?,salary=? where id=?");
//            pstm.setObject(1, customerName);
//            pstm.setObject(2, customerAddress);
//            pstm.setObject(3, customerSalary);
//            pstm.setObject(4, customerID);
//
//            int rowsAffected = pstm.executeUpdate();
//            System.out.println("Rows affected: " + rowsAffected);
//            connection.commit();
//
//            PrintWriter writer = resp.getWriter();
//            writer.write("sdfvdgmidfbjmfg");
//            boolean b = pstm.executeUpdate() > 0;
//            if (b) {
//                writer.write("Customer Updated..!");
//            }
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerID = req.getParameter("customerID");
        String customerName = req.getParameter("customerName");
        String customerAddress = req.getParameter("customerAddress");
        String customerSalary = req.getParameter("customerSalary");

        System.out.println("Updating Customer with ID: " + customerID);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "1234");
            PreparedStatement pstm = connection.prepareStatement("UPDATE Customer set name=?,address=?,salary=? where id=?");
            pstm.setObject(1, customerName);
            pstm.setObject(2, customerAddress);
            pstm.setObject(3, customerSalary);
            pstm.setObject(4, customerID);

            int affectedRows = pstm.executeUpdate();

            PrintWriter writer = resp.getWriter();

            if (affectedRows > 0) {
                writer.write("Customer Updated..!");
                System.out.println("Customer with ID: " + customerID + " updated successfully!");
            } else {
                writer.write("No Customer Found with the given ID.");
                System.out.println("No Customer found with ID: " + customerID);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
