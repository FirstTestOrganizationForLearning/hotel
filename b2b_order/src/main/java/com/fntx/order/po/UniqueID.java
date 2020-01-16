package com.fntx.order.po;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @Author: 渠猛
 * @Date: 2019/7/19 0019 下午 3:20
 * @Description:
 *
 * Type与ID相对应
 * 参见com.fntx.common.constant.HotelUniqueIDType
 *
 */
  
public  class UniqueID {
        /**
         * Type : 1
         * ID : 92893cf1-8c92-4fb2-9fef-3570535f2dc4
         */
        private String Type;
        private String ID;

        public String getType() {
            return Type;
        }

        public void setType(String Type) {
            this.Type = Type;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }
    }