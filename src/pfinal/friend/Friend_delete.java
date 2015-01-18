package pfinal.friend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.JoinDto;
import kit.Command;
import kit.DBConnectionMgr;

public class Friend_delete implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		DBConnectionMgr pool=null;
		
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("id");
		String[] fid = req.getParameterValues("fid");
		int del = Integer.parseInt(req.getParameter("del"));
		System.out.println("�����̵�"+id);
		System.out.println("ģ�����̵�"+fid.length);
		System.out.println("��ư!"+del);
		
		
		Vector v = new Vector();
		Vector frv = new Vector();
		
		try {
			pool = DBConnectionMgr.getInstance();
			
			// ���� 
			
			String sql = "delete from friendlist where (friend_id='" + fid[del-1]
					+ "' and user_id='" + id + "') OR (friend_id='"
					+ id + "' AND user_id='" + fid[del-1] + "')";
			System.out.println(sql);
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.executeUpdate();

			// ģ�����
			sql = "SELECT * FROM members WHERE id IN ("
					+ "SELECT friend_id FROM friendlist "
					+ "WHERE (invited=1 AND user_id = '" + id
					+ "')) ORDER BY name";
			System.out.println(sql);
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				JoinDto dto = new JoinDto();
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setAge(rs.getInt("age"));
				dto.setTel(rs.getString("tel"));
				dto.setGender(rs.getInt("gender"));
				// System.out.println(dto.getId());
				v.add(dto);
			}

			// ģ����û ���
			sql = "SELECT * FROM members WHERE id IN (select friend_id from friendlist where invited=0 AND user_id='"
					+ id + "')";
			System.out.println(sql);
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				JoinDto dto = new JoinDto();
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setAge(rs.getInt("age"));
				dto.setTel(rs.getString("tel"));
				dto.setGender(rs.getInt("gender"));
				// System.out.println(dto.getMember_id());
				frv.add(dto);
			}
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		req.setAttribute("frvector", frv);
		req.setAttribute("vector", v);
		System.out.println("Delete ���");

		return "/pr_friends/Friend_List.jsp";
	}

}