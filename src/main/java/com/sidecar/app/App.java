package com.sidecar.app;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sidecar.model.JenkinsJob;
import com.sidecar.util.HibernateUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import org.hibernate.Session;
import org.hibernate.Query;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {

    private static final Logger logger = LogManager.getLogger(App.class);
    private static Session session;

    public static void main(String[] args) throws Exception {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        // create job
        JenkinsJob job = new JenkinsJob();
		job.setJob("Job");
		job.setInsertTime(new Date());
        session.save(job);

        Runnable jenkinsJobRunnable = new Runnable() {
            public void run() {
                String hql = "FROM JenkinsJob";
                Query query = session.createQuery(hql);
                query.setMaxResults(1);
                List results = query.list();
                System.out.println("JenkinsJob="+results.toString());
                // HibernateUtil.getSessionFactory().close();
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(jenkinsJobRunnable, 0, 10, TimeUnit.SECONDS);
    }


}
