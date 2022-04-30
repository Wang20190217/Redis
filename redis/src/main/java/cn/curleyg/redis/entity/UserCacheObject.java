package cn.curleyg.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 城南花已开<br>
 *
 * @Description: <br>
 * @Project: <br>
 * @CreateDate: Created in 2022/4/29 23:54 <br>
 * @Author: Wang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCacheObject implements Serializable {
    private Integer id;
    private String name;
    private Integer gender;

}
