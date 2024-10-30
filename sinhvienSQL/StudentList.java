package JavaQLSV_SQL.sinhvienSQL;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class StudentList {
    private ArrayList<Student> students;
    Scanner sc = new Scanner(System.in);
    private static final String URL = "jdbc:mysql://localhost:3306/qlsv";
    private static final String USER = "root";
    private static final String PASSWORD = "nnh212005";  // Đổi mật khẩu MySQL của bạn
    public StudentList(ArrayList<Student> students) {
        this.students = students;
    }

    public StudentList() {
        this.students = new ArrayList<>();
    }

    public void addSinhVien() {
        System.out.println("Nhap so luong sinh vien: ");
        int n = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < n; i++) {
            System.out.println("Nhap ID sinh vien thu " + (i + 1) + ": ");
            int id = Integer.parseInt(sc.nextLine());
            if(checkID(id)){
                System.out.println("ID sinh vien da ton tai. Vui long nhap lai.");
                i--;
                continue;
            }
            System.out.println("Nhap ten sinh vien: ");
            String name = sc.nextLine();
            System.out.println("Nhap ngay sinh cua sinh vien: ");
            String age  = sc.nextLine();
            System.out.println("Nhap dia chi sinh vien: ");
            String address = sc.nextLine();
            System.out.println("Nhap diem trung binh sinh vien: ");
            double gpa = Double.parseDouble(sc.nextLine());
            Student std = new Student(id, age, name, gpa, address);
            students.add(std);
            String sql = "INSERT INTO students (studentID, studentName, studentAge, studentAddress, studentGPA) VALUES (?, ?, ?, ?, ?)";
            try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){
                PreparedStatement pre = connection.prepareStatement(sql);
                pre.setInt(1, id);
                pre.setString(2, name);
                pre.setString(3, age);
                pre.setString(4, address);
                pre.setDouble(5, gpa);
                int rs = pre.executeUpdate();
                if(rs > 0) {
                    System.out.println("Thêm sinh viên thành công.");
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public boolean checkID(int id){
        String sql = "SELECT COUNT(*) FROM students WHERE studentID = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pre = connection.prepareStatement(sql)) {
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu đếm được số lượng lớn hơn 0 thì ID đã tồn tại
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false; // ID không tồn tại
    }


    public <connection> void editById() {
        System.out.println("Nhap ID can sua: ");
        int id = Integer.parseInt(sc.nextLine());
        String sql = "UPDATE students SET studentName = ?, studentAge = ?, studentAddress = ?, studentGPA = ? WHERE studentID = ?";
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){
            PreparedStatement pre = connection.prepareStatement(sql);{
                for (Student std : students) {
                    if (std.getId() == id) {
                        // Sửa thông tin sinh viên (nếu những thông tin nào không muốn chỉnh sửa thì bỏ qua)
                        System.out.println("Nhap ten moi: ");
                        String newName = sc.nextLine();
                        if(!newName.isEmpty()){
                            std.setName(newName);
                            pre.setString(1, std.getName());
                        }else{
                            pre.setString(1, std.getName());
                        }
                        System.out.println("Nhap ngay sinh moi: ");
                        String newAge = sc.nextLine();
                        if(!newAge.isEmpty()) {
                            std.setAge(newAge);
                            pre.setString(2, std.getAge());
                        }else{
                            pre.setString(2, std.getAge());
                        }
                        System.out.println("Nhap dia chi moi: ");
                        String newAddress = sc.nextLine();
                        if(!newAddress.isEmpty()){
                            std.setAddress((newAddress));
                            pre.setString(3, std.getAddress());
                        }else{
                            pre.setString(3, std.getAddress());
                        }
                        System.out.println("Nhap diem trung binh moi: ");
                        String newGpa = sc.nextLine();
                        if(!newGpa.isEmpty()){ {
                            std.setGpa(Double.parseDouble(newGpa));
                            pre.setDouble(4, std.getGpa());
                        }
                        }else{
                            pre.setDouble(4, std.getGpa());
                        }
                        pre.setInt(5, id);
                        int rs = pre.executeUpdate();
                        if(rs > 0){
                            System.out.println("Sua thanh cong.");
                            return;
                        }
                    }
                }
            }
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Khong tim thay sinh vien co ID = " + id + " de sua.");
    }

    public void DeleteById() {
        System.out.println("Nhap ID can xoa: ");
        int id = Integer.parseInt(sc.nextLine());
        String sql = "DELETE FROM students WHERE studentID = ?";
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){
            PreparedStatement pre = connection.prepareStatement(sql);
            for (Student std : students) {
                if (std.getId() == id) {
                    students.remove(std);
                    pre.setInt(1, id);
                    int rs = pre.executeUpdate();
                    if(rs > 0){
                        System.out.println("Xoa thanh cong.");
                        return;
                    }
                }
            }
            pre.setInt(1, id);
    }catch (SQLException e) {
        throw new RuntimeException(e);
    }
        System.out.println("Khong tim thay sinh vien co ID = " + id + " de xoa.");
    }

    public void SortByGpa() {
        String selectSql = "SELECT * FROM students ORDER BY studentGPA DESC";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement selectPre = connection.prepareStatement(selectSql);
             ResultSet rs = selectPre.executeQuery()) {
            students.clear();
            while (rs.next()) {
                int id = rs.getInt("studentID");
                String name = rs.getString("studentName");
                String age = rs.getString("studentAge");
                String address = rs.getString("studentAddress");
                double gpa = rs.getDouble("studentGPA");
                Student std = new Student(id, age, name, gpa, address);
                students.add(std);
            }
            Collections.sort(students, Comparator.comparingDouble(Student::getGpa).reversed());
            System.out.println("Danh sach sinh vien sau khi sap xep theo GPA: ");
            for (Student std : students) {
                System.out.println(std);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void SortByID() {
        String selectSql = "SELECT * FROM students";
        ArrayList<Student> sortedStudents = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement selectPre = connection.prepareStatement(selectSql);
             ResultSet rs = selectPre.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("studentID");
                String name = rs.getString("studentName");
                String age = rs.getString("studentAge");
                String address = rs.getString("studentAddress");
                double gpa = rs.getDouble("studentGPA");
                Student std = new Student(id, age, name, gpa, address);
                sortedStudents.add(std);
            }
            Collections.sort(sortedStudents, Comparator.comparingInt(Student::getId));
            for (Student std : sortedStudents) {
                String updateSql = "UPDATE students SET studentID = ? WHERE studentID = ?";
                try (PreparedStatement updatePre = connection.prepareStatement(updateSql)) {
                    updatePre.setInt(1, std.getId());
                    updatePre.setInt(2, std.getId());
                    updatePre.executeUpdate();
                }
            }
            System.out.println("Sap xep thanh cong.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void showStudent() {
        String sql = "SELECT * FROM students";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("\nDanh sách sinh viên:");
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("studentID"));
                System.out.println("Tên: " + resultSet.getString("studentName"));
                System.out.println("Tuổi: " + resultSet.getInt("studentAge"));
                System.out.println("Địa chỉ: " + resultSet.getString("studentAddress"));
                System.out.println("Điểm trung bình: " + resultSet.getDouble("studentGPA"));
                System.out.println("--------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void SaveFile() {
        boolean check = false;
        try {
            FileOutputStream fos = new FileOutputStream("src/sinhvienSQl/Student.txt");
            for (Student std : students) {
                String line = std.toFile();
                fos.write(line.getBytes());
            }
            fos.close();
            check = true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (check) {
            System.out.println("Luu file thanh cong.");
        } else {
            System.out.println("Luu file that bai.");
        }
    }

    public void ReadFile() {
        try {
            FileInputStream fis = new FileInputStream("src/sinhvienSQl/Student.txt");
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                if(line.isEmpty()){
                    continue;
                }
                Student std = new Student();
                std.parse(line);
                students.add(std);
            }
            br.close();
            isr.close();
            fis.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Doc file thanh cong.");
        System.out.println("Danh sach sinh vien sau khi doc tu file: ");
        for (Student std : students) {
            System.out.println(std);
        }
    }

    public void SaveToDB() {
        String sql = "INSERT INTO students (studentID, studentName, studentAge, studentAddress, studentGPA) VALUES (?, ?, ?, ?, ?)";
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){
            PreparedStatement pre = connection.prepareStatement(sql);
            for (Student std : students) {
                pre.setInt(1, std.getId());
                pre.setString(2, std.getName());
                pre.setString(3, std.getAge());
                pre.setString(4, std.getAddress());
                pre.setDouble(5, std.getGpa());
                int rs = pre.executeUpdate();
                if(rs > 0){
                    System.out.println("Chuyen du lieu tu file sang database thanh cong.");
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void ReadFromDB() {
        String sql = "SELECT * FROM students";
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()){
                int id = rs.getInt("studentID");
                String name = rs.getString("studentName");
                String age = rs.getString("studentAge");
                String address = rs.getString("studentAddress");
                double gpa = rs.getDouble("studentGPA");
                Student std = new Student(id, age, name, gpa, address);
                students.add(std);
            }
            System.out.println("Danh sach sinh vien sau khi doc tu database: ");
            for (Student std : students) {
                System.out.println(std);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void UpdateDB() {
        String sql = "UPDATE students SET studentName = ?, studentAge = ?, studentAddress = ?, studentGPA = ? WHERE studentID = ?";
        try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){
            PreparedStatement pre = connection.prepareStatement(sql);
            for (Student std : students) {
                pre.setString(1, std.getName());
                pre.setString(2, std.getAge());
                pre.setString(3, std.getAddress());
                pre.setDouble(4, std.getGpa());
                pre.setInt(5, std.getId());
                int rs = pre.executeUpdate();
                if(rs > 0){
                    System.out.println("Cap nhat du lieu thanh cong.");
                }
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
