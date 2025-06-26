package main.dao;

import main.model.Entry;
import main.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
* Entry 객체를 DB에 저장하거나 조회하는 DAO 클래스
*/
public class EntryDao {

    /*
    * 가계부 내역을 DB에 삽입하는 메서드
    * @param entry 저장할 Entry 객체R
    * @return 성공 시 true, 실패 시 false
    */

    public boolean insertEntry(Entry entry) {
        String sql = "INSERT INTO entries (date, type, category, amount, memo) VALUES (?, ?, ?, ?, ?)";

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, Date.valueOf(entry.getDate()));
            pstmt.setString(2, entry.getType());
            pstmt.setString(3, entry.getCategory());
            pstmt.setInt(4, entry.getAmount());
            pstmt.setString(5, entry.getMemo());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows == 1;

            } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
    * 모든 가계부 내역을 조회하는 메서드
    * @return Entry 리스트
    */

    public List<Entry> getAllEntries() {
        List<Entry> list = new ArrayList<>();
        String sql = "SELECT id, date, type, category, amount, memo FROM entries ORDER BY date DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()){

            while (rs.next()) {
                Entry entry = new Entry();
                entry.setId(rs.getInt("id"));
                entry.setDate(rs.getDate("date").toLocalDate());
                entry.setType(rs.getString("type"));
                entry.setCategory(rs.getString("category"));
                entry.setAmount(rs.getInt("amount"));
                entry.setMemo(rs.getString("memo"));

                list.add(entry);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    /*
    * ID를 기준으로 가계부 내역 삭제
    * @param id 삭제할 entry ID
    * @return 성공 시 true, 실패 시 false
    */

    public boolean deleteEntry(int id) {
        String sql = "DELETE FROM entries WHERE id = ?";

        try(Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*
    * 기존 Entry 수정
    * @param entry 수정할 entry 객체 (id 포함)
    * @return 성공 시 true, 실패 시 false
    */

    public boolean updateEntry(Entry entry) {
        String sql = "UPDATE entries SET date=?, type=?, category=?, amount=?, memo=? WHERE id =?";

        try (Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, Date.valueOf(entry.getDate()));
            pstmt.setString(2, entry.getType());
            pstmt.setString(3, entry.getCategory());
            pstmt.setInt(4, entry.getAmount());
            pstmt.setString(5, entry.getMemo());
            pstmt.setInt(6, entry.getId());

            return pstmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
