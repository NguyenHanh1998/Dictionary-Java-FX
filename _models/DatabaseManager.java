package _models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

interface IDatabaseManager {
    public void insertDictionary(String name);

    public void deleteDictionary(int id);

    public void insertWord(int id, String word, String mean);

    public void deleteWord(int id, String word);

    public void updateWord(int id, String word, String newWord);

    public void updateMean(int id, String word, String mean);

    public Word getWord(int id, String word);

    public String[] getDictionaries();
}

public class DatabaseManager implements IDatabaseManager {

    public Connection connect() {
        String url = "jdbc:sqlite:dictionary.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    @Override
    public void insertWord(int id, String word, String mean) {
        String sql = "INSERT INTO Word(id,word,mean) VALUES(?,?,?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, word);
            pstmt.setString(3, mean);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteWord(int id, String word) {
        String sql = "DELETE FROM Word WHERE word LIKE ? AND id = ?";
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, word);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateWord(int id, String word, String newWord) {
        String sql = "UPDATE Word SET word = ? WHERE word = ? AND id = ?";
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newWord);
            pstmt.setString(2, word);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateMean(int id, String word, String mean) {
        String sql = "UPDATE Word SET mean = ? WHERE word = ? AND id = ?";
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mean);
            pstmt.setString(2, word);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Word getWord(int id, String word) {
        Word returnWord = null;
        String sql = "SELECT * FROM Word WHERE word = ? AND id = ?";
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, word);
            pstmt.setInt(2, id);
            ResultSet resultSet = pstmt.executeQuery();
            returnWord = new Word(resultSet.getString(1), resultSet.getString(2));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return returnWord;
    }

    @Override
    public void insertDictionary(String name) {
        String sql = "INSERT INTO Dictionary(name) VALUES(?)";
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteDictionary(int id) {
        String sql = "DELETE FROM Dictionary WHERE id = ?";
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String[] getDictionaries() {
        String sql = "SELECT name FROM Dictionary";
        ArrayList<String> result = new ArrayList<String>();
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet resultSet = pstmt.executeQuery();
            while(resultSet.next()) {
                result.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result.toArray(new String[result.size()]);
    }

}