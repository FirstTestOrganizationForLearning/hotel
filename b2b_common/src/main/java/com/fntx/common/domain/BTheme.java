package com.fntx.common.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 酒店主题信息表
 * </p>
 *
 * @author kang
 * @since 2019-07-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("B_THEME")
public class BTheme implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主题名称
     */
    @TableField("THEME_NAME")
    private String themeName;
    @TableId("THEME_ID")
    private String themeId;

}
