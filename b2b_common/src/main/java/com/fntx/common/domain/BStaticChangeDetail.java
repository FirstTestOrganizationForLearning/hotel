package com.fntx.common.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 静态信息增量明细表
 * </p>
 *
 * @author wang
 * @since 2019-08-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("B_STATIC_CHANGE_DETAIL")
public class BStaticChangeDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 增量明细信息ID
     */
    @TableField("CHANGEDETAIL_ID")
    private Long changedetailId;

    /**
     * 增量类型
     */
    @TableField("NAME")
    private String name;

    /**
     * 增量内容
     */
    @TableField("NEWVALUE")
    private String newvalue;

    /**
     * 静态信息增量更新ID
     */
    @TableField("CHANGE_ID")
    private Long changeId;


}
