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
	public ArrayList<String> danilSangdaeKnow6(String champname,String champNew,int minPanSoo)  throws ClassNotFoundException, SQLException {
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
		GameDAO a = new GameDAO(champname);//기준챔
		a.champ2=champNew;//상대챔
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<GameDTO> arr = a.listGames_bothAdc();//기준챔+상대챔이 있었던 모든 게임을 가져와 arr에 넣습니다
		
		HashMap<String,BottomDto> map = new HashMap<String,BottomDto>();//기준챔 상대전적 계산을 위한 해쉬맵입니다
		
		boolean mainChampWin;
		boolean subChampWin;
		String mainChamp=a.champ;
		String subChamp = null;
		String mainTeamChamp = null;
		//객체원딜의 카운터 원딜을 구하는 것
		for(int i=0;i<arr.size();i++) { //gamedto 반복문 돌립니다
			GameDTO nowGame=arr.get(i);

			//기준챔이 이겼는지 졌는지랑 기준서폿 뭔지
			if(nowGame.getBadc().equals(mainChamp)) {
				mainTeamChamp=nowGame.getBsup(); //블루원딜이 기준챔이니 블루서폿이 기준팀챔
				if(nowGame.getBwin().contentEquals("1")) {
					mainChampWin=true;					
				}else {
					mainChampWin=false;
				}
			}else {
				mainChampWin=false;
				mainTeamChamp=nowGame.getRsup(); //레드원딜이 기준챔이니 레드서폿이 기준팀챔
				if(nowGame.getRwin().contentEquals("1")) {
					mainChampWin=true;					
				}else {
					mainChampWin=false;
				}
			}
			
			
			//map에 기록
			//키 이미 있는경우
			if (map.containsKey(mainChamp+"_"+mainTeamChamp)) {
				//총게임수기록
				map.get(mainChamp+"_"+mainTeamChamp).whole+=1;
				//승패기록
				if (mainChampWin) {
					map.get(mainChamp+"_"+mainTeamChamp).win+=1;
				}else {
					map.get(mainChamp+"_"+mainTeamChamp).lose+=1;
				}
				map.get(mainChamp+"_"+mainTeamChamp).win_rate=(float) (Math.round(map.get(mainChamp+"_"+mainTeamChamp).win/map.get(mainChamp+"_"+mainTeamChamp).whole*10000)/100.0);
			}else {//키 없는 경우
				BottomDto temp = new BottomDto();
				temp.whole=1;
				if(mainChampWin) {
					temp.win=1;
					temp.lose=0;
					temp.win_rate=(float)100.0;
				}else {
					temp.win=0;
					temp.lose=1;
					temp.win_rate=(float)0.0;
				}
				map.put(mainChamp+"_"+mainTeamChamp,temp);
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

				result.add(entrySet.getKey()
						+" :: "+a.champ2
						+","+entrySet.getValue().whole
						+","+entrySet.getValue().win
						+","+entrySet.getValue().lose
						+","+Math.round(((float)entrySet.getValue().win/(float)entrySet.getValue().whole)*10000)/100.0
						);
			}
			
			
			Collections.sort(result,new DanilComparator_jungbang());
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
	//양쪽서폿알때 무슨 원딜 해야되는지
	//dao.listGames_bothAdc()을 기반으로 잘 만들어 보도록
	//1. 아군서폿이 이겼나 졌나 보고
	//2. map[아군원딜_아군서폿] 있나보고(상대원딜은 고정이니까)
	//3. 승패 기록하면 될듯
	//4. 아군원딜_아군서폿::상대원딜(고정) 리스트 반ㄹ환하면됨
	public ArrayList<String> new_both_sup(String champname,String champNew,int minPanSoo)  throws ClassNotFoundException, SQLException {
		GameDAO a = new GameDAO(champname);//기준챔
		a.champ2=champNew;//상대챔
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<GameDTO> arr = a.listGames_bothSup();//기준챔+상대챔이 있었던 모든 게임을 가져와 arr에 넣습니다
		
		HashMap<String,BottomDto> map = new HashMap<String,BottomDto>();//기준챔 상대전적 계산을 위한 해쉬맵입니다
		
		boolean mainChampWin;
		boolean subChampWin;
		String mainChamp=a.champ;
		String subChamp = null;
		String mainTeamChamp = null;
		//객체원딜의 카운터 원딜을 구하는 것
		System.out.println(arr.size());
		for(int i=0;i<arr.size();i++) { //gamedto 반복문 돌립니다
			GameDTO nowGame=arr.get(i);
			//기준챔이 이겼는지 졌는지랑 기준서폿 뭔지
			if(nowGame.getBadc().equals(mainChamp)) {
				mainTeamChamp=nowGame.getBsup(); //블루원딜이 기준챔이니 블루서폿이 기준팀챔
				if(nowGame.getBwin().contentEquals("1")) {
					mainChampWin=true;					
				}else {
					mainChampWin=false;
				}
			}else {
				mainChampWin=false;
				mainTeamChamp=nowGame.getRsup(); //레드원딜이 기준챔이니 레드서폿이 기준팀챔
				if(nowGame.getRwin().contentEquals("1")) {
					mainChampWin=true;					
				}else {
					mainChampWin=false;
				}
			}
			
			
			//map에 기록
			//키 이미 있는경우
			String theKey =mainChamp+"_"+mainTeamChamp;
			if (map.containsKey(theKey)) {
				//총게임수기록
				map.get(theKey).whole+=1;
				//승패기록
				if (mainChampWin) {
					map.get(theKey).win+=1;
				}else {
					map.get(theKey).lose+=1;
				}
				map.get(theKey).win_rate=(float) (Math.round(map.get(theKey).win/map.get(theKey).whole*10000)/100.0);
			}else {//키 없는 경우
				BottomDto temp = new BottomDto();
				temp.whole=1;
				if(mainChampWin) {
					temp.win=1;
					temp.lose=0;
					temp.win_rate=(float)100.0;
				}else {
					temp.win=0;
					temp.lose=1;
					temp.win_rate=(float)0.0;
				}
				map.put(theKey,temp);
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

				result.add(entrySet.getKey()
						+" :: "+a.champ2
						+","+entrySet.getValue().whole
						+","+entrySet.getValue().win
						+","+entrySet.getValue().lose
						+","+Math.round(((float)entrySet.getValue().win/(float)entrySet.getValue().whole)*10000)/100.0
						);
			}
			
			
			Collections.sort(result,new DanilComparator_jungbang());
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
	//양쪽서폿알때 무슨 원딜 해야되는지
	//dao.listGames_bothAdc()을 기반으로 잘 만들어 보도록
	//1. 아군서폿이 이겼나 졌나 보고
	//2. map[아군원딜_아군서폿] 있나보고(상대원딜은 고정이니까)
	//3. 승패 기록하면 될듯
	//4. 아군원딜_아군서폿::상대원딜(고정) 리스트 반ㄹ환하면됨
	public ArrayList<String> new_ma_es(String champname,String champNew,int minPanSoo)  throws ClassNotFoundException, SQLException {
		GameDAO a = new GameDAO(champname);//기준챔
		a.champ2=champNew;//상대챔
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<GameDTO> arr = a.listGames_MaEs();//기준챔+상대챔이 있었던 모든 게임을 가져와 arr에 넣습니다
		
		HashMap<String,BottomDto> map = new HashMap<String,BottomDto>();//기준챔 상대전적 계산을 위한 해쉬맵입니다
		
		boolean mainChampWin;
		boolean subChampWin;
		String mainChamp=a.champ;
		String subChamp = null;
		String mainTeamChamp = null;
		//객체원딜의 카운터 원딜을 구하는 것
		System.out.println(arr.size());
		for(int i=0;i<arr.size();i++) { //gamedto 반복문 돌립니다
			GameDTO nowGame=arr.get(i);
			//기준챔이 이겼는지 졌는지랑 기준서폿 뭔지
			if(nowGame.getBadc().equals(mainChamp)) {
				mainTeamChamp=nowGame.getBsup(); //블루원딜이 기준챔이니 블루서폿이 기준팀챔
				if(nowGame.getBwin().contentEquals("1")) {
					mainChampWin=true;					
				}else {
					mainChampWin=false;
				}
			}else {
				mainChampWin=false;
				mainTeamChamp=nowGame.getRsup(); //레드원딜이 기준챔이니 레드서폿이 기준팀챔
				if(nowGame.getRwin().contentEquals("1")) {
					mainChampWin=true;					
				}else {
					mainChampWin=false;
				}
			}
			
			
			//map에 기록
			//키 이미 있는경우
			String theKey =mainChamp+"_"+mainTeamChamp;
			if (map.containsKey(theKey)) {
				//총게임수기록
				map.get(theKey).whole+=1;
				//승패기록
				if (mainChampWin) {
					map.get(theKey).win+=1;
				}else {
					map.get(theKey).lose+=1;
				}
				map.get(theKey).win_rate=(float) (Math.round(map.get(theKey).win/map.get(theKey).whole*10000)/100.0);
			}else {//키 없는 경우
				BottomDto temp = new BottomDto();
				temp.whole=1;
				if(mainChampWin) {
					temp.win=1;
					temp.lose=0;
					temp.win_rate=(float)100.0;
				}else {
					temp.win=0;
					temp.lose=1;
					temp.win_rate=(float)0.0;
				}
				map.put(theKey,temp);
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

				result.add(entrySet.getKey()
						+" :: "+a.champ2
						+","+entrySet.getValue().whole
						+","+entrySet.getValue().win
						+","+entrySet.getValue().lose
						+","+Math.round(((float)entrySet.getValue().win/(float)entrySet.getValue().whole)*10000)/100.0
						);
			}
			
			
			Collections.sort(result,new DanilComparator_jungbang());
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
	//양쪽서폿알때 무슨 원딜 해야되는지
	//dao.listGames_bothAdc()을 기반으로 잘 만들어 보도록
	//1. 아군서폿이 이겼나 졌나 보고
	//2. map[아군원딜_아군서폿] 있나보고(상대원딜은 고정이니까)
	//3. 승패 기록하면 될듯
	//4. 아군원딜_아군서폿::상대원딜(고정) 리스트 반ㄹ환하면됨
	public ArrayList<String> new_ms_ea(String champname,String champNew,int minPanSoo)  throws ClassNotFoundException, SQLException {
		GameDAO a = new GameDAO(champname);//기준챔
		a.champ2=champNew;//상대챔
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<GameDTO> arr = a.listGames_MsEa();//기준챔+상대챔이 있었던 모든 게임을 가져와 arr에 넣습니다
		
		HashMap<String,BottomDto> map = new HashMap<String,BottomDto>();//기준챔 상대전적 계산을 위한 해쉬맵입니다
		
		boolean mainChampWin;
		boolean subChampWin;
		String mainChamp=a.champ;
		String subChamp = null;
		String mainTeamChamp = null;
		//객체원딜의 카운터 원딜을 구하는 것
		System.out.println(arr.size());
		for(int i=0;i<arr.size();i++) { //gamedto 반복문 돌립니다
			GameDTO nowGame=arr.get(i);
			//기준챔이 이겼는지 졌는지랑 기준서폿 뭔지
			if(nowGame.getBsup().equals(mainChamp)) {
				mainTeamChamp=nowGame.getBadc(); //블루원딜이 기준챔이니 블루서폿이 기준팀챔
				if(nowGame.getBwin().contentEquals("1")) {
					mainChampWin=true;					
				}else {
					mainChampWin=false;
				}
			}else {
				mainChampWin=false;
				mainTeamChamp=nowGame.getRadc(); //레드원딜이 기준챔이니 레드서폿이 기준팀챔
				if(nowGame.getRwin().contentEquals("1")) {
					mainChampWin=true;					
				}else {
					mainChampWin=false;
				}
			}
			
			
			//map에 기록
			//키 이미 있는경우
			String theKey =mainChamp+"_"+mainTeamChamp;
			if (map.containsKey(theKey)) {
				//총게임수기록
				map.get(theKey).whole+=1;
				//승패기록
				if (mainChampWin) {
					map.get(theKey).win+=1;
				}else {
					map.get(theKey).lose+=1;
				}
				map.get(theKey).win_rate=(float) (Math.round(map.get(theKey).win/map.get(theKey).whole*10000)/100.0);
			}else {//키 없는 경우
				BottomDto temp = new BottomDto();
				temp.whole=1;
				if(mainChampWin) {
					temp.win=1;
					temp.lose=0;
					temp.win_rate=(float)100.0;
				}else {
					temp.win=0;
					temp.lose=1;
					temp.win_rate=(float)0.0;
				}
				map.put(theKey,temp);
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

				result.add(entrySet.getKey()
						+" :: "+a.champ2
						+","+entrySet.getValue().whole
						+","+entrySet.getValue().win
						+","+entrySet.getValue().lose
						+","+Math.round(((float)entrySet.getValue().win/(float)entrySet.getValue().whole)*10000)/100.0
						);
			}
			
			
			Collections.sort(result,new DanilComparator_jungbang());
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
	
	//new type5
	public ArrayList<String> new_ms_ea_es(String champname,String champNew, String champNew2, int minPanSoo)  throws ClassNotFoundException, SQLException {
		GameDAO a = new GameDAO(champname);//기준챔
		a.champ2=champNew;//상대챔
		a.champ3=champNew2;//상대챔
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<GameDTO> arr = a.listGames_MsEaEs();//기준챔+상대챔이 있었던 모든 게임을 가져와 arr에 넣습니다
		
		HashMap<String,BottomDto> map = new HashMap<String,BottomDto>();//기준챔 상대전적 계산을 위한 해쉬맵입니다
		
		boolean mainChampWin;
		boolean subChampWin;
		String mainChamp=a.champ;
		String subChamp = null;
		String mainTeamChamp = null;
		//객체원딜의 카운터 원딜을 구하는 것
		System.out.println(arr.size());
		for(int i=0;i<arr.size();i++) { //gamedto 반복문 돌립니다
			GameDTO nowGame=arr.get(i);
			//기준챔이 이겼는지 졌는지랑 기준서폿 뭔지
			if(nowGame.getBsup().equals(mainChamp)) {
				mainTeamChamp=nowGame.getBadc(); //블루원딜이 기준챔이니 블루서폿이 기준팀챔
				if(nowGame.getBwin().contentEquals("1")) {
					mainChampWin=true;					
				}else {
					mainChampWin=false;
				}
			}else {
				mainChampWin=false;
				mainTeamChamp=nowGame.getRadc(); //레드원딜이 기준챔이니 레드서폿이 기준팀챔
				if(nowGame.getRwin().contentEquals("1")) {
					mainChampWin=true;					
				}else {
					mainChampWin=false;
				}
			}
			
			
			//map에 기록
			//키 이미 있는경우
			String theKey =mainChamp+"_"+mainTeamChamp;
			if (map.containsKey(theKey)) {
				//총게임수기록
				map.get(theKey).whole+=1;
				//승패기록
				if (mainChampWin) {
					map.get(theKey).win+=1;
				}else {
					map.get(theKey).lose+=1;
				}
				map.get(theKey).win_rate=(float) (Math.round(map.get(theKey).win/map.get(theKey).whole*10000)/100.0);
			}else {//키 없는 경우
				BottomDto temp = new BottomDto();
				temp.whole=1;
				if(mainChampWin) {
					temp.win=1;
					temp.lose=0;
					temp.win_rate=(float)100.0;
				}else {
					temp.win=0;
					temp.lose=1;
					temp.win_rate=(float)0.0;
				}
				map.put(theKey,temp);
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

				result.add(entrySet.getKey()
						+" :: "+a.champ2+"_"+a.champ3
						+","+entrySet.getValue().whole
						+","+entrySet.getValue().win
						+","+entrySet.getValue().lose
						+","+Math.round(((float)entrySet.getValue().win/(float)entrySet.getValue().whole)*10000)/100.0
						);
			}
			
			
			Collections.sort(result,new DanilComparator_jungbang());
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
	//new type6
	public ArrayList<String> new_ma_ea_es(String champname,String champNew, String champNew2, int minPanSoo)  throws ClassNotFoundException, SQLException {
		GameDAO a = new GameDAO(champname);//기준챔
		a.champ2=champNew;//상대챔
		a.champ3=champNew2;//상대챔
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<GameDTO> arr = a.listGames_MaEaEs();//기준챔+상대챔이 있었던 모든 게임을 가져와 arr에 넣습니다
		
		HashMap<String,BottomDto> map = new HashMap<String,BottomDto>();//기준챔 상대전적 계산을 위한 해쉬맵입니다
		
		boolean mainChampWin;
		boolean subChampWin;
		String mainChamp=a.champ;
		String subChamp = null;
		String mainTeamChamp = null;
		//객체원딜의 카운터 원딜을 구하는 것
		System.out.println(arr.size());
		for(int i=0;i<arr.size();i++) { //gamedto 반복문 돌립니다
			GameDTO nowGame=arr.get(i);
			//기준챔이 이겼는지 졌는지랑 기준서폿 뭔지
			if(nowGame.getBadc().equals(mainChamp)) {
				mainTeamChamp=nowGame.getBsup(); //블루원딜이 기준챔이니 블루서폿이 기준팀챔
				if(nowGame.getBwin().contentEquals("1")) {
					mainChampWin=true;					
				}else {
					mainChampWin=false;
				}
			}else {
				mainChampWin=false;
				mainTeamChamp=nowGame.getRsup(); //레드원딜이 기준챔이니 레드서폿이 기준팀챔
				if(nowGame.getRwin().contentEquals("1")) {
					mainChampWin=true;					
				}else {
					mainChampWin=false;
				}
			}
			
			
			//map에 기록
			//키 이미 있는경우
			String theKey =mainChamp+"_"+mainTeamChamp;
			if (map.containsKey(theKey)) {
				//총게임수기록
				map.get(theKey).whole+=1;
				//승패기록
				if (mainChampWin) {
					map.get(theKey).win+=1;
				}else {
					map.get(theKey).lose+=1;
				}
				map.get(theKey).win_rate=(float) (Math.round(map.get(theKey).win/map.get(theKey).whole*10000)/100.0);
			}else {//키 없는 경우
				BottomDto temp = new BottomDto();
				temp.whole=1;
				if(mainChampWin) {
					temp.win=1;
					temp.lose=0;
					temp.win_rate=(float)100.0;
				}else {
					temp.win=0;
					temp.lose=1;
					temp.win_rate=(float)0.0;
				}
				map.put(theKey,temp);
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

				result.add(entrySet.getKey()
						+" :: "+a.champ2+"_"+a.champ3
						+","+entrySet.getValue().whole
						+","+entrySet.getValue().win
						+","+entrySet.getValue().lose
						+","+Math.round(((float)entrySet.getValue().win/(float)entrySet.getValue().whole)*10000)/100.0
						);
			}
			
			
			Collections.sort(result,new DanilComparator_jungbang());
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
	//new type7
	public ArrayList<String> new_ma_only(String champname, int minPanSoo)  throws ClassNotFoundException, SQLException {
		GameDAO a = new GameDAO(champname);//기준챔
		//a.champ2=champNew;//상대챔
		//a.champ3=champNew2;//상대챔
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<GameDTO> arr = a.listGames_MaOnly();//기준챔+상대챔이 있었던 모든 게임을 가져와 arr에 넣습니다
		
		HashMap<String,BottomDto> map = new HashMap<String,BottomDto>();//기준챔 상대전적 계산을 위한 해쉬맵입니다
		
		boolean mainChampWin;
		boolean subChampWin;
		String mainChamp=a.champ;
		String subChamp = null;
		String mainTeamChamp = null;
		//객체원딜의 카운터 원딜을 구하는 것
		System.out.println(arr.size());
		for(int i=0;i<arr.size();i++) { //gamedto 반복문 돌립니다
			GameDTO nowGame=arr.get(i);
			//기준챔이 이겼는지 졌는지랑 기준서폿 뭔지
			if(nowGame.getBadc().equals(mainChamp)) {
				mainTeamChamp=nowGame.getBsup(); //블루원딜이 기준챔이니 블루서폿이 기준팀챔
				if(nowGame.getBwin().contentEquals("1")) {
					mainChampWin=true;					
				}else {
					mainChampWin=false;
				}
			}else {
				mainChampWin=false;
				mainTeamChamp=nowGame.getRsup(); //레드원딜이 기준챔이니 레드서폿이 기준팀챔
				if(nowGame.getRwin().contentEquals("1")) {
					mainChampWin=true;					
				}else {
					mainChampWin=false;
				}
			}
			
			
			//map에 기록
			//키 이미 있는경우
			String theKey =mainChamp+"_"+mainTeamChamp;
			if (map.containsKey(theKey)) {
				//총게임수기록
				map.get(theKey).whole+=1;
				//승패기록
				if (mainChampWin) {
					map.get(theKey).win+=1;
				}else {
					map.get(theKey).lose+=1;
				}
				map.get(theKey).win_rate=(float) (Math.round(map.get(theKey).win/map.get(theKey).whole*10000)/100.0);
			}else {//키 없는 경우
				BottomDto temp = new BottomDto();
				temp.whole=1;
				if(mainChampWin) {
					temp.win=1;
					temp.lose=0;
					temp.win_rate=(float)100.0;
				}else {
					temp.win=0;
					temp.lose=1;
					temp.win_rate=(float)0.0;
				}
				map.put(theKey,temp);
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

				result.add(entrySet.getKey()
						+" :: "
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

	//new type8
	public ArrayList<String> new_ms_only(String champname, int minPanSoo)  throws ClassNotFoundException, SQLException {
		GameDAO a = new GameDAO(champname);//기준챔
		//a.champ2=champNew;//상대챔
		//a.champ3=champNew2;//상대챔
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<GameDTO> arr = a.listGames_MsOnly();//기준챔+상대챔이 있었던 모든 게임을 가져와 arr에 넣습니다
		
		HashMap<String,BottomDto> map = new HashMap<String,BottomDto>();//기준챔 상대전적 계산을 위한 해쉬맵입니다
		
		boolean mainChampWin;
		boolean subChampWin;
		String mainChamp=a.champ;
		String subChamp = null;
		String mainTeamChamp = null;
		//객체원딜의 카운터 원딜을 구하는 것
		System.out.println(arr.size());
		for(int i=0;i<arr.size();i++) { //gamedto 반복문 돌립니다
			GameDTO nowGame=arr.get(i);
			//기준챔이 이겼는지 졌는지랑 기준서폿 뭔지
			if(nowGame.getBsup().equals(mainChamp)) {
				mainTeamChamp=nowGame.getBadc(); //블루원딜이 기준챔이니 블루서폿이 기준팀챔
				if(nowGame.getBwin().contentEquals("1")) {
					mainChampWin=true;					
				}else {
					mainChampWin=false;
				}
			}else {
				mainChampWin=false;
				mainTeamChamp=nowGame.getRadc(); //레드원딜이 기준챔이니 레드서폿이 기준팀챔
				if(nowGame.getRwin().contentEquals("1")) {
					mainChampWin=true;					
				}else {
					mainChampWin=false;
				}
			}
			
			
			//map에 기록
			//키 이미 있는경우
			String theKey =mainChamp+"_"+mainTeamChamp;
			if (map.containsKey(theKey)) {
				//총게임수기록
				map.get(theKey).whole+=1;
				//승패기록
				if (mainChampWin) {
					map.get(theKey).win+=1;
				}else {
					map.get(theKey).lose+=1;
				}
				map.get(theKey).win_rate=(float) (Math.round(map.get(theKey).win/map.get(theKey).whole*10000)/100.0);
			}else {//키 없는 경우
				BottomDto temp = new BottomDto();
				temp.whole=1;
				if(mainChampWin) {
					temp.win=1;
					temp.lose=0;
					temp.win_rate=(float)100.0;
				}else {
					temp.win=0;
					temp.lose=1;
					temp.win_rate=(float)0.0;
				}
				map.put(theKey,temp);
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

				result.add(entrySet.getKey()
						+" :: "
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
	public ArrayList<String> new_all(String champname,String champNew, String champNew2, String champNew3, int minPanSoo)  throws ClassNotFoundException, SQLException {
		GameDAO a = new GameDAO(champname);//기준챔
		a.champ2=champNew;//상대원딜
		a.champ3=champNew2;//상대서폿
		a.champ4=champNew3;//아군원딜
		ArrayList<String> result = new ArrayList<String>();
		ArrayList<GameDTO> arr = a.listGames_all();//기준챔+상대챔이 있었던 모든 게임을 가져와 arr에 넣습니다
		
		HashMap<String,BottomDto> map = new HashMap<String,BottomDto>();//기준챔 상대전적 계산을 위한 해쉬맵입니다
		
		boolean mainChampWin;
		boolean subChampWin;
		String mainChamp=a.champ;
		String subChamp = null;
		String mainTeamChamp = null;
		//객체원딜의 카운터 원딜을 구하는 것
		System.out.println(arr.size());
		for(int i=0;i<arr.size();i++) { //gamedto 반복문 돌립니다
			GameDTO nowGame=arr.get(i);
			//기준챔이 이겼는지 졌는지랑 기준서폿 뭔지
			if(nowGame.getBsup().equals(mainChamp)) {
				mainTeamChamp=nowGame.getBadc(); //블루원딜이 기준챔이니 블루서폿이 기준팀챔
				if(nowGame.getBwin().contentEquals("1")) {
					mainChampWin=true;					
				}else {
					mainChampWin=false;
				}
			}else {
				mainChampWin=false;
				mainTeamChamp=nowGame.getRadc(); //레드원딜이 기준챔이니 레드서폿이 기준팀챔
				if(nowGame.getRwin().contentEquals("1")) {
					mainChampWin=true;					
				}else {
					mainChampWin=false;
				}
			}
			
			
			//map에 기록
			//키 이미 있는경우
			String theKey =mainTeamChamp+"_"+mainChamp;
			if (map.containsKey(theKey)) {
				//총게임수기록
				map.get(theKey).whole+=1;
				//승패기록
				if (mainChampWin) {
					map.get(theKey).win+=1;
				}else {
					map.get(theKey).lose+=1;
				}
				map.get(theKey).win_rate=(float) (Math.round(map.get(theKey).win/map.get(theKey).whole*10000)/100.0);
			}else {//키 없는 경우
				BottomDto temp = new BottomDto();
				temp.whole=1;
				if(mainChampWin) {
					temp.win=1;
					temp.lose=0;
					temp.win_rate=(float)100.0;
				}else {
					temp.win=0;
					temp.lose=1;
					temp.win_rate=(float)0.0;
				}
				map.put(theKey,temp);
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

				result.add(entrySet.getKey()
						+" :: "+a.champ2+"_"+a.champ3
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
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		GameResulter t = new GameResulter();
		t.new_all("나미","미스 포츈","유미","루시안",0);
		
		
	}
}

