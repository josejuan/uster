package com.uster.extra;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.apache.naming.java.javaURLContextFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

// fake backend, this is a pet project, configure multiple servers and backends is boring, this works
public class DerbyBackend {
    public static void setup() throws NamingException {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, javaURLContextFactory.class.getName());
        System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
        final InitialContext ic = new InitialContext();

        ic.createSubcontext("java:");
        ic.createSubcontext("java:comp");
        ic.createSubcontext("java:comp/env");
        ic.createSubcontext("java:comp/env/jdbc");

        final EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName("backendDB");
        ds.setCreateDatabase("create");

        ic.bind("java:comp/env/jdbc/backendDS", ds);
    }
}
