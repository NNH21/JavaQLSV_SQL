package JavaQLSV_SQL.sinhvienSQL;

public class Student {
    private int id;
    private String name, address, age;
    private double gpa;

    public Student() {
    }

    public Student(int id, String age, String name, double gpa, String address) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.gpa = gpa;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student: "+id+" | "+name+" | "+age+" | "+address+" | "+gpa;
    }
    public String toFile() {
        return id+" | "+name+" | "+age+" | "+address+" | "+gpa + "\n";
    }
    public void parse(String line){
        try{
            // Chỉ sử dụng "|" làm dấu phân cách
            String[] data = line.split("\\|");
            id = Integer.parseInt(data[0].trim());
            name = data[1].trim();
            age = data[2].trim(); // Nếu age là ngày sinh, bạn nên đặt tên biến là birthDate
            address = data[3].trim();
            gpa = Double.parseDouble(data[4].trim());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Loi dinh dang file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Loi dinh dang so: " + e.getMessage());
        }
    }

}
