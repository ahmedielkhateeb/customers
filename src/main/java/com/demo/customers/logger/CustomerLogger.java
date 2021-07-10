package com.demo.customers.logger;

import com.demo.customers.CustomersApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerLogger {
    public static Logger log = LoggerFactory.getLogger(CustomersApplication.class);
}
