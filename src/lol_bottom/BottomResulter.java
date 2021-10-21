package lol_bottom;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Collections;
import java.util.Comparator;

public class BottomResulter {
	//아군서폿 알고, 상대 원딜 알 때
	public ArrayList<String> type_one(String ourPick, String enePick,int minPanSoo) throws ClassNotFoundException, SQLException {
		ArrayList<String> result = new ArrayList<String>();
		
		
		ChampTbl a = new ChampTbl(ourPick); //우리서폿이 들어있는 모든 테이블 리스트 반환
		ArrayList<String> temp = a.mkBtList();
		
		HashMap<String,BottomDto> map = new HashMap<String,BottomDto>();
		
		for(int i=0; i<temp.size();i++) {
			ChampRow tt = new ChampRow(enePick,temp.get(i)); //temp.get(i)(아군서폿)테이블에서 enePick(상대원딜)이 들어있는 모든 행
			ArrayList<BottomDto> finList = tt.mkBtList();
			
			for(int j=0;j<finList.size();j++) {
				//찐최종 결과 key값 바꾸고 싶으면 아래행, else아래아래행 수정
				if (map.containsKey(temp.get(i))) {//조합명 있냐?
					map.get(temp.get(i)).whole+=finList.get(j).getWhole();
					map.get(temp.get(i)).win+=finList.get(j).getWin();
					map.get(temp.get(i)).lose+=finList.get(j).getLose();
					//map.get(temp.get(i)).win_rate=map.get(temp.get(i)).win/map.get(temp.get(i)).whole;
					
				}else {//없으면 만들어
					finList.get(j).Enemy_combi=enePick; //상대유닛
					map.put(temp.get(i), finList.get(j));
				}
				map.get(temp.get(i)).win_rate=map.get(temp.get(i)).win/map.get(temp.get(i)).whole;
				//System.out.print(temp.get(i)+" VS ");
				//System.out.print(finList.get(j).getEnemy_combi()+" ");
				//System.out.print(finList.get(j).getWhole()+" ");
				//System.out.print(finList.get(j).getWin()+" ");
				//System.out.print(finList.get(j).getLose()+" ");
				//System.out.println(finList.get(j).getWin_rate()+"%");

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
					+" :: "+entrySet.getValue().Enemy_combi
					+","+entrySet.getValue().whole
					+","+entrySet.getValue().win
					+","+entrySet.getValue().lose
					+","+Math.round(((float)entrySet.getValue().win/(float)entrySet.getValue().whole)*10000)/100.0
					);
		}
		
		
		Collections.sort(result,new LolBotResultComparator());
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
	
	public ArrayList<String> type_two(String ourPick, String enePick,int minPanSoo) throws ClassNotFoundException, SQLException {
		ArrayList<String> result = new ArrayList<String>();
		
		
		ChampTbl a = new ChampTbl(ourPick); //우리서폿이 들어있는 모든 테이블 리스트 반환
		ArrayList<String> temp = a.mkBtList();//ourPick이 들어있는 테이블명1,2,3 ...
		
		HashMap<String,BottomDto> map = new HashMap<String,BottomDto>();
		
		for(int i=0; i<temp.size();i++) {
			ChampRow tt = new ChampRow(enePick,temp.get(i)); //temp.get(i)(아군서폿)테이블에서 enePick(상대원딜)이 들어있는 모든 행
			ArrayList<BottomDto> finList = tt.mkBtList();//상대원딜 들어있는 테이블1,2,3...
			
			for(int j=0;j<finList.size();j++) {
				//찐최종 결과 key값 바꾸고 싶으면 아래행, else아래아래행 수정
				if (map.containsKey(temp.get(i))) {//아군조합명 있냐?
					map.get(temp.get(i)).whole+=finList.get(j).getWhole();
					map.get(temp.get(i)).win+=finList.get(j).getWin();
					map.get(temp.get(i)).lose+=finList.get(j).getLose();
					//map.get(temp.get(i)).win_rate=map.get(temp.get(i)).win/map.get(temp.get(i)).whole;
					
				}else {//없으면 만들어
					finList.get(j).Enemy_combi=enePick; //상대유닛
					map.put(temp.get(i), finList.get(j));
				}
				map.get(temp.get(i)).win_rate=map.get(temp.get(i)).win/map.get(temp.get(i)).whole;
				//System.out.print(temp.get(i)+" VS ");
				//System.out.print(finList.get(j).getEnemy_combi()+" ");
				//System.out.print(finList.get(j).getWhole()+" ");
				//System.out.print(finList.get(j).getWin()+" ");
				//System.out.print(finList.get(j).getLose()+" ");
				//System.out.println(finList.get(j).getWin_rate()+"%");

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
					+" :: "+entrySet.getValue().Enemy_combi
					+","+entrySet.getValue().whole
					+","+entrySet.getValue().win
					+","+entrySet.getValue().lose
					+","+Math.round(((float)entrySet.getValue().win/(float)entrySet.getValue().whole)*10000)/100.0
					);
		}
		
		
		Collections.sort(result,new LolBotResultComparator());
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
		BottomResulter a = new BottomResulter();
		a.type_one("'%MISSFORTUNE%'", "'%AMUMU%'",2);
	}

}
