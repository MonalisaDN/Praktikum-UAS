public class dataorang {

    private String nama = null;
    private int id;
    private String umur = null;
    private String tinggi = null;
    private String berat = null;

    public dataorang(int id, String nama, String umur, String tinggi, String berat) {
        this.nama = nama;
        this.id = id;
        this.umur = umur;
        this.tinggi = tinggi;
        this.berat = berat;
    }

    public String getNama() {
        return nama;
    }

    public int getId() {
        return id;
    }

    public String getUmur() {
        return umur;
    }

    public String getTinggi() {
        return tinggi;
    }

    public String getBerat() {
        return berat;
    }

}