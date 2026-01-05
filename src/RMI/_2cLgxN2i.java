package RMI;

import java.rmi.Naming;

public class _2cLgxN2i {
    public static void main(String[] args) {
        String studentCode = "B22DCVT445";
        String qCode = "2cLgxN2i";
        String url = "rmi://203.162.10.109/RMIByteService";

        try {
            ByteService service = (ByteService) Naming.lookup(url);

            byte[] data = service.requestData(studentCode, qCode);
            System.out.println("Đã nhận dữ liệu từ server (dạng chuỗi): " + new String(data, "ASCII"));

            int shift = data.length;
            byte[] encrypted = caesarEncrypt(data, shift);
            System.out.println("Dữ liệu sau mã hóa: " + new String(encrypted, "ASCII"));

            service.submitData(studentCode, qCode, encrypted);
            System.out.println("Đã gửi dữ liệu mã hóa về server.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] caesarEncrypt(byte[] data, int shift) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = (byte) (data[i] + shift);
        }
        return result;
    }
}
