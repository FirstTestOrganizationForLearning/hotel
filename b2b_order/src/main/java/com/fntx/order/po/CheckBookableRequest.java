package com.fntx.order.po;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Copyright (C), 2019, 弗恩天下
 * @FileName
 * @Author 胡庆康
 * @Date 2019/7/25 10:06
 */
@NoArgsConstructor
@Data
public class CheckBookableRequest {
    /**
     * AvailRequestSegments : {"AvailRequestSegment":{"HotelSearchCriteria":{"Criterion":{"HotelRef":{"HotelCode":"718450"},"StayDateRange":{"Start":"2018-01-08","End":"2018-01-10"},"PlanCandidates":{"PlanCandidate":{"RoomID":"105425599","PlanID":"11396002","PlanCategory":"501","PrepaidIndicator":true}},"RoomStayCandidates":{"RoomStayCandidate":{"BookingCode":"105425599","GuestCounts":{"GuestCount":{"Count":1}},"Quantity":1}},"TPA_Extensions":{"LateArrivalTime":"2018-01-08T20:00:00+08:00","DisplayCurrency":"CNY","SpecialRequests":[{"Text":"","Name":"BedType","ParagraphNumber":"7","RequestCode":"7","CodeContext":"一定安排大床"}],"ShadowRoomInfo":{"ShadowID":1100012201}}}}}}
     * TimeStamp : 2016-11-11T00:00:00.0000000
     * Version : 1
     * PrimaryLangID : en
     * TransactionIdentifier : 7422CF9F311E48388D9ABA2AC7B54052
     */

    private AvailRequestSegments AvailRequestSegments;
    private String TimeStamp;
    private int Version;
    private String PrimaryLangID;
    private String TransactionIdentifier;

    @NoArgsConstructor
    @Data
    public static class AvailRequestSegments {
        /**
         * AvailRequestSegment : {"HotelSearchCriteria":{"Criterion":{"HotelRef":{"HotelCode":"718450"},"StayDateRange":{"Start":"2018-01-08","End":"2018-01-10"},"PlanCandidates":{"PlanCandidate":{"RoomID":"105425599","PlanID":"11396002","PlanCategory":"501","PrepaidIndicator":true}},"RoomStayCandidates":{"RoomStayCandidate":{"BookingCode":"105425599","GuestCounts":{"GuestCount":{"Count":1}},"Quantity":1}},"TPA_Extensions":{"LateArrivalTime":"2018-01-08T20:00:00+08:00","DisplayCurrency":"CNY","SpecialRequests":[{"Text":"","Name":"BedType","ParagraphNumber":"7","RequestCode":"7","CodeContext":"一定安排大床"}],"ShadowRoomInfo":{"ShadowID":1100012201}}}}}
         */

        private AvailRequestSegment AvailRequestSegment;

        @NoArgsConstructor
        @Data
        public static class AvailRequestSegment {
            /**
             * HotelSearchCriteria : {"Criterion":{"HotelRef":{"HotelCode":"718450"},"StayDateRange":{"Start":"2018-01-08","End":"2018-01-10"},"PlanCandidates":{"PlanCandidate":{"RoomID":"105425599","PlanID":"11396002","PlanCategory":"501","PrepaidIndicator":true}},"RoomStayCandidates":{"RoomStayCandidate":{"BookingCode":"105425599","GuestCounts":{"GuestCount":{"Count":1}},"Quantity":1}},"TPA_Extensions":{"LateArrivalTime":"2018-01-08T20:00:00+08:00","DisplayCurrency":"CNY","SpecialRequests":[{"Text":"","Name":"BedType","ParagraphNumber":"7","RequestCode":"7","CodeContext":"一定安排大床"}],"ShadowRoomInfo":{"ShadowID":1100012201}}}}
             */

            private HotelSearchCriteria HotelSearchCriteria;

            @NoArgsConstructor
            @Data
            public static class HotelSearchCriteria {
                /**
                 * Criterion : {"HotelRef":{"HotelCode":"718450"},"StayDateRange":{"Start":"2018-01-08","End":"2018-01-10"},"PlanCandidates":{"PlanCandidate":{"RoomID":"105425599","PlanID":"11396002","PlanCategory":"501","PrepaidIndicator":true}},"RoomStayCandidates":{"RoomStayCandidate":{"BookingCode":"105425599","GuestCounts":{"GuestCount":{"Count":1}},"Quantity":1}},"TPA_Extensions":{"LateArrivalTime":"2018-01-08T20:00:00+08:00","DisplayCurrency":"CNY","SpecialRequests":[{"Text":"","Name":"BedType","ParagraphNumber":"7","RequestCode":"7","CodeContext":"一定安排大床"}],"ShadowRoomInfo":{"ShadowID":1100012201}}}
                 */

                private Criterion Criterion;

                @NoArgsConstructor
                @Data
                public static class Criterion {
                    /**
                     * HotelRef : {"HotelCode":"718450"}
                     * StayDateRange : {"Start":"2018-01-08","End":"2018-01-10"}
                     * PlanCandidates : {"PlanCandidate":{"RoomID":"105425599","PlanID":"11396002","PlanCategory":"501","PrepaidIndicator":true}}
                     * RoomStayCandidates : {"RoomStayCandidate":{"BookingCode":"105425599","GuestCounts":{"GuestCount":{"Count":1}},"Quantity":1}}
                     * TPA_Extensions : {"LateArrivalTime":"2018-01-08T20:00:00+08:00","DisplayCurrency":"CNY","SpecialRequests":[{"Text":"","Name":"BedType","ParagraphNumber":"7","RequestCode":"7","CodeContext":"一定安排大床"}],"ShadowRoomInfo":{"ShadowID":1100012201}}
                     */

                    private HotelRef HotelRef;
                    private StayDateRange StayDateRange;
                    private PlanCandidates PlanCandidates;
                    private RoomStayCandidates RoomStayCandidates;
                    private TPAExtensions TPA_Extensions;

                    @NoArgsConstructor
                    @Data
                    public static class HotelRef {
                        /**
                         * HotelCode : 718450
                         */

                        private String HotelCode;
                    }

                    @NoArgsConstructor
                    @Data
                    public static class StayDateRange {
                        /**
                         * Start : 2018-01-08
                         * End : 2018-01-10
                         */

                        private String Start;
                        private String End;
                    }

                    @NoArgsConstructor
                    @Data
                    public static class PlanCandidates {
                        /**
                         * PlanCandidate : {"RoomID":"105425599","PlanID":"11396002","PlanCategory":"501","PrepaidIndicator":true}
                         */

                        private PlanCandidate PlanCandidate;

                        @NoArgsConstructor
                        @Data
                        public static class PlanCandidate {
                            /**
                             * RoomID : 105425599
                             * PlanID : 11396002
                             * PlanCategory : 501
                             * PrepaidIndicator : true
                             */

                            private String RoomID;
                            private String PlanID;
                            private String PlanCategory;
                            private boolean PrepaidIndicator;
                        }
                    }

                    @NoArgsConstructor
                    @Data
                    public static class RoomStayCandidates {
                        /**
                         * RoomStayCandidate : {"BookingCode":"105425599","GuestCounts":{"GuestCount":{"Count":1}},"Quantity":1}
                         */

                        private RoomStayCandidate RoomStayCandidate;

                        @NoArgsConstructor
                        @Data
                        public static class RoomStayCandidate {
                            /**
                             * BookingCode : 105425599
                             * GuestCounts : {"GuestCount":{"Count":1}}
                             * Quantity : 1
                             */

                            private String BookingCode;
                            private GuestCounts GuestCounts;
                            private int Quantity;

                            @NoArgsConstructor
                            @Data
                            public static class GuestCounts {
                                /**
                                 * GuestCount : {"Count":1}
                                 */

                                private GuestCount GuestCount;

                                @NoArgsConstructor
                                @Data
                                public static class GuestCount {
                                    /**
                                     * Count : 1
                                     */

                                    private int Count;
                                }
                            }
                        }
                    }

                    @NoArgsConstructor
                    @Data
                    public static class TPAExtensions {
                        /**
                         * LateArrivalTime : 2018-01-08T20:00:00+08:00
                         * DisplayCurrency : CNY
                         * SpecialRequests : [{"Text":"","Name":"BedType","ParagraphNumber":"7","RequestCode":"7","CodeContext":"一定安排大床"}]
                         * ShadowRoomInfo : {"ShadowID":1100012201}
                         */

                        private String LateArrivalTime;
                        private String DisplayCurrency;
                        private ShadowRoomInfo ShadowRoomInfo;
                        private List<SpecialRequests> SpecialRequests;

                        @NoArgsConstructor
                        @Data
                        public static class ShadowRoomInfo {
                            /**
                             * ShadowID : 1100012201
                             */

                            private int ShadowID;
                        }

                        @NoArgsConstructor
                        @Data
                        public static class SpecialRequests {
                            /**
                             * Text :
                             * Name : BedType
                             * ParagraphNumber : 7
                             * RequestCode : 7
                             * CodeContext : 一定安排大床
                             */

                            private String Text;
                            private String Name;
                            private String ParagraphNumber;
                            private String RequestCode;
                            private String CodeContext;
                        }
                    }
                }
            }
        }
    }
}
