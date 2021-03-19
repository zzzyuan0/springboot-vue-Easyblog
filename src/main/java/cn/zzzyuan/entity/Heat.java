package cn.zzzyuan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Heat implements Serializable {

    private Long id;

    private String title;

    private Long score;

}
