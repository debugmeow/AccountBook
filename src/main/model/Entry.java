package main.model;

import java.time.LocalDate;

/*
 * 수입/지출 데이터를 표현하는 모델 클래스
*/
public class Entry {

    private int id;             // 고유 ID (DB 자동 증가)
    private LocalDate date;     // 날짜
    private String type;        // "income" 또는 "expense"
    private String category;    // 분류 (예: 식비, 교통비 등)
    private int amount;         // 금액
    private String memo;        // 메모 내용 (선택)

    // 기본 생성자
    public Entry() {
    }

    // 모든 필드를 받는 생성자
    public Entry(int id, LocalDate date, String type, String category, int amount, String memo) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.memo = memo;
    }

    // 데이터베이스 저장 시 id는 자동으로 증가하므로 id 없이 생성
    public Entry (LocalDate date, String type, String category, int amount, String memo) {
        this.date = date;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.memo = memo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "[" + id + "]" + "[" + date + "] "
                + (type.equals("income") ? "[수입]" : "[지출]") + " "
                + "[목록] " + category + " - " + "₩ " + amount + "원 "
                + (memo != null ? "(" + memo + ")" : "");
    }
}
