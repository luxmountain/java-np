# RMI Client - Vigenère Cipher Encoder

## Mô tả
Chương trình RMI Client kết nối tới RMI Server để thực hiện mã hóa Vigenère cho chuỗi.

## Cấu trúc
- **Package RMI**: Chứa interface CharacterService
- **Package RMIClient**: Chứa class BBylrcvA (client implementation)

## Cách sử dụng

### 1. Cấu hình
Mở file `src/RMIClient/BBylrcvA.java` và thay đổi:
- `studentCode`: Mã sinh viên của bạn (hiện tại: B22DCVT445)
- `url`: Địa chỉ RMI server (hiện tại: rmi://203.162.10.109/RMICharacterService)

### 2. Biên dịch
```bash
cd src
javac -encoding UTF-8 RMI/CharacterService.java RMIClient/BBylrcvA.java
```

### 3. Chạy chương trình
```bash
cd src
java RMIClient.BBylrcvA
```

## Chức năng
1. Kết nối tới RMI Server
2. Nhận chuỗi từ server theo định dạng: "Từ khóa;Chuỗi đầu vào"
3. Mã hóa chuỗi bằng thuật toán Vigenère Cipher
4. Gửi kết quả mã hóa về server

## Ví dụ
Input từ server: `PTIT;HELLO`
- Từ khóa: PTIT
- Chuỗi gốc: HELLO
- Kết quả mã hóa: WXTED

### Giải thích mã hóa Vigenère:
- H + P = W (7 + 15 = 22)
- E + T = X (4 + 19 = 23)
- L + I = T (11 + 8 = 19)
- L + T = E (11 + 19 = 4)
- O + P = D (14 + 15 = 3)
