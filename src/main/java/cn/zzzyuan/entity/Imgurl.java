package cn.zzzyuan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author 杂货店的阿猿
 * @since 2021-01-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_imgurl")
public class Imgurl implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String imgurl;


}
