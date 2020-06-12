package com.example.tugasakhirsatunya.Pojo;

import java.sql.Timestamp;
import java.util.Date;

public class Komentar {

    private Integer id;
    private Long pelapor_id;



    private Integer laporan_id;
    private String  komentar_isi;
    private String created_at;
    private String updated_at;





    public Integer getId() {
        return id;
    }

    public Long getPelapor_id() { return pelapor_id;
    }

    public Integer getLaporan_id() { return laporan_id;
    }

    public String getKomentar_isi() { return komentar_isi;
    }

    public String getCreated_at() { return created_at;
    }

    public String getUpdated_at() { return updated_at;
    }






}
