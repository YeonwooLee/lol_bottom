package lol_bottom;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lol_bottom.typeAfterEight.GameResulter;

/**
 * Servlet implementation class FindCounterServlet
 */
@WebServlet("/bcounter")
public class FindCounterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindCounterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
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
		request.setCharacterEncoding("utf-8");
		BottomResulter br = new BottomResulter();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		HashMap<String, String> engToKo = new HashMap<String, String>();
		engToKo.put("아트록스", "AATROX");
		engToKo.put("아리", "AHRI");
		engToKo.put("아칼리", "AKALI");
		engToKo.put("아크샨", "AKSHAN");
		engToKo.put("알리스타", "ALISTAR");
		engToKo.put("아무무", "AMUMU");
		engToKo.put("애니비아", "ANIVIA");
		engToKo.put("애니", "ANNIE");
		engToKo.put("아펠리오스", "APHELIOS");
		engToKo.put("애쉬", "ASHE");
		engToKo.put("아우렐리온 솔", "AURELIONSOL");
		engToKo.put("아지르", "AZIR");
		engToKo.put("바드", "BARD");
		engToKo.put("블리츠크랭크", "BLITZCRANK");
		engToKo.put("브랜드", "BRAND");
		engToKo.put("브라움", "BRAUM");
		engToKo.put("케이틀린", "CAITLYN");
		engToKo.put("카밀", "CAMILLE");
		engToKo.put("카시오페아", "CASSIOPEIA");
		engToKo.put("초가스", "CHO'GATH");
		engToKo.put("코르키", "CORKI");
		engToKo.put("다리우스", "DARIUS");
		engToKo.put("다이애나", "DIANA");
		engToKo.put("드레이븐", "DRAVEN");
		engToKo.put("문도 박사", "DR.MUNDO");
		engToKo.put("에코", "EKKO");
		engToKo.put("엘리스", "ELISE");
		engToKo.put("이블린", "EVELYNN");
		engToKo.put("이즈리얼", "EZREAL");
		engToKo.put("피들스틱", "FIDDLESTICKS");
		engToKo.put("피오라", "FIORA");
		engToKo.put("피즈", "FIZZ");
		engToKo.put("갈리오", "GALIO");
		engToKo.put("갱플랭크", "GANGPLANK");
		engToKo.put("가렌", "GAREN");
		engToKo.put("나르", "GNAR");
		engToKo.put("그라가스", "GRAGAS");
		engToKo.put("그레이브즈", "GRAVES");
		engToKo.put("그웬", "GWEN");
		engToKo.put("헤카림", "HECARIM");
		engToKo.put("하이머딩거", "HEIMERDINGER");
		engToKo.put("일라오이", "ILLAOI");
		engToKo.put("이렐리아", "IRELIA");
		engToKo.put("아이번", "IVERN");
		engToKo.put("잔나", "JANNA");
		engToKo.put("자르반 4세", "JARVANIV");
		engToKo.put("잭스", "JAX");
		engToKo.put("제이스", "JAYCE");
		engToKo.put("진", "JHIN");
		engToKo.put("징크스", "JINX");
		engToKo.put("카이사", "KAI'SA");
		engToKo.put("칼리스타", "KALISTA");
		engToKo.put("카르마", "KARMA");
		engToKo.put("카서스", "KARTHUS");
		engToKo.put("카사딘", "KASSADIN");
		engToKo.put("카타리나", "KATARINA");
		engToKo.put("케일", "KAYLE");
		engToKo.put("케인", "KAYN");
		engToKo.put("케넨", "KENNEN");
		engToKo.put("카직스", "KHA'ZIX");
		engToKo.put("킨드레드", "KINDRED");
		engToKo.put("클레드", "KLED");
		engToKo.put("코그모", "KOG'MAW");
		engToKo.put("르블랑", "LEBLANC");
		engToKo.put("리 신", "LEESIN");
		engToKo.put("레오나", "LEONA");
		engToKo.put("릴리아", "LILLIA");
		engToKo.put("리산드라", "LISSANDRA");
		engToKo.put("루시안", "LUCIAN");
		engToKo.put("룰루", "LULU");
		engToKo.put("럭스", "LUX");
		engToKo.put("말파이트", "MALPHITE");
		engToKo.put("말자하", "MALZAHAR");
		engToKo.put("마오카이", "MAOKAI");
		engToKo.put("마스터 이", "MASTERYI");
		engToKo.put("미스 포츈", "MISSFORTUNE");
		engToKo.put("오공", "WUKONG");
		engToKo.put("모데카이저", "MORDEKAISER");
		engToKo.put("모르가나", "MORGANA");
		engToKo.put("나미", "NAMI");
		engToKo.put("나서스", "NASUS");
		engToKo.put("노틸러스", "NAUTILUS");
		engToKo.put("니코", "NEEKO");
		engToKo.put("니달리", "NIDALEE");
		engToKo.put("녹턴", "NOCTURNE");
		engToKo.put("누누와 윌럼프", "NUNU&WILLUMP");
		engToKo.put("올라프", "OLAF");
		engToKo.put("오리아나", "ORIANNA");
		engToKo.put("오른", "ORNN");
		engToKo.put("판테온", "PANTHEON");
		engToKo.put("뽀삐", "POPPY");
		engToKo.put("파이크", "PYKE");
		engToKo.put("키아나", "QIYANA");
		engToKo.put("퀸", "QUINN");
		engToKo.put("라칸", "RAKAN");
		engToKo.put("람머스", "RAMMUS");
		engToKo.put("렉사이", "REK'SAI");
		engToKo.put("렐", "RELL");
		engToKo.put("레넥톤", "RENEKTON");
		engToKo.put("렝가", "RENGAR");
		engToKo.put("리븐", "RIVEN");
		engToKo.put("럼블", "RUMBLE");
		engToKo.put("라이즈", "RYZE");
		engToKo.put("사미라", "SAMIRA");
		engToKo.put("세주아니", "SEJUANI");
		engToKo.put("세나", "SENNA");
		engToKo.put("세라핀", "SERAPHINE");
		engToKo.put("세트", "SETT");
		engToKo.put("샤코", "SHACO");
		engToKo.put("쉔", "SHEN");
		engToKo.put("쉬바나", "SHYVANA");
		engToKo.put("신지드", "SINGED");
		engToKo.put("사이온", "SION");
		engToKo.put("시비르", "SIVIR");
		engToKo.put("스카너", "SKARNER");
		engToKo.put("소나", "SONA");
		engToKo.put("소라카", "SORAKA");
		engToKo.put("스웨인", "SWAIN");
		engToKo.put("사일러스", "SYLAS");
		engToKo.put("신드라", "SYNDRA");
		engToKo.put("탐 켄치", "TAHMKENCH");
		engToKo.put("탈리야", "TALIYAH");
		engToKo.put("탈론", "TALON");
		engToKo.put("타릭", "TARIC");
		engToKo.put("티모", "TEEMO");
		engToKo.put("쓰레쉬", "THRESH");
		engToKo.put("트리스타나", "TRISTANA");
		engToKo.put("트런들", "TRUNDLE");
		engToKo.put("트린다미어", "TRYNDAMERE");
		engToKo.put("트위스티드 페이트", "TWISTEDFATE");
		engToKo.put("트위치", "TWITCH");
		engToKo.put("우디르", "UDYR");
		engToKo.put("우르곳", "URGOT");
		engToKo.put("바루스", "VARUS");
		engToKo.put("베인", "VAYNE");
		engToKo.put("베이가", "VEIGAR");
		engToKo.put("벨코즈", "VEL'KOZ");
		engToKo.put("벡스", "VEX");
		engToKo.put("바이", "VI");
		engToKo.put("비에고", "VIEGO");
		engToKo.put("빅토르", "VIKTOR");
		engToKo.put("블라디미르", "VLADIMIR");
		engToKo.put("볼리베어", "VOLIBEAR");
		engToKo.put("워윅", "WARWICK");
		engToKo.put("자야", "XAYAH");
		engToKo.put("제라스", "XERATH");
		engToKo.put("신 짜오", "XINZHAO");
		engToKo.put("야스오", "YASUO");
		engToKo.put("요네", "YONE");
		engToKo.put("요릭", "YORICK");
		engToKo.put("유미", "YUUMI");
		engToKo.put("자크", "ZAC");
		engToKo.put("제드", "ZED");
		engToKo.put("직스", "ZIGGS");
		engToKo.put("질리언", "ZILEAN");
		engToKo.put("조이", "ZOE");
		engToKo.put("자이라", "ZYRA");
		engToKo.put("", "");
		

		String ms=request.getParameter("mySup");
		String ma=request.getParameter("myAdc");
		String es=request.getParameter("enemySup");
		String ea=request.getParameter("enemyAdc");
		String cp = request.getParameter("chk_info"); //client position
		ms=engToKo.get(request.getParameter("mySup"));
		ma=engToKo.get(request.getParameter("myAdc"));
		es=engToKo.get(request.getParameter("enemySup"));
		ea=engToKo.get(request.getParameter("enemyAdc"));		
		


		
		int searchType = 0;
		
		if(ma.contentEquals("")
				&&!ms.contentEquals("")
				&&!ea.contentEquals("")
				&&es.contentEquals("")) {
			searchType=1;			
		}else if(!ma.contentEquals("")
				&&ms.contentEquals("")
				&&ea.contentEquals("")
				&&!es.contentEquals("")) {
			searchType=2;			
		}else if(ma.contentEquals("")
				&&!ms.contentEquals("")
				&&ea.contentEquals("")
				&&!es.contentEquals("")) {
			searchType=3;
		}else if(!ma.contentEquals("")
				&&ms.contentEquals("")
				&&!ea.contentEquals("")
				&&es.contentEquals("")) {
			searchType=4;
		}else if(ma.contentEquals("")
				&&!ms.contentEquals("")
				&&!ea.contentEquals("")
				&&!es.contentEquals("")) {
			searchType=5;
		}else if(!ma.contentEquals("")
				&&ms.contentEquals("")
				&&!ea.contentEquals("")
				&&!es.contentEquals("")) {
			searchType=6;
		}else if(!ma.contentEquals("")
				&&ms.contentEquals("")
				&&ea.contentEquals("")
				&&es.contentEquals("")) {
			searchType=7;
		}else if(ma.contentEquals("")
				&&!ms.contentEquals("")
				&&ea.contentEquals("")
				&&es.contentEquals("")) {
			searchType=8;
		}else if(ma.contentEquals("")
				&&ms.contentEquals("")
				&&!ea.contentEquals("")
				&&es.contentEquals("")) {
			if(cp.contentEquals("ADC")) {
				searchType=9;
				ea=request.getParameter("enemyAdc");
			}else if(cp.contentEquals("SUP")) {
				searchType=10;
				ea=request.getParameter("enemyAdc");
			}else {
				System.out.println(cp);
			}
		}else if(ma.contentEquals("")
				&&ms.contentEquals("")
				&&ea.contentEquals("")
				&&!es.contentEquals("")) {
			if(cp.contentEquals("ADC")) {
				searchType=11;
				es=request.getParameter("enemySup");
			}else if(cp.contentEquals("SUP")) {
				searchType=12;
				es=request.getParameter("enemySup");
			}else {
				System.out.println(cp);
			}
		}else if(ma.contentEquals("")
				&&ms.contentEquals("")
				&&!ea.contentEquals("")
				&&!es.contentEquals("")) {
			if(cp.contentEquals("ADC")) {
				searchType=14;// ********************************주의 14가 먼저있고 13이 뒤에있음
				ea=request.getParameter("enemyAdc");
				es=request.getParameter("enemySup");
			}else if(cp.contentEquals("SUP")) {
				searchType=13;
				ea=request.getParameter("enemyAdc");
				es=request.getParameter("enemySup");
			}else {
				System.out.println(cp);
			}
		}
		
		
		
		if(searchType==1) {
			//나중에 이프 tpye==one으로 처리, BottomResulter.type_one(아군서폿,상대원딜알때)
			String mySup = "'%_"+ms+"_%'"; //아군 서폿이 있는 테이블을 뽑아옵니다 
			String enemyAdc = "'"+ea+"%'";//아군 서폿이 있는 테이블 중 상대원딜이 있는 행을 가져옵니다 
			
			try {
				ArrayList<String> typeOneResult=br.type_one(mySup, enemyAdc,2);
				out.print("<html><body><h1>들어온입력:아군서폿,상대원딜</h1><h1><a href='http://116.33.177.58:8080/lol_bottom/FindCounter.html'>홈으로</a></h1><table border=1>");
				out.print("<tr align='center' bgcolor='lightgreen'><td>결과</td></tr>");
				for (int i=0;i<typeOneResult.size();i++) {
					out.print("<tr><td>"+typeOneResult.get(i)+"</td></tr>");				
				}
				out.print("</table></body></html>");
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(searchType==2) {
			//나중에 이프 tpye==two으로 처리, BottomResulter.type_one(아군원딜,상대서폿알때)
			String mySup = "'"+ma+"%'"; //아군 서폿이 있는 테이블을 뽑아옵니다 
			String enemyAdc = "'%_"+es+"_%'";//아군 서폿이 있는 테이블 중 상대원딜이 있는 행을 가져옵니다 
			
			try {
				ArrayList<String> typeOneResult=br.type_one(mySup, enemyAdc,2);
				out.print("<html><body><h1>들어온입력:아군원딜,상대서폿</h1><h1><a href='http://116.33.177.58:8080/lol_bottom/FindCounter.html'>홈으로</a></h1><table border=1>");
				out.print("<tr align='center' bgcolor='lightgreen'><td>결과</td></tr>");
				for (int i=0;i<typeOneResult.size();i++) {
					out.print("<tr><td>"+typeOneResult.get(i)+"</td></tr>");				
				}
				out.print("</table></body></html>");
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(searchType==3) { //아군서폿,상대서폿 알 때
			//나중에 이프 tpye==two으로 처리, BottomResulter.type_one(아군원딜,상대서폿알때)
			String mySup ="'%_"+ms+"_%'"; //아군 서폿이 있는 테이블을 뽑아옵니다 
			String enemyAdc = "'%_"+es+"_%'";//아군 서폿이 있는 테이블 중 상대원딜이 있는 행을 가져옵니다 
			
			try {
				ArrayList<String> typeOneResult=br.type_one(mySup, enemyAdc,2);
				out.print("<html><body><h1>들어온입력:아군서폿,상대서폿</h1><h1><a href='http://116.33.177.58:8080/lol_bottom/FindCounter.html'>홈으로</a></h1><table border=1>");
				out.print("<tr align='center' bgcolor='lightgreen'><td>결과</td></tr>");
				for (int i=0;i<typeOneResult.size();i++) {
					out.print("<tr><td>"+typeOneResult.get(i)+"</td></tr>");				
				}
				out.print("</table></body></html>");
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(searchType==4) { //아군원딜,상대원딜 알 때
			//나중에 이프 tpye==two으로 처리, BottomResulter.type_one(아군원딜,상대서폿알때)
			String mySup = "'"+ma+"%'"; //아군 서폿이 있는 테이블을 뽑아옵니다 
			String enemyAdc = "'"+ea+"%'";//아군 서폿이 있는 테이블 중 상대원딜이 있는 행을 가져옵니다 
			
			try {
				ArrayList<String> typeOneResult=br.type_one(mySup, enemyAdc,2);
				out.print("<html><body><h1>들어온입력:아군원딜,상대원딜</h1><h1><a href='http://116.33.177.58:8080/lol_bottom/FindCounter.html'>홈으로</a></h1><table border=1>");
				out.print("<tr align='center' bgcolor='lightgreen'><td>결과</td></tr>");
				for (int i=0;i<typeOneResult.size();i++) {
					out.print("<tr><td>"+typeOneResult.get(i)+"</td></tr>");				
				}
				out.print("</table></body></html>");
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(searchType==5) { //아군서폿,상대원딜서폿 알 때
			//나중에 이프 tpye==two으로 처리, BottomResulter.type_one(아군원딜,상대서폿알때)
			String mySup = "'%_"+ms+"_%'"; //아군 서폿이 있는 테이블을 뽑아옵니다 
			String enemyAdc = "'"+ea+"_"+request.getParameter("enemySup")+"%'";//아군 서폿이 있는 테이블 중 상대원딜이 있는 행을 가져옵니다 
			
			try {
				ArrayList<String> typeOneResult=br.type_one(mySup, enemyAdc,2);
				out.print("<html><body><h1>들어온입력:아군서폿,상대원딜,상대서폿</h1><h1><a href='http://116.33.177.58:8080/lol_bottom/FindCounter.html'>홈으로</a></h1><table border=1>");
				out.print("<tr align='center' bgcolor='lightgreen'><td>결과</td></tr>");
				for (int i=0;i<typeOneResult.size();i++) {
					out.print("<tr><td>"+typeOneResult.get(i)+"</td></tr>");				
				}
				out.print("</table></body></html>");
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(searchType==6) { //아군원딜,상대원딜서폿 알 때
			//나중에 이프 tpye==two으로 처리, BottomResulter.type_one(아군원딜,상대서폿알때)
			String mySup = "'"+ma+"%'"; //아군 서폿이 있는 테이블을 뽑아옵니다 
			String enemyAdc = "'"+ea+"_"+request.getParameter("enemySup")+"%'";//아군 서폿이 있는 테이블 중 상대원딜이 있는 행을 가져옵니다 
			
			try {
				ArrayList<String> typeOneResult=br.type_one(mySup, enemyAdc,2);
				out.print("<html><body><h1>들어온입력:아군원딜,상대원딜,상대서폿</h1><h1><a href='http://116.33.177.58:8080/lol_bottom/FindCounter.html'>홈으로</a></h1><table border=1>");
				out.print("<tr align='center' bgcolor='lightgreen'><td>결과</td></tr>");
				for (int i=0;i<typeOneResult.size();i++) {
					out.print("<tr><td>"+typeOneResult.get(i)+"</td></tr>");				
				}
				out.print("</table></body></html>");
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(searchType==7) { //아군원딜만 알 때
			//나중에 이프 tpye==two으로 처리, BottomResulter.type_one(아군원딜,상대서폿알때)
			String mySup = "'"+ma+"%'"; //아군 서폿이 있는 테이블을 뽑아옵니다 
			String enemyAdc = "'%%'";//아군 서폿이 있는 테이블 중 상대원딜이 있는 행을 가져옵니다 
			
			try {
				ArrayList<String> typeOneResult=br.type_one(mySup, enemyAdc,2);
				out.print("<html><body><h1>들어온입력:아군원딜</h1><h1><a href='http://116.33.177.58:8080/lol_bottom/FindCounter.html'>홈으로</a></h1><table border=1>");
				out.print("<tr align='center' bgcolor='lightgreen'><td>결과</td></tr>");
				for (int i=0;i<typeOneResult.size();i++) {
					out.print("<tr><td>"+typeOneResult.get(i)+"</td></tr>");				
				}
				out.print("</table></body></html>");
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(searchType==8) { //아군서폿만 알 때
			//나중에 이프 tpye==two으로 처리, BottomResulter.type_one(아군원딜,상대서폿알때)
			String mySup = "'%_"+ms+"_%'"; //아군 서폿이 있는 테이블을 뽑아옵니다 
			String enemyAdc = "'%%'";//아군 서폿이 있는 테이블 중 상대원딜이 있는 행을 가져옵니다 
			
			try {
				ArrayList<String> typeOneResult=br.type_one(mySup, enemyAdc,2);
				out.print("<html><body><h1>들어온입력:아군서폿</h1><h1><a href='http://116.33.177.58:8080/lol_bottom/FindCounter.html'>홈으로</a></h1><table border=1>");
				out.print("<tr align='center' bgcolor='lightgreen'><td>결과</td></tr>");
				for (int i=0;i<typeOneResult.size();i++) {
					out.print("<tr><td>"+typeOneResult.get(i)+"</td></tr>");				
				}
				out.print("</table></body></html>");
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(searchType==9) { //상대원딜알고 아군원딜 찾을때
			//나중에 이프 tpye==two으로 처리, BottomResulter.type_one(아군원딜,상대서폿알때)
			
			GameResulter t = new GameResulter();
			try {
				ArrayList<String> typeOneResult=t.danilSangdaeKnow(ea,30);
				out.print("<html><body><h1>들어온입력:상대원딜</h1><h1><a href='http://116.33.177.58:8080/lol_bottom/FindCounter.html'>홈으로</a></h1><table border=1>");
				out.print("<tr align='center' bgcolor='lightgreen'><td>결과</td></tr>");
				for (int i=0;i<typeOneResult.size();i++) {
					out.print("<tr><td>"+typeOneResult.get(i)+"</td></tr>");				
				}
				out.print("</table>");
				out.print("</body></html>");
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(searchType==10) { //상대원딜알고 아군서폿 찾을때
			//나중에 이프 tpye==two으로 처리, BottomResulter.type_one(아군원딜,상대서폿알때)
			
			GameResulter t = new GameResulter();
			try {
				ArrayList<String> typeOneResult=t.danilSangdaeKnow2(ea,30);
				out.print("<html><body><h1>들어온입력:상대원딜</h1><h1><a href='http://116.33.177.58:8080/lol_bottom/FindCounter.html'>홈으로</a></h1><table border=1>");
				out.print("<tr align='center' bgcolor='lightgreen'><td>결과</td></tr>");
				for (int i=0;i<typeOneResult.size();i++) {
					out.print("<tr><td>"+typeOneResult.get(i)+"</td></tr>");				
				}
				out.print("</table>");
				out.print("</body></html>");
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(searchType==11) { //상대서폿알고 아군원딜 찾을때
			//나중에 이프 tpye==two으로 처리, BottomResulter.type_one(아군원딜,상대서폿알때)
			
			GameResulter t = new GameResulter();
			try {
				ArrayList<String> typeOneResult=t.danilSangdaeKnow3(es,30);
				out.print("<html><body><h1>들어온입력:상대서폿</h1><h1><a href='http://116.33.177.58:8080/lol_bottom/FindCounter.html'>홈으로</a></h1><table border=1>");
				out.print("<tr align='center' bgcolor='lightgreen'><td>결과</td></tr>");
				for (int i=0;i<typeOneResult.size();i++) {
					out.print("<tr><td>"+typeOneResult.get(i)+"</td></tr>");				
				}
				out.print("</table>");
				out.print("</body></html>");
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(searchType==12) { //상대서폿알고 아군서폿 찾을때
			//나중에 이프 tpye==two으로 처리, BottomResulter.type_one(아군원딜,상대서폿알때)
			
			GameResulter t = new GameResulter();
			try {
				ArrayList<String> typeOneResult=t.danilSangdaeKnow4(es,30);
				out.print("<html><body><h1>들어온입력:상대서폿</h1><h1><a href='http://116.33.177.58:8080/lol_bottom/FindCounter.html'>홈으로</a></h1><table border=1>");
				out.print("<tr align='center' bgcolor='lightgreen'><td>결과</td></tr>");
				for (int i=0;i<typeOneResult.size();i++) {
					out.print("<tr><td>"+typeOneResult.get(i)+"</td></tr>");				
				}
				out.print("</table>");
				out.print("</body></html>");
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(searchType==13) { //상대조합알고 아군서폿 찾을때 
			//나중에 이프 tpye==two으로 처리, BottomResulter.type_one(아군원딜,상대서폿알때)
			
			GameResulter t = new GameResulter();
			try {
				ArrayList<String> typeOneResult=t.danilSangdaeKnow5(ea,es,3);
				out.print("<html><body><h1>들어온입력:상대원딜서폿</h1><h1><a href='http://116.33.177.58:8080/lol_bottom/FindCounter.html'>홈으로</a></h1><table border=1>");
				out.print("<tr align='center' bgcolor='lightgreen'><td>결과</td></tr>");
				for (int i=0;i<typeOneResult.size();i++) {
					out.print("<tr><td>"+typeOneResult.get(i)+"</td></tr>");				
				}
				out.print("</table>");
				out.print("</body></html>");
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(searchType==14) { //상대조합알고 아군원딜 찾을때
			//나중에 이프 tpye==two으로 처리, BottomResulter.type_one(아군원딜,상대서폿알때)
			
			GameResulter t = new GameResulter();
			try {
				ArrayList<String> typeOneResult=t.danilSangdaeKnow6(ea,es,3);
				out.print("<html><body><h1>들어온입력:상대원딜서폿</h1><h1><a href='http://116.33.177.58:8080/lol_bottom/FindCounter.html'>홈으로</a></h1><table border=1>");
				out.print("<tr align='center' bgcolor='lightgreen'><td>결과</td></tr>");
				for (int i=0;i<typeOneResult.size();i++) {
					out.print("<tr><td>"+typeOneResult.get(i)+"</td></tr>");				
				}
				out.print("</table>");
				out.print("</body></html>");
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else {
			out.print("<html><body><h1><a href='http://116.33.177.58:8080/lol_bottom/FindCounter.html'>아직지원x(클릭하면 홈으로 갑니다)</a></h1></body></html>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
