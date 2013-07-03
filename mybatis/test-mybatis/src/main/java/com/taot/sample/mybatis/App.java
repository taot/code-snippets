package com.taot.sample.mybatis;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.taot.sample.mybatis.bean.Account;
import com.taot.sample.mybatis.mapper.AccountMapper;

public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Started...");
        SqlSessionFactory ssf = createSqlSessionFactory();
        SqlSession session = null;
        try {
            session = ssf.openSession();
            AccountMapper mapper = session.getMapper(AccountMapper.class);
            Account account = mapper.find(1);
            System.out.println("Account: " + account.toString());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private static SqlSessionFactory createSqlSessionFactory() throws IOException {
        String mybatisConfig = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(mybatisConfig);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }
}
