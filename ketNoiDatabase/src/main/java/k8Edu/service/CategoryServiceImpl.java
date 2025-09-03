package k8Edu.service;

import java.io.File;
import java.util.List;

import k8Edu.dao.CategoryDao;
import k8Edu.dao.CategoryDaoImpl;
import k8Edu.model.Category;

public class CategoryServiceImpl implements CategoryService {
	CategoryDao categoryDao = new CategoryDaoImpl();

    // >> THÊM PHƯƠNG THỨC insert(Category category) VÀO ĐÂY <<
    @Override
    public void insert(Category category) {
        categoryDao.insert(category);
    }

	@Override
	public void delete(int id) {
	categoryDao.delete(id);
	}
	@Override
	public Category get(int id) {
	return categoryDao.get(id);
	}

	@Override
	public void edit(Category newCategory) {
	Category oldCategory = categoryDao.get(newCategory.getId());
	oldCategory.setName(newCategory.getName());
	if (newCategory.getIcon() != null) {
	// XOA ANH CU DI
	final String dir = "E:\\upload"; // Đường dẫn này nên được cấu hình động, không nên hardcode
	String fileName = oldCategory.getIcon();
	File file = new File(dir + "/category/" + fileName); // Đảm bảo đường dẫn chính xác
	if (file.exists()) {
	file.delete();
	}
	oldCategory.setIcon(newCategory.getIcon());
	}
	categoryDao.edit(oldCategory);
	}

	@Override
	public Category get(String name) {
	return categoryDao.get(name);
	}
	@Override
	public List<Category> getAll() {
	return categoryDao.getAll();
	}
	@Override
	public List<Category> search(String catename) {
	return categoryDao.search(catename);
	}

}