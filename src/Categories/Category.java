/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Categories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author icom
 */
public class Category {
    private ObjectProperty<Integer> CatID;
    private StringProperty Cat_name;

    public Category() {
        CatID = new SimpleObjectProperty<>(null);
        Cat_name = new SimpleStringProperty();
    }

    public Integer getCatID() {
        return CatID.get();
    }

    public String getCat_name() {
        return Cat_name.get();
    }

    public void setCatID(int CatID) {
        this.CatID.set(CatID);
    }

    public void setCat_name(String Cat_name) {
        this.Cat_name.set(Cat_name);
    }


    public ObjectProperty<Integer> getCatIDProperty() {
        return this.CatID;
    }

    public StringProperty getCat_nameProperty() {
        return this.Cat_name;
    }


    public static ObservableList<Category> selectAll() {

        ObservableList<Category> categories = FXCollections.observableArrayList();

        try (
                Connection conn = DB_service.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM category");) {
            while (rs.next()) {
                Category c = new Category();
                c.setCatID(rs.getInt("CatID")); //"id" is column name in table book
                c.setCat_name(rs.getString("Cat_name")); //"title" is column name in table book
                categories.add(c);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());

        }

        return categories;
    }

    public static Category insert(Category newcategory) throws SQLException {
        String sql = "INSERT into category (Cat_name) "
                + "VALUES (?)";
        ResultSet key = null;
        try (
                Connection conn = DB_service.getConnection();
                PreparedStatement stmt
                = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stmt.setString(1, newcategory.getCat_name());

            int rowInserted = stmt.executeUpdate();

            if (rowInserted == 1) {
                key = stmt.getGeneratedKeys();
                key.next();
                int newKey = key.getInt(1);
                newcategory.setCatID(newKey);
                return newcategory;
            } else {
                System.err.println("No category inserted");
                return null;
            }

        } catch (Exception e) {
            System.err.println(e);
            return null;
        } finally {
            if (key != null) {
                key.close();
            }
        }
    }

    public static boolean update(Category updatecategory) {
        String sql = "UPDATE category SET "
                + "Cat_name = ? "
                + "WHERE CatID = ?";

        try (
                Connection conn = DB_service.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ) {

            stmt.setString(1, updatecategory.getCat_name());
            stmt.setInt(2, updatecategory.getCatID());
            

            int rowUpdated = stmt.executeUpdate();

            if (rowUpdated == 1) {
                return true;
            } else {
                System.err.println("No category updated");
                return false;
            }

        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    public static boolean delete(Category deletecategory) {
        String sql = "DELETE FROM category WHERE CatID = ?";
        try (
                Connection conn = DB_service.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, deletecategory.getCatID());

            int rowDeleted = stmt.executeUpdate();

            if (rowDeleted == 1) {
                return true;
            } else {
                System.err.println("No category deleted");
                return false;
            }

        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
}
