import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WarriorMain {
	public static Map<Integer, Warrior> warriors = new HashMap<Integer, Warrior>();

	public static void main(String[] args) throws Exception {
		System.out.println("Connecting to db ...");
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "Asdf-1744");
		System.out.println("Connected!");

		Statement st = con.createStatement();
		st.execute("CREATE DATABASE IF NOT EXISTS warriors_stats");
		st.execute("CREATE TABLE IF NOT EXISTS warriors_stats.warriors (\r\n"
				+ " id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,\r\n" + " name VARCHAR(32) NOT NULL,\r\n"
				+ " experience INT(6) NOT NULL,\r\n" + " created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\r\n"
				+ " updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP\r\n" + " ); ");

		System.out.println("Fetching warriors from db...");
		ResultSet rs = st.executeQuery("SELECT * FROM warriors_stats.warriors");
		while (rs.next()) {
			Warrior warrior = new Warrior(rs.getString("name"));
			warrior.setExperince(rs.getInt("experience"));
			warriors.put(rs.getInt("id"), warrior);
		}
		System.out.println("Done");
		Scanner scan = new Scanner(System.in);
		String command = "";
		int selected = 0 ;
		int enemyId = 0;
		String line;
		menu("");
		while (!command.equalsIgnoreCase("exit")) {
			line = scan.nextLine();
			command = line.split(" ")[0];
			if (command.equalsIgnoreCase("")) {
				menu("none");
				continue;
			} 
			else if (command.equalsIgnoreCase("list")|| command.equalsIgnoreCase("1")){
				rs = st.executeQuery("SELECT id FROM warriors_stats.warriors");
				System.out.printf("%-7s %-30s %-10s %-12s%n", "Id", "Name", "Level", "Rank");
				while (rs.next()) {
					Warrior warrior = warriors.get(rs.getInt("id"));
					System.out.printf("%-7s %-30s %-10s %-12s%n", rs.getInt("id"), warrior.getName(),
							warrior.getWarriorLevel(), warrior.getWarriorRank());
				}
				menu("list");
				continue;
			} else if (command.equalsIgnoreCase("create")) {
				String name = line.split(" ", 2)[1];
				Warrior warrior = new Warrior(name);
				st.execute("INSERT INTO warriors_stats.warriors (name, experience) VALUES ('"+warrior.getName()+"',"+warrior.getExperince()+")");
				rs = st.executeQuery("SELECT LAST_INSERT_ID() FROM warriors_stats.warriors LIMIT 1"); 
				rs.next(); 
				warriors.put(rs.getInt("LAST_INSERT_ID()"), warrior);
				menu("Created");
				continue;
			} else if (command.equalsIgnoreCase("delete")) {
				selected = Integer.parseInt(line.split(" ")[1]);
				st.execute("DELETE FROM warriors_stats.warriors WHERE id= "+selected);
				menu("deleted");
				continue;
			} else if (command.equalsIgnoreCase("warrior")) {
				selected = Integer.parseInt(line.split(" ")[1]);
				if (warriors.get(selected) == null) {
					selected = 0;
				}
				menu(warriors.get(selected).getName()+" is the selected warrior");
				continue;
			} else if (command.equalsIgnoreCase("enemy")) {
				if (selected == 0) {
					System.out.println("Select a warrior first!");
					menu("");
					continue;
				}
				enemyId = Integer.parseInt(line.split(" ")[1]);
				if (warriors.get(enemyId) == null || selected == enemyId) {
					System.out.println("Wrong enemy id!");
					menu(warriors.get(enemyId).getName()+" is the selected enemy");
					continue;
				}
				menu(warriors.get(enemyId).getName()+" is the selected enemy");
				continue;
			} else if (command.equalsIgnoreCase("battle")) {
				if (selected == 0) {
					System.out.println("Select a warrior first!");
					menu("");
					continue;
				}
				if (enemyId == 0) {
					System.out.println("Select an enemy first!");
					menu("");
					continue;
					
				}
			
				
				String battleResult = Warrior.battle(warriors.get(selected), warriors.get(enemyId))[0];
				st.execute("UPDATE warriors_stats.warriors SET experience = "+warriors.get(selected).getExperince()+" WHERE id = " + selected);
				st.execute("UPDATE warriors_stats.warriors SET experience = "+warriors.get(enemyId).getExperince()+" WHERE id = " + enemyId);
				System.out.println(battleResult);
				menu("The warrior "+warriors.get(selected).getName()+" is fighting "+warriors.get(enemyId).getName());
				continue;
			} else if (command.equalsIgnoreCase("train")) {
				if (selected == 0) {
					System.out.println("Select a warrior first!");
					continue;
				}
				System.out.println("Enter description");
				String description = scan.nextLine();
				System.out.println("Enter experience points");
				int experiencePoints = scan.nextInt();
				System.out.println("Enter minimum level");
				int minimumLevel = scan.nextInt();
				String trainResult = warriors.get(selected).train(description, experiencePoints, minimumLevel);
				st.execute("UPDATE warriors_stats.warriors SET experience = "+warriors.get(selected).getExperince()+" WHERE id = " + selected);
				System.out.println(trainResult);
				menu("Train");
				continue;
			}
			
		}
		con.close();
	}

	public static void menu(String selected) {
		System.out.println("===============================================");
		System.out.println("Selected warrior (" + selected + "):");
		System.out.println("Select a command from the list:");
		System.out.println("- list warriors");
		System.out.println("- create (warrior name)");
		System.out.println("- Delete (warrior id)");
		System.out.println("- warrior(warrior id)");
		System.out.println("- enemy(warrior id)");
		System.out.println("- battle ");
		System.out.println("- train (description experience-points minimum-level)");
		System.out.println("- exit");
		System.out.println("===============================================");
	}

}
