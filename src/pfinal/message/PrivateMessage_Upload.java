package pfinal.message;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kit.Command;
import kit.DBConnectionMgr;

public class PrivateMessage_Upload implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		System.out.println("���ε� ����");
		
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");
		
		String msgpr_id = req.getParameter("msgpr_id");
		System.out.println("�����̺� �޽��� :"+msgpr_id);
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DBConnectionMgr pool = null;
		String sql = null;
		
		String resfriend = "";
		String[] resfriends = req.getParameterValues("friends1");
		
		for (int i = 0; i < resfriends.length; i++) {
			resfriend += resfriends[i];
			if (i < resfriends.length - 1) {
				resfriend += ',';
			}
		}
		System.out.println("�迭���� ���ڿ��� ����"+resfriend);
		
		String friends[] = resfriend.split(",");

		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("id");
		String msg_content = req.getParameter("msg_content"); 

		for(int i=0; i<friends.length;i++){

			try {
				pool = DBConnectionMgr.getInstance();

			
				sql = "INSERT INTO message(send_id, receive_id, msg_contents, send_date, read_status, privatepr_id) VALUES('"
						+id + "', '"+friends[i]+ "', '"+ msg_content	+ "', now(), 0, '"+msgpr_id+"')";
				 System.out.println(sql);

				con = pool.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
			} catch (Exception err) {
				err.printStackTrace();
			} finally {
				pool.freeConnection(con, pstmt, rs);
			}
			System.out.println("����� �޽��� ģ�����"+friends[i]);
		}
		
		System.out.println("����� �޽��� ���ε� �Ϸ�");
		return "/pr_list/PrivateList.jsp";
	}

}
