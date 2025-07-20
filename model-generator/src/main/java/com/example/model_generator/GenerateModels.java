package com.example.model_generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class GenerateModels {
    // Thông tin kết nối cố định
    private static final String HOST     = "cleanmate-server.database.windows.net";
    private static final String PORT     = "1433";
    private static final String DATABASE = "CleanMateDB_PRM";
    private static final String USER     = "cleanmateadmin@cleanmate-server";

    // Package đầu ra cho các class model
    private static final String OUTPUT_PACKAGE = "com.example.cleanmate.data.model";
    // Thư mục gốc để lưu .java, đồng bộ với PACKAGE trên
    private static final String OUTPUT_DIR =
            "app/src/main/java/" + OUTPUT_PACKAGE.replace('.', '/') + "/";

    public static void main(String[] args) throws Exception {

        String password = "shinnosuke04@";

        // Tạo thư mục nếu chưa tồn tại
        File outDir = new File(OUTPUT_DIR);
        if (!outDir.exists() && !outDir.mkdirs()) {
            throw new IOException("Cannot create output directory: " + OUTPUT_DIR);
        }

        // Load driver jTDS
        Class.forName("net.sourceforge.jtds.jdbc.Driver");

        // Build URL jTDS
        String url = String.format(
                "jdbc:jtds:sqlserver://%s:%s/%s;ssl=require;user=%s;password=%s;",
                HOST, PORT, DATABASE, USER, password
        );

        try (Connection conn = DriverManager.getConnection(url)) {
            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet tables = meta.getTables(null, "dbo", "%", new String[]{"TABLE"})) {
                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");
                    generateModelForTable(meta, tableName);
                }
            }
        }

        System.out.println("Done generating models in " + OUTPUT_DIR);
    }

    private static void generateModelForTable(DatabaseMetaData meta, String tableName)
            throws SQLException, IOException {
        String className = toCamelCase(tableName, true);
        String filePath  = OUTPUT_DIR + className + ".java";

        try (FileWriter fw = new FileWriter(filePath)) {
            // package
            fw.write("package " + OUTPUT_PACKAGE + ";\n\n");

            // class header
            fw.write("public class " + className + " {\n\n");

            // Thu thập cột và sinh field
            Map<String,String> fields = new HashMap<>();
            try (ResultSet cols = meta.getColumns(null, "dbo", tableName, "%")) {
                while (cols.next()) {
                    String colName   = cols.getString("COLUMN_NAME");
                    int    sqlType   = cols.getInt("DATA_TYPE");
                    String javaType  = sqlTypeToJava(sqlType);
                    String fieldName = toCamelCase(colName, false);
                    fields.put(colName, fieldName);
                    fw.write("    private " + javaType + " " + fieldName + ";\n");
                }
            }
            fw.write("\n");

            // Sinh getter và setter
            for (Map.Entry<String,String> e : fields.entrySet()) {
                String col = e.getKey(), fld = e.getValue();
                // Lấy lại javaType cho cột
                int    sqlType  = meta.getColumns(null, "dbo", tableName, col).getInt("DATA_TYPE");
                String javaType = sqlTypeToJava(sqlType);
                String cap      = toCamelCase(col, true);

                // Getter
                fw.write("    public " + javaType + " get" + cap + "() {\n");
                fw.write("        return " + fld + ";\n    }\n\n");

                // Setter
                fw.write("    public void set" + cap + "(" + javaType + " " + fld + ") {\n");
                fw.write("        this." + fld + " = " + fld + ";\n    }\n\n");
            }

            fw.write("}\n");
        }

        System.out.println("Generated: " + filePath);
    }

    private static String sqlTypeToJava(int sqlType) {
        switch (sqlType) {
            case Types.INTEGER:     return "Integer";
            case Types.BIGINT:      return "Long";
            case Types.FLOAT:
            case Types.REAL:
            case Types.DOUBLE:      return "Double";
            case Types.DECIMAL:
            case Types.NUMERIC:     return "java.math.BigDecimal";
            case Types.BIT:
            case Types.BOOLEAN:     return "Boolean";
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR: return "String";
            case Types.DATE:        return "java.sql.Date";
            case Types.TIMESTAMP:   return "java.sql.Timestamp";
            default:                return "String";
        }
    }

    private static String toCamelCase(String s, boolean capitalizeFirst) {
        StringBuilder sb    = new StringBuilder();
        boolean       nextCap = capitalizeFirst;
        for (char c : s.toCharArray()) {
            if (c == '_' || c == ' ') {
                nextCap = true;
            } else if (nextCap) {
                sb.append(Character.toUpperCase(c));
                nextCap = false;
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }
}
