package pfinal.Project.RF;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kit.Command;
import kit.DBConnectionMgr;
import dto.rfDto;

public class Project_Fin implements Command {

	@Override
	public Object processCommand(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		System.out.println("완료 프로젝트 평가 안된거 보기");
		req.setCharacterEncoding("utf-8");
		res.setCharacterEncoding("utf-8");
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		DBConnectionMgr pool=null;;
		
		String pr_id = req.getParameter("pr_id");
		System.out.println(pr_id);
		String sql, fr_id;
		String[] fr_ids;
		rfDto fin_pro;
		rfDto run_fin_pro=null;
		Vector v = new Vector();
		Vector v2 = new Vector();
		String flag = "N";
		try{
			pool = DBConnectionMgr.getInstance();
			sql = "select * from finish_project where pr_id="+pr_id;
			System.out.println("finish_project :" + sql);
			con = pool.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			rs.next();
			fin_pro = new rfDto();
			fr_id = rs.getString("fr_id");
			fr_ids = fr_id.split(",");
			System.out.println(fr_ids[0]);
			req.setAttribute("pr_id", rs.getString("pr_id"));
			req.setAttribute("fr_id", fr_id);
				
//				System.out.println("ㅊㅊ");
			
			sql = "select pr_comment, evaluate, pr_subject, start_day, fin_price, fr_id, Floor((to_days(now())-to_days(start_day)))as total_day  from runing_finish_project where pr_id="+pr_id;
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				run_fin_pro = new rfDto();
				run_fin_pro.setComment(rs.getString("pr_comment"));
				run_fin_pro.setPr_subject(rs.getString("pr_subject"));
				run_fin_pro.setStart_day(rs.getString("start_day"));
//				run_fin_pro.setEnd_day(rs.getString("end_day"));
				run_fin_pro.setFin_price(rs.getString("fin_price"));
				run_fin_pro.setFr_id(rs.getString("fr_id"));
				run_fin_pro.setTotal_day(rs.getShort("total_day"));
				
				v2.add(run_fin_pro);
//				System.out.println("ㅊㅊ2");
				flag = "F";
			}
			
//			System.out.println("ㅜㅜ");
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			pool.freeConnection(con,pstmt,rs);
		}
		req.setAttribute("Dto1", run_fin_pro);
		req.setAttribute("dtoGetBoard", v);
		req.setAttribute("dtoGetBoard2", v2);
		req.setAttribute("flag", flag);
		
		System.out.println("프로젝트완료로 고고");
		
		return "pr_running_finish/Project_View.jsp";
	}

}
