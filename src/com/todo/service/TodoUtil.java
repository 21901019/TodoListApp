package com.todo.service;

import java.util.*;
import java.io.*;


import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸��߰�]\n"+ "���� > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("������ �ߺ��˴ϴ�!");
			return;
		}
		sc.nextLine();
		System.out.print("ī�װ� > ");
		category = sc.next();
		sc.nextLine();
		
		System.out.print("���� > ");
		desc = sc.nextLine().trim();
		
		System.out.print("�������� > ");
		due_date = sc.next();

		TodoItem t = new TodoItem(title, desc, category, due_date);
		list.addItem(t);
		System.out.println("�߰��Ǿ����ϴ�.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("[�׸� ����]\n"+"������ �׸��� ��ȣ�� �Է��Ͻÿ� > ");
		int num = sc.nextInt();
		int i = 1;
		String yn = "";
		for (TodoItem item : l.getList()) {
			if (num == i) {
				System.out.println(i+". "+item);
				System.out.print("�� �׸��� �����Ͻðڽ��ϱ�? (y/n) > ");
				yn = sc.next();
				if(yn.equals("y")) {
					l.deleteItem(item);
					System.out.println("�����Ǿ����ϴ�.");
					i++;
					break;					
				}
				else if(yn.equals("n")) {
					i++;
					break;
				}
				else {
					System.out.println("�߸� �Է��ϼ̽��ϴ�.");
					i++;
					break;
				}
			}
			i++;
		}
		if(num != i-1) {
		System.out.println("���� ��ȣ �Դϴ�.");			
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[�׸� ����]\n"+ "������ �׸��� ��ȣ�� �Է��Ͻÿ� > ");
		int num = sc.nextInt();
		int i = 1;
		for (TodoItem item : l.getList()) {
			if (num == i) {
				System.out.println(i+". "+item);
				System.out.print("�� ���� > ");
				String new_title = sc.next().trim();
				sc.nextLine();
				if (l.isDuplicate(new_title)) {
					System.out.println("������ �ߺ��˴ϴ�!");
					return;
				}
				
				System.out.print("�� ī�װ� > ");
				String new_category = sc.next();
				sc.nextLine();
				System.out.print("�� ���� > ");
				String new_description = sc.nextLine().trim();
				System.out.print("�� �������� > ");
				String new_due_date = sc.next();
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date);
				l.addItem(t);
				System.out.println("�����Ǿ����ϴ�.");
				i++;
				break;			
			}
			i++;
		}
			
		if(num != i-1) {
			System.out.println("���� ��ȣ �Դϴ�.");			
		}
	}
		

	public static void listAll(TodoList l) {
		int i = 1;
		System.out.println("[��ü ���, �� "+l.getList().size()+"��]");
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
		System.out.println("�� "+num+"���� �׸��� ã�ҽ��ϴ�.");
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
		System.out.println("�� "+num+"���� �׸��� ã�ҽ��ϴ�.");
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
		System.out.println("\n�� "+cate.size()+"���� ī�װ��� ��ϵǾ� �ֽ��ϴ�.");
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
				System.out.println(i+"���� �׸��� �о����ϴ�.");
				
			}
			else {
				System.out.println("todolist.txt ������ �����ϴ�.");
			}
		

	}
}
