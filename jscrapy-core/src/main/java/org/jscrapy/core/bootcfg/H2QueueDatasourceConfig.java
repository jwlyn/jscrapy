package org.jscrapy.core.bootcfg;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by cxu on 2016/8/4.
 */
@Configuration
@MapperScan(basePackages = {"org.jscrapy.core.dal.h2"}, sqlSessionFactoryRef = "h2QueueSqlSessionFactory")
public class H2QueueDatasourceConfig {

    @Value("${spring.h2.queue.datasource.url}")
    private String url;
    @Value("${spring.h2.queue.datasource.username}")
    private String username;
    @Value("${spring.h2.queue.datasource.password}")
    private String password;
    @Value("${spring.h2.queue.datasource.mapperpath}")
    private String mapperPath;

    @Bean(name = "h2DataSource")
    @Primary
    public DataSource h2DataSource() {
        DataSource ds = DataSourceBuilder.create().url(url).username(username).password(password).build();
        return ds;
    }

    @Bean(name = "h2QueueTransactionManager")
    @Primary
    public DataSourceTransactionManager h2TransactionManager() {
        DataSourceTransactionManager txm = new DataSourceTransactionManager(h2DataSource());
        return txm;
    }

    @Bean(name = "h2QueueSqlSessionFactory")
    @Primary
    public SqlSessionFactory h2SqlSessionFactory(@Qualifier("h2DataSource") DataSource h2DataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(h2DataSource);

        ResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
        Resource[] rs = resourceLoader.getResources(mapperPath);
        sessionFactory.setMapperLocations(rs);

        SqlSessionFactory ft = sessionFactory.getObject();
        return ft;
    }
}
