package com.fntx.common.domain.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 酒店静态房型信息请求model
 * @author: 魏世杰
 * @date: Created in 2019/8/5 10:20
 * @version: 1.0
 * @modified By:
 */
public class HotelRoomDetailReqModel implements Serializable {


    /**
     * SearchCandidate : {"HotelID":391650,"RoomIDs":[]}
     * Settings : {"PrimaryLangID":"en","ExtendedNodes":["RoomTypeInfo.Facilities","RoomTypeInfo.Pictures","RoomTypeInfo.Descriptions","RoomTypeInfo.Smoking","RoomTypeInfo.BroadNet","RoomTypeInfo.ChildLimit","RoomTypeInfo.RoomBedInfos","RoomInfo.ApplicabilityInfo","RoomInfo.AreaApplicabilityInfo","RoomInfo.Smoking","RoomInfo.BroadNet","RoomInfo.RoomBedInfos","RoomInfo.RoomFGToPPInfo","RoomInfo.RoomGiftInfos","RoomInfo.ChannelLimit","RoomInfo.ExpressCheckout","RoomInfo.RoomTags","RoomInfo.BookingRules","RoomInfo.Descriptions"]}
     */

    private SearchCandidateBean SearchCandidate;
    private SettingsBean Settings;

    public SearchCandidateBean getSearchCandidate() {
        return SearchCandidate;
    }

    public void setSearchCandidate(SearchCandidateBean SearchCandidate) {
        this.SearchCandidate = SearchCandidate;
    }

    public SettingsBean getSettings() {
        return Settings;
    }

    public void setSettings(SettingsBean Settings) {
        this.Settings = Settings;
    }

    public static class SearchCandidateBean {
        /**
         * HotelID : 391650
         * RoomIDs : []
         */

        private Long HotelID;
        private List<?> RoomIDs;

        public Long getHotelID() {
            return HotelID;
        }

        public void setHotelID(Long HotelID) {
            this.HotelID = HotelID;
        }

        public List<?> getRoomIDs() {
            return RoomIDs;
        }

        public void setRoomIDs(List<?> RoomIDs) {
            this.RoomIDs = RoomIDs;
        }
    }

    public static class SettingsBean {
        /**
         * PrimaryLangID : en
         * ExtendedNodes : ["RoomTypeInfo.Facilities","RoomTypeInfo.Pictures","RoomTypeInfo.Descriptions","RoomTypeInfo.Smoking","RoomTypeInfo.BroadNet","RoomTypeInfo.ChildLimit","RoomTypeInfo.RoomBedInfos","RoomInfo.ApplicabilityInfo","RoomInfo.AreaApplicabilityInfo","RoomInfo.Smoking","RoomInfo.BroadNet","RoomInfo.RoomBedInfos","RoomInfo.RoomFGToPPInfo","RoomInfo.RoomGiftInfos","RoomInfo.ChannelLimit","RoomInfo.ExpressCheckout","RoomInfo.RoomTags","RoomInfo.BookingRules","RoomInfo.Descriptions"]
         */

        private String PrimaryLangID;
        private List<String> ExtendedNodes;

        public String getPrimaryLangID() {
            return PrimaryLangID;
        }

        public void setPrimaryLangID(String PrimaryLangID) {
            this.PrimaryLangID = PrimaryLangID;
        }

        public List<String> getExtendedNodes() {
            return ExtendedNodes;
        }

        public void setExtendedNodes(List<String> ExtendedNodes) {
            this.ExtendedNodes = ExtendedNodes;
        }
    }
}
