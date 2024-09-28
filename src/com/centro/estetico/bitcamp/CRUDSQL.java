package com.centro.estetico.bitcamp;

import java.sql.*;

public interface CRUDSQL {
	int insertData();
	ResultSet getData();
	int updateData();
	int deleteData();
}
