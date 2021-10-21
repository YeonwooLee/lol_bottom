package lol_bottom;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BottomWhole
 */
@WebServlet("/bwhole")
public class BottomWhole extends HttpServlet {
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		BottomDAO dao = new BottomDAO();
		ArrayList<BottomDto> dtoArr=new ArrayList<BottomDto>();
		try {
			dtoArr = dao.mkBtList();
			//System.out.println("111111111111111111111111111111111111111111111");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		out.print("<html><body><h1><a href='http://116.33.177.58:8080/lol_bottom/FindCounter.html'>홈으로</a></h1><table border=1>");
		out.print("<tr align='center' bgcolor='lightgreen'><td>조합</td><td>총</td><td>승</td><td>패</td><td>승률</td></tr>");
		for (int i=0;i<dtoArr.size();i++) {
			BottomDto btDto = dtoArr.get(i);
			String enemy_combi =btDto.getEnemy_combi();
			int whole = btDto.getWhole();
			int win = btDto.getWin();
			int lose = btDto.getLose();
			float win_rate = btDto.getWin_rate();
			//System.out.println(enemy_combi);
			out.print("<tr><td>"+enemy_combi+"</td><td>"+String.valueOf(whole)+"</td><td>"+String.valueOf(win)+"</td><td>"+String.valueOf(lose)+"</td><td>"+String.valueOf(win_rate)+"</td></tr>");				
		}
		out.print("</table></body></html>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
