package com.taot.sample.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
            
            {
                Account account = mapper.find(1);
                System.out.println("select: " + account.toString());
            }
            
            {
                List<Account> accounts = mapper.findAll();
                System.out.println("selectAll:");
                for (Account a : accounts) {
                    System.out.println("    " + a.toString());
                }
            }
            
            {
                Account account = new Account();
                account.setId(5);
                account.setOwner("Potato");
                boolean result = mapper.insert(account);
                System.out.println("insert result: " + result);
                session.commit();
            }
            
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
