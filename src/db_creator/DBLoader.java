package db_creator;

import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import servidor.Util;

public class DBLoader {
	static JFrame frame;
	private static void createAndShowGUI() {
		frame = new JFrame("DB LOAD");
  		frame.setVisible(true);
		frame.setSize(300,150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		frame.add(panel);
		JLabel label = new JLabel("Creating database..");		
		label.setHorizontalAlignment(SwingConstants.CENTER); // set the horizontal alignement on the x axis !
		label.setVerticalAlignment(SwingConstants.CENTER);
		panel.add(label);
	}
	
	private static void loadDb() throws Exception{
		if(!Util.fileExists("database.sqlite")){
			throw new Exception("Database file 'database.sqlite' not found");
		}
		Class.forName("org.sqlite.JDBC");
		Connection oldDb = null;
		Connection newDb = null;
		oldDb = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
		newDb = DriverManager.getConnection("jdbc:sqlite:databaseSD_new.sqlite");
		oldDb.setAutoCommit(false);
		newDb.setAutoCommit(false);
		Statement stmt = null;
		stmt = oldDb.createStatement();
		
		ResultSet originRs = stmt.executeQuery( "select Ano, team_long_name, player_name, SUM(Wins), SUM(Losses) from(\r\n" + 
				"\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"Player.player_name,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.home_team_api_id\r\n" + 
				"INNER JOIN Player ON Player.player_api_id = Match.home_player_1\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name,Player.player_name \r\n" + 
				"\r\n" + 
				"union all\r\n" + 
				"\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"Player.player_name,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.home_team_api_id\r\n" + 
				"INNER JOIN Player ON Player.player_api_id = Match.home_player_2\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name,Player.player_name\r\n" + 
				"\r\n" + 
				"union all\r\n" + 
				"\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"Player.player_name,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.home_team_api_id\r\n" + 
				"INNER JOIN Player ON Player.player_api_id = Match.home_player_3\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name,Player.player_name\r\n" + 
				"\r\n" + 
				"union all\r\n" + 
				"\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"Player.player_name,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.home_team_api_id\r\n" + 
				"INNER JOIN Player ON Player.player_api_id = Match.home_player_4\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name,Player.player_name\r\n" + 
				"\r\n" + 
				"union all\r\n" + 
				"\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"Player.player_name,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.home_team_api_id\r\n" + 
				"INNER JOIN Player ON Player.player_api_id = Match.home_player_5\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name,Player.player_name\r\n" + 
				"\r\n" + 
				"union all\r\n" + 
				"\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"Player.player_name,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.home_team_api_id\r\n" + 
				"INNER JOIN Player ON Player.player_api_id = Match.home_player_6\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name,Player.player_name\r\n" + 
				"\r\n" + 
				"union all\r\n" + 
				"\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"Player.player_name,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.home_team_api_id\r\n" + 
				"INNER JOIN Player ON Player.player_api_id = Match.home_player_7\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name,Player.player_name\r\n" + 
				"\r\n" + 
				"union all\r\n" + 
				"\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"Player.player_name,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.home_team_api_id\r\n" + 
				"INNER JOIN Player ON Player.player_api_id = Match.home_player_8\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name,Player.player_name\r\n" + 
				"\r\n" + 
				"union all\r\n" + 
				"\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"Player.player_name,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.home_team_api_id\r\n" + 
				"INNER JOIN Player ON Player.player_api_id = Match.home_player_9\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name,Player.player_name\r\n" + 
				"\r\n" + 
				"union all\r\n" + 
				"\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"Player.player_name,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.home_team_api_id\r\n" + 
				"INNER JOIN Player ON Player.player_api_id = Match.home_player_10\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name,Player.player_name\r\n" + 
				"\r\n" + 
				"union all\r\n" + 
				"\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"Player.player_name,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.home_team_api_id\r\n" + 
				"INNER JOIN Player ON Player.player_api_id = Match.home_player_11\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name,Player.player_name\r\n" + 
				"\r\n" + 
				"union all\r\n" + 
				"\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"Player.player_name,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.home_team_api_id\r\n" + 
				"INNER JOIN Player ON Player.player_api_id = Match.away_player_1\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name,Player.player_name \r\n" + 
				"\r\n" + 
				"union all\r\n" + 
				"\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"Player.player_name,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.home_team_api_id\r\n" + 
				"INNER JOIN Player ON Player.player_api_id = Match.away_player_2\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name,Player.player_name\r\n" + 
				"\r\n" + 
				"union all\r\n" + 
				"\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"Player.player_name,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.home_team_api_id\r\n" + 
				"INNER JOIN Player ON Player.player_api_id = Match.away_player_3\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name,Player.player_name\r\n" + 
				"\r\n" + 
				"union all\r\n" + 
				"\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"Player.player_name,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.home_team_api_id\r\n" + 
				"INNER JOIN Player ON Player.player_api_id = Match.away_player_4\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name,Player.player_name\r\n" + 
				"\r\n" + 
				"union all\r\n" + 
				"\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"Player.player_name,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.home_team_api_id\r\n" + 
				"INNER JOIN Player ON Player.player_api_id = Match.away_player_5\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name,Player.player_name\r\n" + 
				"\r\n" + 
				"union all\r\n" + 
				"\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"Player.player_name,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.home_team_api_id\r\n" + 
				"INNER JOIN Player ON Player.player_api_id = Match.away_player_6\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name,Player.player_name\r\n" + 
				"\r\n" + 
				"union all\r\n" + 
				"\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"Player.player_name,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.home_team_api_id\r\n" + 
				"INNER JOIN Player ON Player.player_api_id = Match.away_player_7\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name,Player.player_name\r\n" + 
				"\r\n" + 
				"union all\r\n" + 
				"\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"Player.player_name,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.home_team_api_id\r\n" + 
				"INNER JOIN Player ON Player.player_api_id = Match.away_player_8\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name,Player.player_name\r\n" + 
				"\r\n" + 
				"union all\r\n" + 
				"\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"Player.player_name,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.home_team_api_id\r\n" + 
				"INNER JOIN Player ON Player.player_api_id = Match.away_player_9\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name,Player.player_name\r\n" + 
				"\r\n" + 
				"union all\r\n" + 
				"\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"Player.player_name,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.home_team_api_id\r\n" + 
				"INNER JOIN Player ON Player.player_api_id = Match.away_player_10\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name,Player.player_name\r\n" + 
				"\r\n" + 
				"union all\r\n" + 
				"\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"Player.player_name,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.home_team_api_id\r\n" + 
				"INNER JOIN Player ON Player.player_api_id = Match.away_player_11\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name,Player.player_name\r\n" + 
				"\r\n" + 
				") a \r\n" + 
				"group by Ano, team_long_name, player_name  ;" );
		stmt = newDb.createStatement();
		stmt.execute("CREATE TABLE IF NOT EXISTS Players(\r\n" + 
				"  \"Ano\" TEXT,\r\n" + 
				"  \"team_long_name\" TEXT,\r\n" + 
				"  \"player_name\" TEXT,\r\n" + 
				"  \"Wins\" TEXT,\r\n" + 
				"  \"Losses\" TEXT\r\n" + 
				");");
		stmt.execute("CREATE TABLE IF NOT EXISTS Times(\r\n" + 
				"  \"Ano\" TEXT,\r\n" + 
				"  \"team_long_name\" TEXT,\r\n" + 
				"  \"Wins\" TEXT,\r\n" + 
				"  \"Losses\" TEXT\r\n" + 
				");\r\n" + 
				"");
		newDb.commit();
		stmt.execute("create index if not exists ano_player 		on Players 	(Ano,player_name);");
		stmt.execute("create index if not exists ano_team			on Times	(Ano,team_long_name);");
		stmt.execute("create index if not exists ano_team_player	on Players	(Ano,team_long_name,player_name);");
		newDb.commit();
		PreparedStatement prep_stmt = null;
		while ( originRs.next() ) {
			//System.out.println("'"+originRs.getString(1)+"','"+originRs.getString(2)+"','"+originRs.getString(3)+"','"+originRs.getString(4)+"','"+originRs.getString(5)+"'");
			prep_stmt = newDb.prepareStatement("INSERT INTO Players(Ano,team_long_name,player_name,Wins,Losses) VALUES (?,?,?,?,?);");
			prep_stmt.setString(1, originRs.getString(1));
			prep_stmt.setString(2, originRs.getString(2));
			prep_stmt.setString(3, originRs.getString(3));
			prep_stmt.setString(4, originRs.getString(4));
			prep_stmt.setString(5, originRs.getString(5));
			prep_stmt.executeUpdate();		
	      }
		newDb.commit();
		stmt = oldDb.createStatement();
		originRs = stmt.executeQuery("select Ano, team_long_name, SUM(Wins), SUM(Losses) from(\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.home_team_api_id\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name\r\n" + 
				"\r\n" + 
				"union all\r\n" + 
				"\r\n" + 
				"select substr(Match.date,0,5) Ano,\r\n" + 
				"Team.team_long_name,\r\n" + 
				"SUM(Match.home_team_goal < Match.away_team_goal) Wins,\r\n" + 
				"SUM(Match.home_team_goal > Match.away_team_goal) Losses\r\n" + 
				"from Match\r\n" + 
				"INNER JOIN Team ON Team.team_api_id = Match.away_team_api_id\r\n" + 
				"group by substr(Match.date,0,5),Team.team_long_name\r\n" + 
				"\r\n" + 
				") a \r\n" + 
				"group by Ano, team_long_name  ;");
		stmt = newDb.createStatement();
		while ( originRs.next() ) {
			//System.out.println("'"+originRs.getString(1)+"','"+originRs.getString(2)+"','"+originRs.getString(3)+"','"+originRs.getString(4)+"','"+originRs.getString(5)+"'");
			prep_stmt = newDb.prepareStatement("INSERT INTO Times(Ano,team_long_name,Wins,Losses) VALUES (?,?,?,?);");
			prep_stmt.setString(1, originRs.getString(1));
			prep_stmt.setString(2, originRs.getString(2));
			prep_stmt.setString(3, originRs.getString(3));
			prep_stmt.setString(4, originRs.getString(4));
			prep_stmt.executeUpdate();
	      }
		newDb.commit();
		
		originRs.close();
		stmt.close();
	}

	public static void main(String[] args) throws Exception {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();                
            }
        });
		loadDb();
		frame.setVisible(false);
		frame.dispose();
	}
		// TODO Auto-generated method stub
		
}
