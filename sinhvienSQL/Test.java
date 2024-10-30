package JavaQLSV_SQL.sinhvienSQL;/*
    Đọc và ghi file với đối tượng
    Viết chương trình quản lý sinh viên với các chức năng:
    1. Thêm sinh viên.
    2. Sửa sinh viên theo ID.
    3. Xóa sinh viên theo ID.
    4. Sắp xếp sinh viên theo GPA.
    5. Sắp xếp sinh viên theo tên.
    6. Hiển thị sinh viên.
    7. Lưu thông tin sinh viên vào file Student.txt.
    8. Đọc thông tin sinh viên từ file Student.txt và hiển thị ra màn hình.
    9. Chuyển thông tin từ file sang database.
    10. Đọc thông tin sinh viên từ database.
 */

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentList listSV = new StudentList();
        int luaChon = 0;
        do{
            System.out.println("--------------------MENU--------------------");
            System.out.println("1. Them sinh vien. ");
            System.out.println("2. Sua sinh vien theo ID. ");
            System.out.println("3. Xoa sinh vien theo ID. ");
            System.out.println("4. Sap xep sinh vien theo GPA. ");
            System.out.println("5. Sap xep sinh vien theo ID. ");
            System.out.println("6. Hien thi sinh vien. ");
            System.out.println("7. Luu thong tin sinh vien vao file Student.txt.");
            System.out.println("8. Doc thong tin sinh vien tu file Student.txt va hien thi ra man hinh. ");
            System.out.println("9. Chuyen thong tin tu file sang database. ");
            System.out.println("10. Doc thong tin sinh vien tu database");
            System.out.println("11. Xoa thong tin cu va cap nhat lai thong tin moi vao database");
            System.out.println("0. Thoat. ");
            System.out.println("Chon: ");
            luaChon = Integer.parseInt(sc.nextLine());
            switch(luaChon){
                case 1:
                    listSV.addSinhVien();
                    break;
                case 2:
                    listSV.editById();
                    break;
                case 3:
                    listSV.DeleteById();
                    break;
                case 4:
                    listSV.SortByGpa();
                    break;
                case 5:
                    listSV.SortByID();
                    break;
                case 6:
                    listSV.showStudent();
                    break;
                 case 7:
                    listSV.SaveFile();
                    break;
                case 8:
                    listSV.ReadFile();
                    break;
                    case 9:
                    listSV.SaveToDB();
                    break;
                case 10:
                    listSV.ReadFromDB();
                    break;
                case 11:
                    listSV.UpdateDB();
                    break;
                case 0:
                    System.out.println("Ket thuc chuong trinh.");
                    break;
                default:
                    System.out.println("Nhap sai, moi nhap lai.");
                    break;
            }
        }while(luaChon!=0);
    }


}
