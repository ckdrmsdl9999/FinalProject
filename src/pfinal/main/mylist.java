package pfinal.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kit.Command;
import kit.DBConnectionMgr;
import dto.listDto;
import dto.quDto;

public class mylist implements Command {
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	DBConnectionMgr pool;

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");
		String sql = null;
		int year=0,month=0;
		Vector d_day = new Vector();
		Vector skills = new Vector();
		Vector qv = new Vector();
		
		HttpSession session = req.getSession();
		String id = (String) session.getAttribute("id");
		
		try {

			pool = DBConnectionMgr.getInstance();
			con = pool.getConnection();
			sql = "select (to_days(resign)-to_days(joins)) as day , cname , part , position from career where id = ?";			
			pstmt = con.prepareStatement(sql);			
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				listDto list = new listDto();
				list.setCname(rs.getString("cname"));
				list.setPart(rs.getString("part"));
				list.setPosition(rs.getString("position"));
				int day = rs.getInt("day");
				if(day>365){
					year = day/365;
					day = day - (365*year);
					list.setYear(year);
					System.out.println("연" + day);
				}
				if(day>30){
					month = day/30;
					day = day - (30*month);
					list.setMonth(month);
					System.out.println("월" + day);
				}
				if(day>0){
					list.setDay(day);
					System.out.println("마지막" + day);
				}
				d_day.add(list);
			}
			
			sql = "select * from skill where id = ?";			
			pstmt = con.prepareStatement(sql);			
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				listDto list2 = new listDto();
				list2.setSkill(rs.getString("skill"));
				list2.setExp(rs.getInt("exp"));
				list2.setMastery(rs.getInt("mastery"));
				skills.add(list2);
			}
			
			sql = "select * from quali where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()){
				quDto qdto = new quDto();
				qdto.setQ_name(rs.getString("q_name"));
				qdto.setQ_house(rs.getString("q_house"));
				qdto.setQ_num(rs.getInt("q_num"));
				qdto.setQ_date(rs.getString("q_date"));
				qv.add(qdto);
				
				System.out.println(qdto.getQ_date()+"자격증날짜");
			}
						
		} catch (Exception err) {
			System.out.println("getBoard() : " + err);
			err.printStackTrace();
		}
		finally{
			pool.freeConnection(con, pstmt, rs);
		}
		req.setAttribute("d_day", d_day);
		req.setAttribute("skills1", skills);
		req.setAttribute("qv", qv);
		return "pr_mypage/mylist.jsp";

	}
}
