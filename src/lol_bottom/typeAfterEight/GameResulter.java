package lol_bottom.typeAfterEight;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import lol_bottom.BottomDto;
import lol_bottom.LolBotResultComparator;

public class GameResulter {
	//상대원딜만 알 때, 무슨 원딜 해야되는지
	public ArrayList<String> danilSangdaeKnow(String champname,int minPanSoo)  throws ClassNotFoundException, SQLException {
		GameDAO a = new GameDAO(champname);
		ArrayList<String> result = new ArrayList<String>();
		
		ArrayList<GameDTO> arr = a.listGames();//케이틀린이 있었던 모든 게임을 가져와 arr에 넣습니다
		HashMap<String,BottomDto> map = new HashMap<String,BottomDto>();//케이틀린 상대전적 계산을 위한 해쉬맵입니다
		
		boolean mainChampWin;
		boolean subChampWin;
		String mainChamp=a.champ;
		String subChamp = null;
		
		//객체원딜의 카운터 원딜을 구하는 것
		for(int i=0;i<arr.size();i++) { //gamedto 반복문 돌립니다
			GameDTO nowGame=arr.get(i);
			if(nowGame.getBadc().contentEquals(mainChamp)){//메인챔이 블루팀 원딜인가?
				subChamp=nowGame.getRadc();//서브챔은 레드팀 원딜임
				if(nowGame.getBwin().contentEquals("1")) {//블루팀이 이겼나?
					mainChampWin=true;
					subChampWin=false;
					
				}else {
					mainChampWin=false;
					subChampWin=true;
				}
			}else {//메인챔이 블루팀원딜이 아닌가? == 메인챔은 레드팀원딜인가?
				subChamp=nowGame.getBadc();//서브챔은 블루팀 원딜임
				if(nowGame.getRwin().contentEquals("1")) {//레드팀이  이겼나?
					mainChampWin=true;
					subChampWin=false;
				}else {
					mainChampWin=false;
					subChampWin=true;
				}
			}
			
			
			if(mainChampWin) {//메인챔이 이겼나?
				if(map.containsKey(subChamp)) {//서브챔이 map에 키로 있냐?
					map.get(subChamp).whole+=1;//총+=1
					map.get(subChamp).win+=1;//서브챔에대한승+=1
					//승률계산
					map.get(subChamp).win_rate=(float) (Math.round(map.get(subChamp).win/map.get(subChamp).whole*10000)/100.0);
				}else {//서브챔이 map에 키로 없냐?
					BottomDto temp = new BottomDto(); //임시 bottomdto만들어서 1전1승0패 100.0 세팅
					temp.whole=1;
					temp.win=1;
					temp.lose=0;
					temp.win_rate=(float) 100.0;
					map.put(subChamp, temp);//map에 서브챔,bttomdto put
				}
			}else {//메인챔이 졌냐?
				if(map.containsKey(subChamp)) {//서브챔이 map에 키로 있냐?
					map.get(subChamp).whole+=1;//총+=1
					map.get(subChamp).lose+=1;//서브챔에대한패+=1
					//승률계산
					map.get(subChamp).win_rate=(float) (Math.round(map.get(subChamp).win/map.get(subChamp).whole*10000)/100.0);
				}else {//서브챔이 map에 키로 없냐?
					BottomDto temp = new BottomDto(); //임시 bottomdto만들어서 1전0승1패 0.0 세팅
					temp.whole=1;
					temp.win=0;
					temp.lose=1;
					temp.win_rate=(float) 0.0;
					map.put(subChamp, temp);//map에 서브챔,bttomdto put
				}
			}
		}
		//찐최종 결과물, 이거 리스트화 해서 리턴하면됨
		for (Entry<String,BottomDto> entrySet: map.entrySet()) {
			//System.out.println(entrySet.getKey()
			//		+" :: "+entrySet.getValue().Enemy_combi
			//		+","+entrySet.getValue().whole
			//		+","+entrySet.getValue().win
			//		+","+entrySet.getValue().lose
			//		+","+Math.round(((float)entrySet.getValue().win/(float)entrySet.getValue().whole)*10000)/100.0
			//		);

			result.add(mainChamp
					+" :: "+entrySet.getKey()
					+","+entrySet.getValue().whole
					+","+entrySet.getValue().win
					+","+entrySet.getValue().lose
					+","+Math.round(((float)entrySet.getValue().win/(float)entrySet.getValue().whole)*10000)/100.0
					);
		}
		
		
		Collections.sort(result,new DanilComparator());
		//판 이하인 애들 제거
		ArrayList<String> tempForDel = new ArrayList<String>();
		for(int i=0;i<result.size();i++) {
			String[] split_result = result.get(i).split(",");
			int panSoo=Integer.parseInt(split_result[1]);
			
			if (panSoo<minPanSoo) { //<<<<<<여기가 판수
				tempForDel.add(result.get(i));
			}
		}for(int i=0;i<tempForDel.size();i++) {
			result.remove(tempForDel.get(i));
		}
		
		
		System.out.println("EEEEE");
		for(int i=0;i<result.size();i++) {
			System.out.println(result.get(i));
		}
		return result;
	}
	//상대원딜만 알 때, 무슨 서폿 해야되는지
	public ArrayList<String> danilSangdaeKnow2(String champname,int minPanSoo)  throws ClassNotFoundException, SQLException {
		GameDAO a = new GameDAO(champname);
		ArrayList<String> result = new ArrayList<String>();
		
		ArrayList<GameDTO> arr = a.listGames();//케이틀린이 있었던 모든 게임을 가져와 arr에 넣습니다
		HashMap<String,BottomDto> map = new HashMap<String,BottomDto>();//케이틀린 상대전적 계산을 위한 해쉬맵입니다
		
		boolean mainChampWin;
		boolean subChampWin;
		String mainChamp=a.champ;
		String subChamp = null;
		
		//객체원딜의 카운터 원딜을 구하는 것
		for(int i=0;i<arr.size();i++) { //gamedto 반복문 돌립니다
			GameDTO nowGame=arr.get(i);
			if(nowGame.getBadc().contentEquals(mainChamp)){//메인챔이 블루팀 원딜인가?
				subChamp=nowGame.getRsup();//서브챔은 레드팀 원딜임
				if(nowGame.getBwin().contentEquals("1")) {//블루팀이 이겼나?
					mainChampWin=true;
					subChampWin=false;
					
				}else {
					mainChampWin=false;
					subChampWin=true;
				}
			}else {//메인챔이 블루팀원딜이 아닌가? == 메인챔은 레드팀원딜인가?
				subChamp=nowGame.getBsup();//서브챔은 블루팀 원딜임
				if(nowGame.getRwin().contentEquals("1")) {//레드팀이  이겼나?
					mainChampWin=true;
					subChampWin=false;
				}else {
					mainChampWin=false;
					subChampWin=true;
				}
			}
			
			
			if(mainChampWin) {//메인챔이 이겼나?
				if(map.containsKey(subChamp)) {//서브챔이 map에 키로 있냐?
					map.get(subChamp).whole+=1;//총+=1
					map.get(subChamp).win+=1;//서브챔에대한승+=1
					//승률계산
					map.get(subChamp).win_rate=(float) (Math.round(map.get(subChamp).win/map.get(subChamp).whole*10000)/100.0);
				}else {//서브챔이 map에 키로 없냐?
					BottomDto temp = new BottomDto(); //임시 bottomdto만들어서 1전1승0패 100.0 세팅
					temp.whole=1;
					temp.win=1;
					temp.lose=0;
					temp.win_rate=(float) 100.0;
					map.put(subChamp, temp);//map에 서브챔,bttomdto put
				}
			}else {//메인챔이 졌냐?
				if(map.containsKey(subChamp)) {//서브챔이 map에 키로 있냐?
					map.get(subChamp).whole+=1;//총+=1
					map.get(subChamp).lose+=1;//서브챔에대한패+=1
					//승률계산
					map.get(subChamp).win_rate=(float) (Math.round(map.get(subChamp).win/map.get(subChamp).whole*10000)/100.0);
				}else {//서브챔이 map에 키로 없냐?
					BottomDto temp = new BottomDto(); //임시 bottomdto만들어서 1전0승1패 0.0 세팅
					temp.whole=1;
					temp.win=0;
					temp.lose=1;
					temp.win_rate=(float) 0.0;
					map.put(subChamp, temp);//map에 서브챔,bttomdto put
				}
			}
		}
		//찐최종 결과물, 이거 리스트화 해서 리턴하면됨
		for (Entry<String,BottomDto> entrySet: map.entrySet()) {
			//System.out.println(entrySet.getKey()
			//		+" :: "+entrySet.getValue().Enemy_combi
			//		+","+entrySet.getValue().whole
			//		+","+entrySet.getValue().win
			//		+","+entrySet.getValue().lose
			//		+","+Math.round(((float)entrySet.getValue().win/(float)entrySet.getValue().whole)*10000)/100.0
			//		);

			result.add(mainChamp
					+" :: "+entrySet.getKey()
					+","+entrySet.getValue().whole
					+","+entrySet.getValue().win
					+","+entrySet.getValue().lose
					+","+Math.round(((float)entrySet.getValue().win/(float)entrySet.getValue().whole)*10000)/100.0
					);
		}
		
		
		Collections.sort(result,new DanilComparator());
		//판 이하인 애들 제거
		ArrayList<String> tempForDel = new ArrayList<String>();
		for(int i=0;i<result.size();i++) {
			String[] split_result = result.get(i).split(",");
			int panSoo=Integer.parseInt(split_result[1]);
			
			if (panSoo<minPanSoo) { //<<<<<<여기가 판수
				tempForDel.add(result.get(i));
			}
		}for(int i=0;i<tempForDel.size();i++) {
			result.remove(tempForDel.get(i));
		}
		
		
		System.out.println("EEEEE");
		for(int i=0;i<result.size();i++) {
			System.out.println(result.get(i));
		}
		return result;
	}
	//상대서폿만 알 때, 무슨 원딜 해야되는지
	public ArrayList<String> danilSangdaeKnow3(String champname,int minPanSoo)  throws ClassNotFoundException, SQLException {
		GameDAO a = new GameDAO(champname);
		ArrayList<String> result = new ArrayList<String>();
		
		ArrayList<GameDTO> arr = a.listGames2();//케이틀린이 있었던 모든 게임을 가져와 arr에 넣습니다
		HashMap<String,BottomDto> map = new HashMap<String,BottomDto>();//케이틀린 상대전적 계산을 위한 해쉬맵입니다
		
		boolean mainChampWin;
		boolean subChampWin;
		String mainChamp=a.champ;
		String subChamp = null;
		
		//객체원딜의 카운터 원딜을 구하는 것
		for(int i=0;i<arr.size();i++) { //gamedto 반복문 돌립니다
			GameDTO nowGame=arr.get(i);
			if(nowGame.getBsup().contentEquals(mainChamp)){//메인챔이 블루팀 서폿인가?
				subChamp=nowGame.getRadc();//서브챔은 레드팀 원딜임
				if(nowGame.getBwin().contentEquals("1")) {//블루팀이 이겼나?
					mainChampWin=true;
					subChampWin=false;
					
				}else {
					mainChampWin=false;
					subChampWin=true;
				}
			}else {//메인챔이 블루팀서폿이 아닌가? == 메인챔은 레드팀서폿인가?
				subChamp=nowGame.getBadc();//서브챔은 블루팀 원딜임
				if(nowGame.getRwin().contentEquals("1")) {//레드팀이  이겼나?
					mainChampWin=true;
					subChampWin=false;
				}else {
					mainChampWin=false;
					subChampWin=true;
				}
			}
			
			
			if(mainChampWin) {//메인챔이 이겼나?
				if(map.containsKey(subChamp)) {//서브챔이 map에 키로 있냐?
					map.get(subChamp).whole+=1;//총+=1
					map.get(subChamp).win+=1;//서브챔에대한승+=1
					//승률계산
					map.get(subChamp).win_rate=(float) (Math.round(map.get(subChamp).win/map.get(subChamp).whole*10000)/100.0);
				}else {//서브챔이 map에 키로 없냐?
					BottomDto temp = new BottomDto(); //임시 bottomdto만들어서 1전1승0패 100.0 세팅
					temp.whole=1;
					temp.win=1;
					temp.lose=0;
					temp.win_rate=(float) 100.0;
					map.put(subChamp, temp);//map에 서브챔,bttomdto put
				}
			}else {//메인챔이 졌냐?
				if(map.containsKey(subChamp)) {//서브챔이 map에 키로 있냐?
					map.get(subChamp).whole+=1;//총+=1
					map.get(subChamp).lose+=1;//서브챔에대한패+=1
					//승률계산
					map.get(subChamp).win_rate=(float) (Math.round(map.get(subChamp).win/map.get(subChamp).whole*10000)/100.0);
				}else {//서브챔이 map에 키로 없냐?
					BottomDto temp = new BottomDto(); //임시 bottomdto만들어서 1전0승1패 0.0 세팅
					temp.whole=1;
					temp.win=0;
					temp.lose=1;
					temp.win_rate=(float) 0.0;
					map.put(subChamp, temp);//map에 서브챔,bttomdto put
				}
			}
		}
		//찐최종 결과물, 이거 리스트화 해서 리턴하면됨
		for (Entry<String,BottomDto> entrySet: map.entrySet()) {
			//System.out.println(entrySet.getKey()
			//		+" :: "+entrySet.getValue().Enemy_combi
			//		+","+entrySet.getValue().whole
			//		+","+entrySet.getValue().win
			//		+","+entrySet.getValue().lose
			//		+","+Math.round(((float)entrySet.getValue().win/(float)entrySet.getValue().whole)*10000)/100.0
			//		);

			result.add(mainChamp
					+" :: "+entrySet.getKey()
					+","+entrySet.getValue().whole
					+","+entrySet.getValue().win
					+","+entrySet.getValue().lose
					+","+Math.round(((float)entrySet.getValue().win/(float)entrySet.getValue().whole)*10000)/100.0
					);
		}
		
		
		Collections.sort(result,new DanilComparator());
		//판 이하인 애들 제거
		ArrayList<String> tempForDel = new ArrayList<String>();
		for(int i=0;i<result.size();i++) {
			String[] split_result = result.get(i).split(",");
			int panSoo=Integer.parseInt(split_result[1]);
			
			if (panSoo<minPanSoo) { //<<<<<<여기가 판수
				tempForDel.add(result.get(i));
			}
		}for(int i=0;i<tempForDel.size();i++) {
			result.remove(tempForDel.get(i));
		}
		
		
		System.out.println("EEEEE");
		for(int i=0;i<result.size();i++) {
			System.out.println(result.get(i));
		}
		return result;
	}
	//상대서폿만 알 때, 무슨 서폿 해야되는지
	public ArrayList<String> danilSangdaeKnow4(String champname,int minPanSoo)  throws ClassNotFoundException, SQLException {
		GameDAO a = new GameDAO(champname);
		ArrayList<String> result = new ArrayList<String>();
		
		ArrayList<GameDTO> arr = a.listGames2();//케이틀린이 있었던 모든 게임을 가져와 arr에 넣습니다
		HashMap<String,BottomDto> map = new HashMap<String,BottomDto>();//케이틀린 상대전적 계산을 위한 해쉬맵입니다
		
		boolean mainChampWin;
		boolean subChampWin;
		String mainChamp=a.champ;
		String subChamp = null;
		
		//객체원딜의 카운터 원딜을 구하는 것
		for(int i=0;i<arr.size();i++) { //gamedto 반복문 돌립니다
			GameDTO nowGame=arr.get(i);
			if(nowGame.getBsup().contentEquals(mainChamp)){//메인챔이 블루팀 서폿인가?
				subChamp=nowGame.getRsup();//서브챔은 레드팀 원딜임
				if(nowGame.getBwin().contentEquals("1")) {//블루팀이 이겼나?
					mainChampWin=true;
					subChampWin=false;
					
				}else {
					mainChampWin=false;
					subChampWin=true;
				}
			}else {//메인챔이 블루팀서폿이 아닌가? == 메인챔은 레드팀서폿인가?
				subChamp=nowGame.getBsup();//서브챔은 블루팀 원딜임
				if(nowGame.getRwin().contentEquals("1")) {//레드팀이  이겼나?
					mainChampWin=true;
					subChampWin=false;
				}else {
					mainChampWin=false;
					subChampWin=true;
				}
			}
			
			
			if(mainChampWin) {//메인챔이 이겼나?
				if(map.containsKey(subChamp)) {//서브챔이 map에 키로 있냐?
					map.get(subChamp).whole+=1;//총+=1
					map.get(subChamp).win+=1;//서브챔에대한승+=1
					//승률계산
					map.get(subChamp).win_rate=(float) (Math.round(map.get(subChamp).win/map.get(subChamp).whole*10000)/100.0);
				}else {//서브챔이 map에 키로 없냐?
					BottomDto temp = new BottomDto(); //임시 bottomdto만들어서 1전1승0패 100.0 세팅
					temp.whole=1;
					temp.win=1;
					temp.lose=0;
					temp.win_rate=(float) 100.0;
					map.put(subChamp, temp);//map에 서브챔,bttomdto put
				}
			}else {//메인챔이 졌냐?
				if(map.containsKey(subChamp)) {//서브챔이 map에 키로 있냐?
					map.get(subChamp).whole+=1;//총+=1
					map.get(subChamp).lose+=1;//서브챔에대한패+=1
					//승률계산
					map.get(subChamp).win_rate=(float) (Math.round(map.get(subChamp).win/map.get(subChamp).whole*10000)/100.0);
				}else {//서브챔이 map에 키로 없냐?
					BottomDto temp = new BottomDto(); //임시 bottomdto만들어서 1전0승1패 0.0 세팅
					temp.whole=1;
					temp.win=0;
					temp.lose=1;
					temp.win_rate=(float) 0.0;
					map.put(subChamp, temp);//map에 서브챔,bttomdto put
				}
			}
		}
		//찐최종 결과물, 이거 리스트화 해서 리턴하면됨
		for (Entry<String,BottomDto> entrySet: map.entrySet()) {
			//System.out.println(entrySet.getKey()
			//		+" :: "+entrySet.getValue().Enemy_combi
			//		+","+entrySet.getValue().whole
			//		+","+entrySet.getValue().win
			//		+","+entrySet.getValue().lose
			//		+","+Math.round(((float)entrySet.getValue().win/(float)entrySet.getValue().whole)*10000)/100.0
			//		);

			result.add(mainChamp
					+" :: "+entrySet.getKey()
					+","+entrySet.getValue().whole
					+","+entrySet.getValue().win
					+","+entrySet.getValue().lose
					+","+Math.round(((float)entrySet.getValue().win/(float)entrySet.getValue().whole)*10000)/100.0
					);
		}
		
		
		Collections.sort(result,new DanilComparator());
		//판 이하인 애들 제거
		ArrayList<String> tempForDel = new ArrayList<String>();
		for(int i=0;i<result.size();i++) {
			String[] split_result = result.get(i).split(",");
			int panSoo=Integer.parseInt(split_result[1]);
			
			if (panSoo<minPanSoo) { //<<<<<<여기가 판수
				tempForDel.add(result.get(i));
			}
		}for(int i=0;i<tempForDel.size();i++) {
			result.remove(tempForDel.get(i));
		}
		
		
		System.out.println("EEEEE");
		for(int i=0;i<result.size();i++) {
			System.out.println(result.get(i));
		}
		return result;
	}
	//상대조합다알떄 무슨sup해야되는지
	public ArrayList<String> danilSangdaeKnow5(String champname,String champNew,int minPanSoo)  throws ClassNotFoundException, SQLException {
		GameDAO a = new GameDAO(champname);
		a.champ2=champNew;
		ArrayList<String> result = new ArrayList<String>();
		
		ArrayList<GameDTO> arr = a.listGames3();//케이틀린이 있었던 모든 게임을 가져와 arr에 넣습니다
		HashMap<String,BottomDto> map = new HashMap<String,BottomDto>();//케이틀린 상대전적 계산을 위한 해쉬맵입니다
		
		boolean mainChampWin;
		boolean subChampWin;
		String mainChamp=a.champ;
		String subChamp = null;
		
		//객체원딜의 카운터 원딜을 구하는 것
		for(int i=0;i<arr.size();i++) { //gamedto 반복문 돌립니다
			GameDTO nowGame=arr.get(i);
			if(nowGame.getBadc().contentEquals(mainChamp)){//메인챔이 블루팀 adc인가?
				subChamp=nowGame.getRsup();//서브챔은 레드팀 원딜임
				if(nowGame.getBwin().contentEquals("1")) {//블루팀이 이겼나?
					mainChampWin=true;
					subChampWin=false;
					
				}else {
					mainChampWin=false;
					subChampWin=true;
				}
			}else {//메인챔이 블루팀서폿이 아닌가? == 메인챔은 레드팀서폿인가?
				subChamp=nowGame.getBsup();//서브챔은 블루팀 원딜임
				if(nowGame.getRwin().contentEquals("1")) {//레드팀이  이겼나?
					mainChampWin=true;
					subChampWin=false;
				}else {
					mainChampWin=false;
					subChampWin=true;
				}
			}
			
			
			if(mainChampWin) {//메인챔이 이겼나?
				if(map.containsKey(subChamp)) {//서브챔이 map에 키로 있냐?
					map.get(subChamp).whole+=1;//총+=1
					map.get(subChamp).win+=1;//서브챔에대한승+=1
					//승률계산
					map.get(subChamp).win_rate=(float) (Math.round(map.get(subChamp).win/map.get(subChamp).whole*10000)/100.0);
				}else {//서브챔이 map에 키로 없냐?
					BottomDto temp = new BottomDto(); //임시 bottomdto만들어서 1전1승0패 100.0 세팅
					temp.whole=1;
					temp.win=1;
					temp.lose=0;
					temp.win_rate=(float) 100.0;
					map.put(subChamp, temp);//map에 서브챔,bttomdto put
				}
			}else {//메인챔이 졌냐?
				if(map.containsKey(subChamp)) {//서브챔이 map에 키로 있냐?
					map.get(subChamp).whole+=1;//총+=1
					map.get(subChamp).lose+=1;//서브챔에대한패+=1
					//승률계산
					map.get(subChamp).win_rate=(float) (Math.round(map.get(subChamp).win/map.get(subChamp).whole*10000)/100.0);
				}else {//서브챔이 map에 키로 없냐?
					BottomDto temp = new BottomDto(); //임시 bottomdto만들어서 1전0승1패 0.0 세팅
					temp.whole=1;
					temp.win=0;
					temp.lose=1;
					temp.win_rate=(float) 0.0;
					map.put(subChamp, temp);//map에 서브챔,bttomdto put
				}
			}
		}
		//찐최종 결과물, 이거 리스트화 해서 리턴하면됨
		for (Entry<String,BottomDto> entrySet: map.entrySet()) {
			//System.out.println(entrySet.getKey()
			//		+" :: "+entrySet.getValue().Enemy_combi
			//		+","+entrySet.getValue().whole
			//		+","+entrySet.getValue().win
			//		+","+entrySet.getValue().lose
			//		+","+Math.round(((float)entrySet.getValue().win/(float)entrySet.getValue().whole)*10000)/100.0
			//		);

			result.add(mainChamp+"_"+a.champ2
					+" :: "+entrySet.getKey()
					+","+entrySet.getValue().whole
					+","+entrySet.getValue().win
					+","+entrySet.getValue().lose
					+","+Math.round(((float)entrySet.getValue().win/(float)entrySet.getValue().whole)*10000)/100.0
					);
		}
		
		
		Collections.sort(result,new DanilComparator());
		//판 이하인 애들 제거
		ArrayList<String> tempForDel = new ArrayList<String>();
		for(int i=0;i<result.size();i++) {
			String[] split_result = result.get(i).split(",");
			int panSoo=Integer.parseInt(split_result[1]);
			
			if (panSoo<minPanSoo) { //<<<<<<여기가 판수
				tempForDel.add(result.get(i));
			}
		}for(int i=0;i<tempForDel.size();i++) {
			result.remove(tempForDel.get(i));
		}
		
		
		System.out.println("EEEEE");
		for(int i=0;i<result.size();i++) {
			System.out.println(result.get(i));
		}
		return result;
	}
	//상대조합다알떄 무슨adc해야되는지
	public ArrayLi st<String> danilSangdaeKnow6(String champname,String champNew,int minPanSoo)  throws ClassNotFoundException, SQLException {
		GameDAO a = new GameDAO(champname);
		a.champ2=champNew;
		ArrayList<String> result = new ArrayList<String>();
		
		ArrayList<GameDTO> arr = a.listGames3();//케이틀린이 있었던 모든 게임을 가져와 arr에 넣습니다
		HashMap<String,BottomDto> map = new HashMap<String,BottomDto>();//케이틀린 상대전적 계산을 위한 해쉬맵입니다
		
		boolean mainChampWin;
		boolean subChampWin;
		String mainChamp=a.champ;
		String subChamp = null;
		
		//객체원딜의 카운터 원딜을 구하는 것
		for(int i=0;i<arr.size();i++) { //gamedto 반복문 돌립니다
			GameDTO nowGame=arr.get(i);
			if(nowGame.getBadc().contentEquals(mainChamp)){//메인챔이 블루팀 adc인가?
				subChamp=nowGame.getRadc();//서브챔은 레드팀 원딜임
				if(nowGame.getBwin().contentEquals("1")) {//블루팀이 이겼나?
					mainChampWin=true;
					subChampWin=false;
					
				}else {
					mainChampWin=false;
					subChampWin=true;
				}
			}else {//메인챔이 블루팀서폿이 아닌가? == 메인챔은 레드팀서폿인가?
				subChamp=nowGame.getBadc();//서브챔은 블루팀 원딜임
				if(nowGame.getRwin().contentEquals("1")) {//레드팀이  이겼나?
					mainChampWin=true;
					subChampWin=false;
				}else {
					mainChampWin=false;
					subChampWin=true;
				}
			}
			
			
			if(mainChampWin) {//메인챔이 이겼나?
				if(map.containsKey(subChamp)) {//서브챔이 map에 키로 있냐?
					map.get(subChamp).whole+=1;//총+=1
					map.get(subChamp).win+=1;//서브챔에대한승+=1
					//승률계산
					map.get(subChamp).win_rate=(float) (Math.round(map.get(subChamp).win/map.get(subChamp).whole*10000)/100.0);
				}else {//서브챔이 map에 키로 없냐?
					BottomDto temp = new BottomDto(); //임시 bottomdto만들어서 1전1승0패 100.0 세팅
					temp.whole=1;
					temp.win=1;
					temp.lose=0;
					temp.win_rate=(float) 100.0;
					map.put(subChamp, temp);//map에 서브챔,bttomdto put
				}
			}else {//메인챔이 졌냐?
				if(map.containsKey(subChamp)) {//서브챔이 map에 키로 있냐?
					map.get(subChamp).whole+=1;//총+=1
					map.get(subChamp).lose+=1;//서브챔에대한패+=1
					//승률계산
					map.get(subChamp).win_rate=(float) (Math.round(map.get(subChamp).win/map.get(subChamp).whole*10000)/100.0);
				}else {//서브챔이 map에 키로 없냐?
					BottomDto temp = new BottomDto(); //임시 bottomdto만들어서 1전0승1패 0.0 세팅
					temp.whole=1;
					temp.win=0;
					temp.lose=1;
					temp.win_rate=(float) 0.0;
					map.put(subChamp, temp);//map에 서브챔,bttomdto put
				}
			}
		}
		//찐최종 결과물, 이거 리스트화 해서 리턴하면됨
		for (Entry<String,BottomDto> entrySet: map.entrySet()) {
			//System.out.println(entrySet.getKey()
			//		+" :: "+entrySet.getValue().Enemy_combi
			//		+","+entrySet.getValue().whole
			//		+","+entrySet.getValue().win
			//		+","+entrySet.getValue().lose
			//		+","+Math.round(((float)entrySet.getValue().win/(float)entrySet.getValue().whole)*10000)/100.0
			//		);

			result.add(mainChamp+"_"+a.champ2
					+" :: "+entrySet.getKey()
					+","+entrySet.getValue().whole
					+","+entrySet.getValue().win
					+","+entrySet.getValue().lose
					+","+Math.round(((float)entrySet.getValue().win/(float)entrySet.getValue().whole)*10000)/100.0
					);
		}
		
		
		Collections.sort(result,new DanilComparator());
		//판 이하인 애들 제거
		ArrayList<String> tempForDel = new ArrayList<String>();
		for(int i=0;i<result.size();i++) {
			String[] split_result = result.get(i).split(",");
			int panSoo=Integer.parseInt(split_result[1]);
			
			if (panSoo<minPanSoo) { //<<<<<<여기가 판수
				tempForDel.add(result.get(i));
			}
		}for(int i=0;i<tempForDel.size();i++) {
			result.remove(tempForDel.get(i));
		}
		
		
		System.out.println("EEEEE");
		for(int i=0;i<result.size();i++) {
			System.out.println(result.get(i));
		}
		return result;
	}
	
	//양쪽원딜알때 무슨 서폿 해야되는지
	//dao.listGames_bothAdc()을 기반으로 잘 만들어 보도록
	//1. 아군원딜이 이겼나 졌나 보고
	//2. map[아군원딜_아군서폿] 있나보고(상대원딜은 고정이니까)
	//3. 승패 기록하면 될듯
	//4. 아군원딜_아군서폿::상대원딜(고정) 리스트 반ㄹ환하면됨
	public ArrayList<String> new_both_adc(String champname,String champNew,int minPanSoo)  throws ClassNotFoundException, SQLException {
		GameDAO a = new GameDAO(champname);
		a.champ2=champNew;
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<GameDTO> arr = a.listGames_bothAdc();//케이틀린이 있었던 모든 게임을 가져와 arr에 넣습니다
		
		HashMap<String,BottomDto> map = new HashMap<String,BottomDto>();//케이틀린 상대전적 계산을 위한 해쉬맵입니다
		
		boolean mainChampWin;
		boolean subChampWin;
		String mainChamp=a.champ;
		String subChamp = null;
		
		//객체원딜의 카운터 원딜을 구하는 것
		for(int i=0;i<arr.size();i++) { //gamedto 반복문 돌립니다
			GameDTO nowGame=arr.get(i);
			System.out.print(nowGame.getBadc());
			System.out.print(nowGame.getBsup());
			System.out.println(nowGame.getBwin());
			System.out.print(nowGame.getRadc());
			System.out.print(nowGame.getRsup());
			System.out.println(nowGame.getRwin());
		}
		return result;
	}
	
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		GameResulter t = new GameResulter();
		t.new_both_adc("제드","루시안",0);
		
	}
}

