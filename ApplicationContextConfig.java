package com.niit.martzone.config;

import java.util.Properties;

import javax.sql.DataSource;     

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.niit.martzone.dao.CartDAO;
import com.niit.martzone.dao.CartDAOImpl;
import com.niit.martzone.dao.CategoryDAO;
import com.niit.martzone.dao.CategoryDAOImpl;
import com.niit.martzone.dao.ProductDAO;
import com.niit.martzone.dao.ProductDAOImpl;
import com.niit.martzone.model.Cart;
import com.niit.martzone.model.Category;
import com.niit.martzone.model.Product;
import com.niit.martzone.model.Supplier;
import com.niit.martzone.model.Users;
import com.niit.martzone.dao.SupplierDAO;
import com.niit.martzone.dao.SupplierDAOImpl;






@Configuration
@ComponentScan("com.niit.martzone")
@EnableTransactionManagement
public class ApplicationContextConfig {
	

    
    @Bean(name = "dataSource")
    public DataSource getDataSource() {
    	BasicDataSource dataSource = new BasicDataSource();
    	dataSource.setDriverClassName("org.h2.Driver");
    	dataSource.setUrl("jdbc:h2:tcp://localhost/~/mus");
    	dataSource.setUsername("sa");
    	dataSource.setPassword("");
    	
    	return dataSource;
    }
    
    
    private Properties getHibernateProperties() {
    	Properties properties = new Properties();
    	properties.setProperty("hibernate.hbm2ddl.auto", "update");
    	properties.put("hibernate.show_sql", "true");
    	properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
    	return properties;
    }
    
    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {
    	LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
    	sessionBuilder.addProperties(getHibernateProperties());
    	sessionBuilder.addAnnotatedClasses(Category.class);
    	sessionBuilder.addAnnotatedClasses(Supplier.class);
    	sessionBuilder.addAnnotatedClasses(Users.class);
    	sessionBuilder.addAnnotatedClasses(Product.class);
    	sessionBuilder.addAnnotatedClasses(Cart.class);
    	return sessionBuilder.buildSessionFactory();
    	
    }
    
	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(
			SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(
				sessionFactory);

		return transactionManager;
	}
    
   @Autowired
   @Bean(name = "categoryDao")
   public CategoryDAO getCategoryDao(SessionFactory sessionFactory) {
   return new CategoryDAOImpl(sessionFactory);
   }

   @Autowired
   @Bean(name = "supplierDao")
   public SupplierDAO getSupplierDao(SessionFactory sessionFactory) {
   return new SupplierDAOImpl(sessionFactory);
   }
   
   @Autowired
   @Bean(name = "productDao")
   public ProductDAO getProductDao(SessionFactory sessionFactory) {
   return new ProductDAOImpl(sessionFactory);
   }
   @Autowired
   @Bean(name = "cartDao")
   public CartDAO getCartDao(SessionFactory sessionFactory) {
   	return new CartDAOImpl(sessionFactory);
   }
}

