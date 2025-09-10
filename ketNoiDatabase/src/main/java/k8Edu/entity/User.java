package k8Edu.entity;

public class User {
	@Entity
	@Table(name = "users")
	public class User {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    @Column(name = "fullname")
	    private String fullname;
	    
	    @Column(name = "phone")
	    private String phone;
	    
	    @Column(name = "images")
	    private String images; // Lưu đường dẫn của ảnh
	    
	    // Getters and Setters
	}

}
