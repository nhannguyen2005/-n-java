// Class Sản Phẩm 
class SanPham {
    private String maSP;
    private String tenSP;
    private double gia;
    private int soLuongTon;

    public SanPham(String maSP, String tenSP, double gia, int soLuongTon) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.gia = gia;
        this.soLuongTon = soLuongTon;
    }

    public String getMaSP() {
        return maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public double getGia() {
        return gia;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void truSoLuong(int soLuongMua) {
        if (soLuongMua <= soLuongTon) {
            soLuongTon -= soLuongMua;
        } else {
            System.out.println("⚠️ Không đủ hàng trong kho cho sản phẩm: " + tenSP);
        }
    }

    @Override
    public String toString() {
        return tenSP + " - Giá: " + gia + "₫ - Còn: " + soLuongTon;
    }
}

// Class Khách Hàng
class KhachHang {
    private String maKH;
    private String tenKH;

    public KhachHang(String maKH, String tenKH) {
        this.maKH = maKH;
        this.tenKH = tenKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    @Override
    public String toString() {
        return "Khách hàng: " + tenKH + " (" + maKH + ")";
    }
}

// Interface ThanhToan
interface ThanhToan {
    void thanhToanTienMat(double soTien);

    void thanhToanChuyenKhoan(double soTien);
}

// Class Hóa Đơn
class HoaDon implements ThanhToan {
    private String maHD;
    private Date ngayLap;
    private KhachHang khachHang;
    private Map<SanPham, Integer> danhSachSanPhamMua;
    private double tongTien;

    public HoaDon(String maHD, KhachHang khachHang) {
        this.maHD = maHD;
        this.khachHang = khachHang;
        this.ngayLap = new Date();
        this.danhSachSanPhamMua = new LinkedHashMap<>();
        this.tongTien = 0;
    }

    // Thêm sản phẩm vào hóa đơn
    public void themSanPham(SanPham sp, int soLuong) {
        danhSachSanPhamMua.put(sp, soLuong);
        sp.truSoLuong(soLuong); // Trừ kho
    }

    // Tính tổng tiền
    public void TinhTongTien() {
        tongTien = 0;
        for (Map.Entry<SanPham, Integer> entry : danhSachSanPhamMua.entrySet()) {
            tongTien += entry.getKey().getGia() * entry.getValue();
        }
    }

    // In hóa đơn
    public void InHoaDon() {
        System.out.println("========== HÓA ĐƠN MUA HÀNG ==========");
        System.out.println("Mã HD: " + maHD);
        System.out.println("Ngày lập: " + ngayLap);
        System.out.println(khachHang);
        System.out.println("--------------------------------------");
        for (Map.Entry<SanPham, Integer> entry : danhSachSanPhamMua.entrySet()) {
            SanPham sp = entry.getKey();
            int soLuong = entry.getValue();
            System.out.printf("%-20s SL: %d  Đơn giá: %.0f₫  Thành tiền: %.0f₫\n",
                    sp.getTenSP(), soLuong, sp.getGia(), sp.getGia() * soLuong);
        }
        System.out.println("--------------------------------------");
        System.out.printf("TỔNG TIỀN: %.0f₫\n", tongTien);
        System.out.println("======================================");
    }

    // Hình thức thanh toán
    @Override
    public void thanhToanTienMat(double soTien) {
        System.out.println("Thanh toán tiền mặt: " + soTien + "₫");
    }

    @Override
    public void thanhToanChuyenKhoan(double soTien) {
        System.out.println("Thanh toán chuyển khoản: " + soTien + "₫");
    }
}