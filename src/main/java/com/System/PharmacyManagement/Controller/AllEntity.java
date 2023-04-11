package com.System.PharmacyManagement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/backend/AllEntity")
public class AllEntity {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/tables")
    public List<String> getAllTables() {
        List<String> tableNames = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, null, new String[] {"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                List<String> columnNames = new ArrayList<>();
                ResultSet columns = metaData.getColumns(null, null, tableName, null);
                while (columns.next()) {
                    columnNames.add(columns.getString("COLUMN_NAME"));
                }
                String tableInfo = tableName + ": " + columnNames.toString();
                tableNames.add(tableInfo);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableNames;
    }
}
