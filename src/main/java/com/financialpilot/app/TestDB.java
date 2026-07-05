
package com.financialpilot.app;

import com.financialpilot.database.DBConnection;

public class TestDB {

    public static void main(String[] args) {

        System.out.println(DBConnection.getConnection());

    }
}