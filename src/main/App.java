package main;

import main.dao.EntryDao;
import main.model.Entry;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/*
* 가계부 프로그램 실행 전압점
*/
public class App {

    private static final Scanner scanner = new Scanner(System.in);
    private static final EntryDao entryDAO = new EntryDao();

    public static void main (String[] args) {

        System.out.println("가계부 프로그램 시작");

        while (true) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addEntry();
                    break;
                case "2":
                    viewEntries();
                    break;
                case "3":
                    deleteEntry();
                    break;
                case "4":
                    updateEntry();
                    break;
                case "0":
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
            }
        }
    }



    private static void printMenu() {
        System.out.println("\n=== 메뉴 ===");
        System.out.println("1. 수입/지출 등록");
        System.out.println("2. 전체 내역 조회");
        System.out.println("3. 내역 삭제");
        System.out.println("4. 내역 수정");
        System.out.println("0. 종료");
        System.out.println("선택 : ");
    }

    private static void addEntry() {
        try {
            System.out.print("날짜 입력 (yyyy-mm-dd): ");
            LocalDate date = LocalDate.parse(scanner.nextLine());

            System.out.print("유형을 선택하세요 (1: 수입, 2: 지출): ");
            String choice = scanner.nextLine().trim();

            String type;
            if (choice.equals("1")) {
                type = "income";
            } else if (choice.equals("2")) {
                type = "expense";
            } else {
                System.out.println("잘못된 선택입니다.");
                return;
            }

            System.out.println("카테고리 입력 (예: 식비, 교통비 등): ");
            String category = scanner.nextLine();

            System.out.println("금액 입력: ");
            int amount = Integer.parseInt(scanner.nextLine());

            System.out.println("메모 입력 (선택, 없으면 Enter): ");
            String memo = scanner.nextLine();

            Entry entry = new Entry(date, type, category, amount, memo);
            boolean success = entryDAO.insertEntry(entry);

            if (success) {
                System.out.println("등록 완료!");
            } else {
                System.out.println("등록 실패.");
            }

        } catch (Exception e) {
            System.out.println("입력 형식이 잘못되었습니다. 다시 시도해주세요.");
        }
    }

    private static void viewEntries() {
        List<Entry> entries = entryDAO.getAllEntries();

        if (entries.isEmpty()) {
            System.out.println("내역이 없습니다.");
        } else {
            System.out.println("\n=== 전체 가계부 내역 ===");
            for (Entry entry : entries) {
                System.out.println(entry);
            }
        }
    }

    private static void deleteEntry() {
        viewEntries();

        System.out.print("삭제할 ID를 입력하세요 : ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            boolean success = entryDAO.deleteEntry(id);
            if (success) {
                System.out.println("삭제 완료!");
            } else  {
                System.out.println("삭제 실패, ID 다시 확인해주세요.");
            }
        } catch (NumberFormatException e) {
            System.out.println("숫자 형식이 아닙니다. ID는 숫자로 입력해주세요.");
        }
    }

    private static void updateEntry() {
        viewEntries();

        System.out.print("수정할 ID를 입력하세요.");
        int id;

        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("숫자 ID만 입력하세요.");
            return;
        }

        // 기존 항목 검색
        List<Entry> all = entryDAO.getAllEntries();
        Entry old = all.stream().filter(e -> e.getId() == id).findFirst().orElse(null);

        if (old == null) {
            System.out.println("해당 ID의 내역이 없습니다.");
            return;
        }

        // 입력 받기 (기존 값 유지 옵션 포함)
        try {
            System.out.print("Enter 시 기존 값 유지");
            System.out.print("날짜 입력 (yyyy-mm-dd, 기존: " + old.getDate() + "): ");
            String dateInput = scanner.nextLine();
            LocalDate date = dateInput.isEmpty() ? old.getDate() : LocalDate.parse(dateInput);

            System.out.print("유형을 선택하세요 (1: 수입, 2:지출, 기존: " + (old.getType().equals("income") ? "1" : "2") + "): ");
            String choice = scanner.nextLine().trim();

            String type;
            if (choice.isEmpty()) {
                type = old.getType();
            } else if (choice.equals("1")) {
                type = "income";
            } else if (choice.equals("2")) {
                type = "expense";
            } else {
                System.out.println("잘못된 선택입니다.");
                return;
            }

            System.out.print("카테고리 입력 (기존: "+ old.getCategory() + "): ");
            String categoryInput = scanner.nextLine();
            String category = categoryInput.isEmpty() ? old.getCategory() : categoryInput;

            System.out.print("금액 입력 (기존: " + old.getAmount() + "): ");
            String amountInput = scanner.nextLine();
            int amount = amountInput.isEmpty() ? old.getAmount() : Integer.parseInt(amountInput);

            System.out.print("메모 입력 (기준: " + old.getMemo() + "): ");
            String memoInput = scanner.nextLine();
            String memo = memoInput.isEmpty() ? old.getMemo() : memoInput;

            Entry updated = new Entry(id, date, type, category, amount, memo);
            boolean success = entryDAO.updateEntry(updated);

            System.out.println(success ? "수정완료!" : "수정실패.");

        } catch (Exception e) {
            System.out.println("입력 형식 오류입니다. 다시 시도해주세요.");
        }
    }
}

