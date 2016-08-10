package org.jscrapy.core.bootcfg;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by cxu on 2016/8/5.
 */
@Configuration
@MapperScan(basePackages = {"org.jscrapy.core.dal.pg"}, sqlSessionFactoryRef = "pgSqlSessionFactory")
public class PgDatasourceConfig {
    @Value("${spring.postgresql.datasource.url}")
    private String url;
    @Value("${spring.postgresql.datasource.username}")
    private String username;
    @Value("${spring.postgresql.datasource.password}")
    private String password;
    @Value("${spring.postgresql.datasource.mapperpath}")
    private String mapperPath;

    @Bean(name = "pgDataSource")
    public DataSource pgDataSource() {
        DataSource ds = DataSourceBuilder.create().url(url).username(username).password(password).build();
        return ds;
    }

    @Bean(name = "pgTransactionManager")
    public DataSourceTransactionManager pgTransactionManager() {
        DataSourceTransactionManager txm = new DataSourceTransactionManager(pgDataSource());
        return txm;
    }

    @Bean(name = "pgSqlSessionFactory")
    public SqlSessionFactory pgSqlSessionFactory(@Qualifier("pgDataSource") DataSource h2DataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(h2DataSource);

        ResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
        Resource[] rs = resourceLoader.getResources(mapperPath);
        sessionFactory.setMapperLocations(rs);

        SqlSessionFactory ft = sessionFactory.getObject();
        return ft;
    }
}
