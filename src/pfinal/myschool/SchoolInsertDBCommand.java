package pfinal.myschool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kit.Command;
import kit.DBConnectionMgr;

public class SchoolInsertDBCommand implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");
		
		int schoolnumber = Integer.parseInt(req.getParameter("schoolnumber"));
		String schoolmajor = req.getParameter("schoolmajor");
		String schoolstate = req.getParameter("schoolstate");
		String loginid = req.getParameter("loginid");
		String schoolname = req.getParameter("schoolname");

		switch (schoolstate) {
		case "1":
			schoolstate = "����";
			break;
		case "2":
			schoolstate = "����";
			break;
		case "3":
			schoolstate = "����";
			break;
		}

		System.out.println("�б���ȣ" + schoolnumber);
		System.out.println("�α��� ���̵�" + loginid);

		Connection con;
		PreparedStatement pstmt;
		ResultSet rs;
		DBConnectionMgr pool;
		String sql;

		sql = "insert into myschool(school_id, id, school_state, school_major, school_name) values (?,?,?,?,?);";

		try {
			pool = DBConnectionMgr.getInstance();
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, schoolnumber);
			pstmt.setString(2, loginid);
			pstmt.setString(3, schoolstate);
			pstmt.setString(4, schoolmajor);
			pstmt.setString(5, schoolname);

			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "school/school_Ok.jsp";

	}
}
