package com.todo.service;

import java.util.*;
import java.io.*;


import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목추가]\n"+ "제목 > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("제목이 중복됩니다!");
			return;
		}
		sc.nextLine();
		System.out.print("카테고리 > ");
		category = sc.next();
		sc.nextLine();
		
		System.out.print("내용 > ");
		desc = sc.nextLine().trim();
		
		System.out.print("마감일자 > ");
		due_date = sc.next();

		TodoItem t = new TodoItem(title, desc, category, due_date);
		list.addItem(t);
		System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("[항목 삭제]\n"+"삭제할 항목의 번호를 입력하시오 > ");
		int num = sc.nextInt();
		int i = 1;
		String yn = "";
		for (TodoItem item : l.getList()) {
			if (num == i) {
				System.out.println(i+". "+item);
				System.out.print("위 항목을 삭제하시겠습니까? (y/n) > ");
				yn = sc.next();
				if(yn.equals("y")) {
					l.deleteItem(item);
					System.out.println("삭제되었습니다.");
					i++;
					break;					
				}
				else if(yn.equals("n")) {
					i++;
					break;
				}
				else {
					System.out.println("잘못 입력하셨습니다.");
					i++;
					break;
				}
			}
			i++;
		}
		if(num != i-1) {
		System.out.println("없는 번호 입니다.");			
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 수정]\n"+ "수정할 항목의 번호를 입력하시오 > ");
		int num = sc.nextInt();
		int i = 1;
		for (TodoItem item : l.getList()) {
			if (num == i) {
				System.out.println(i+". "+item);
				System.out.print("새 제목 > ");
				String new_title = sc.next().trim();
				sc.nextLine();
				if (l.isDuplicate(new_title)) {
					System.out.println("제목이 중복됩니다!");
					return;
				}
				
				System.out.print("새 카테고리 > ");
				String new_category = sc.next();
				sc.nextLine();
				System.out.print("새 내용 > ");
				String new_description = sc.nextLine().trim();
				System.out.print("새 마감일자 > ");
				String new_due_date = sc.next();
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date);
				l.addItem(t);
				System.out.println("수정되었습니다.");
				i++;
				break;			
			}
			i++;
		}
			
		if(num != i-1) {
			System.out.println("없는 번호 입니다.");			
		}
	}
		

	public static void listAll(TodoList l) {
		int i = 1;
		System.out.println("[전체 목록, 총 "+l.getList().size()+"개]");
		for (TodoItem item : l.getList()) {
			System.out.println(i+". "+item);
			i++;
		}
	}
	
	public static void find(TodoList l, String key) {
		int i = 1;
		int num = 0;
		for(TodoItem item : l.getList()) {
			if(item.getTitle().contains(key)) {
				System.out.println(i+". "+item);
				num++;
			}
			else if(item.getDesc().contains(key)) {
				System.out.println(i+". "+item);
				num++;
			}
			i++;
		}
		System.out.println("총 "+num+"개의 항목을 찾았습니다.");
	}
	
	public static void find_cate(TodoList l, String key) {
		int i = 1;
		int num = 0;
		for(TodoItem item : l.getList()) {
			if(item.getCategory().contains(key)) {
				System.out.println(i+". "+item);
				num++;
			}
			i++;
		}
		System.out.println("총 "+num+"개의 항목을 찾았습니다.");
	}
	
	public static void ls_cate(TodoList l) {
		ArrayList<String> cate = new ArrayList();
		int num = 0;
		for(TodoItem item : l.getList()) {
			if(!cate.contains(item.getCategory())) {
				cate.add(item.getCategory());
			}
			
		}
		for(int i = 0; i< cate.size(); i++) {
			System.out.print(cate.get(i));
			if(i+1 != cate.size()) {
				System.out.print(" / ");
			}
		}
		System.out.println("\n총 "+cate.size()+"개의 카테고리가 등록되어 있습니다.");
	}
	
	public static void saveList(TodoList l, String filename) {
			try {
				FileWriter fw = new FileWriter(filename);
				for(TodoItem item : l.getList()) {
					fw.write(item.toSaveString());
					fw.flush();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}

		
	}
	
	public static void loadList(TodoList l, String filename) throws IOException {
			File f = new File(filename);
			if(f.exists()) {
				int i = 0;
				BufferedReader br = new BufferedReader(new FileReader(filename));
				String fr;
				
				while((fr = br.readLine())!= null){
					StringTokenizer st = new StringTokenizer(fr, "##");
					String category = st.nextToken();
					String title = st.nextToken();
					String desc = st.nextToken();
					String due_date = st.nextToken();
					String current_date = st.nextToken();
					TodoItem t = new TodoItem(title, desc, category, due_date, current_date);
					l.addItem(t);
					i += 1;	
				}
				System.out.println(i+"개의 항목을 읽었습니다.");
				
			}
			else {
				System.out.println("todolist.txt 파일이 없습니다.");
			}
		

	}
}
