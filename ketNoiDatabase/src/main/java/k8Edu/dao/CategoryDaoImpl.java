package k8Edu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; // Vẫn giữ import này vì PreparedStatement/ResultSet vẫn ném SQLException
import java.util.ArrayList;
import java.util.List;

import k8Edu.model.Category;
import khoatrac.config.DBconnect;

public class CategoryDaoImpl extends DBconnect implements CategoryDao {
    @Override
    public void insert(Category category) {
        String sql = "INSERT INTO category(cate_name,icons) VALUES (?,?)";
        try (Connection con = super.getConnectionW();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, category.getName());
            ps.setString(2, category.getIcon());
            ps.executeUpdate();
        } catch (Exception e) { // <--- THAY ĐỔI TẠI ĐÂY: catch (Exception e)
            e.printStackTrace();
        }
    }

    @Override
    public void edit(Category category) {
        String sql = "UPDATE category SET cate_name = ?, icons=? WHERE cate_id = ?";
        try (Connection con = super.getConnectionW();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, category.getName());
            ps.setString(2, category.getIcon());
            ps.setInt(3, category.getId());
            ps.executeUpdate();
        } catch (Exception e) { // <--- THAY ĐỔI TẠI ĐÂY: catch (Exception e)
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM category WHERE cate_id = ?";
        try (Connection con = super.getConnectionW();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { // <--- THAY ĐỔI TẠI ĐÂY: catch (Exception e)
            e.printStackTrace();
        }
    }

    @Override
    public Category get(int id) {
        String sql = "SELECT * FROM category WHERE cate_id = ? ";
        try (Connection con = super.getConnectionW();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Category category = new Category();
                    category.setId(rs.getInt("cate_id"));
                    category.setName(rs.getString("cate_name"));
                    category.setIcon(rs.getString("icons"));
                    return category;
                }
            }
        } catch (Exception e) { // <--- THAY ĐỔI TẠI ĐÂY: catch (Exception e)
            e.printStackTrace();
        }
        return null;
    }

    // Phương thức get(String name) của bạn (đã được thêm vào ở bước trước)
    @Override
    public Category get(String name) {
        String sql = "SELECT * FROM category WHERE cate_name = ? ";
        try (Connection con = super.getConnectionW();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Category category = new Category();
                    category.setId(rs.getInt("cate_id"));
                    category.setName(rs.getString("cate_name"));
                    category.setIcon(rs.getString("icons"));
                    return category;
                }
            }
        } catch (Exception e) { // <--- THAY ĐỔI TẠI ĐÂY: catch (Exception e)
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Category";
        try (Connection conn = super.getConnectionW();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("cate_id"));
                category.setName(rs.getString("cate_name"));
                category.setIcon(rs.getString("icons"));
                categories.add(category);
            }
        } catch (Exception e) { // <--- THAY ĐỔI TẠI ĐÂY: catch (Exception e)
            e.printStackTrace();
        }
        return categories;
    }

    // Phương thức search(String keyword) của bạn (đã được thêm vào ở bước trước)
    @Override
    public List<Category> search(String keyword) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Category WHERE cate_name LIKE ? OR icons LIKE ?";
        try (Connection conn = super.getConnectionW();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category category = new Category();
                    category.setId(rs.getInt("cate_id"));
                    category.setName(rs.getString("cate_name"));
                    category.setIcon(rs.getString("icons"));
                    categories.add(category);
                }
            }
        } catch (Exception e) { // <--- THAY ĐỔI TẠI ĐÂY: catch (Exception e)
            e.printStackTrace();
        }
        return categories;
    }
}