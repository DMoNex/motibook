package com.example.motibook;

public class EventListItem {
    private String addressHead;
    private String addressTail;
    private String date; // date 는 "yyyy-MM-ddTHH:mm:ss" 와 같은 문자열
    private String startTime;
    private String endTime;
    private String eventName;
    private int eventType;
    private String locate;

    private String authorName;
    private String bookName;
    private String URL;

    public EventListItem() {
        this.addressHead = "";
        this.addressTail = "";
        this.date = "";
        this.startTime = "";
        this.endTime = "";
        this.eventName = "";
        this.eventType = 0;
        this.locate = "";

        this.authorName = "";
        this.bookName = "";
        this.URL = "";
    }

    public EventListItem(String addressHead, String addressTail, String date, String startTime, String endTime,
                         String eventName, int eventType, String locate) {
        this.addressHead = addressHead;
        this.addressTail = addressTail;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventName = eventName;
        this.eventType = eventType;
        this.locate = locate;

        this.authorName = "";
        this.bookName = "";
        this.URL = "";
    }


    public String getAddressHead() {
        return addressHead;
    }

    public void setAddressHead(String addressHead) {
        this.addressHead = addressHead;
    }

    public String getAddressTail() {
        return addressTail;
    }

    public void setAddressTail(String addressTail) {
        this.addressTail = addressTail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getStrAddress() {
        String strAddress = "";
        switch (this.addressHead) {
            case "11":
                strAddress += "서울특별시 ";
                strAddress += getStrAddressSeoul();
                break;
            case "21":
                strAddress += "부산광역시 ";
                strAddress += getStrAddressBusan();
                break;
            case "22":
                strAddress += "대구광역시 ";
                strAddress += getStrAddressDaegu();
                break;
            case "23":
                strAddress += "인천광역시 ";
                strAddress += getStrAddressIncheon();
                break;
            case "24":
                strAddress += "광주광역시 ";
                strAddress += getStrAddressGwangju();
                break;
            case "25":
                strAddress += "대전광역시 ";
                strAddress += getStrAddressDaejeon();
                break;
            case "26":
                strAddress += "울산광역시 ";
                strAddress += getStrAddressUlsan();
                break;
            case "29": // 세종 세부주소 없음
                strAddress += "세종특별자치시 ";
                break;
            case "31":
                strAddress += "경기도 ";
                strAddress += getStrAddressGyeonggi();
                break;
            case "32":
                strAddress += "강원도 ";
                strAddress += getStrAddressGangwon();
                break;
            case "33":
                strAddress += "충청북도 ";
                strAddress += getStrAddressChungcheongN();
                break;
            case "34":
                strAddress += "충청남도 ";
                strAddress += getStrAddressChungcheongS();
                break;
            case "35":
                strAddress += "전라북도 ";
                strAddress += getStrAddressJeollaN();
                break;
            case "36":
                strAddress += "전라남도 ";
                strAddress += getStrAddressJeollaS();
                break;
            case "37":
                strAddress += "경상북도 ";
                strAddress += getStrAddressGyeongsangN();
                break;
            case "38":
                strAddress += "경상남도 ";
                strAddress += getStrAddressGyeongsangS();
                break;
            case "39":
                strAddress += "제주특별자치도 ";
                strAddress += getStrAddressJeju();
                break;
            default:
                break;
        }
        return strAddress;
    }

    private String getStrAddressSeoul() {
        String strAddress = "";
        switch (this.addressTail) {
            case "010":
                return (strAddress += "종로구");
            case "020":
                return (strAddress += "중구");
            case "030":
                return (strAddress += "용산구");
            case "040":
                return (strAddress += "성동구");
            case "050":
                return (strAddress += "광진구");
            case "060":
                return (strAddress += "동대문구");
            case "070":
                return (strAddress += "중랑구");
            case "080":
                return (strAddress += "성북구");
            case "090":
                return (strAddress += "강북구");
            case "100":
                return (strAddress += "도봉구");
            case "110":
                return (strAddress += "노원구");
            case "120":
                return (strAddress += "은평구");
            case "130":
                return (strAddress += "서대문구");
            case "140":
                return (strAddress += "마포구");
            case "150":
                return (strAddress += "양천구");
            case "160":
                return (strAddress += "강서구");
            case "170":
                return (strAddress += "구로구");
            case "180":
                return (strAddress += "금천구");
            case "190":
                return (strAddress += "영등포구");
            case "200":
                return (strAddress += "동작구");
            case "210":
                return (strAddress += "관악구");
            case "220":
                return (strAddress += "서초구");
            case "230":
                return (strAddress += "강남구");
            case "240":
                return (strAddress += "송파구");
            case "250":
                return (strAddress += "강동구");
            default:
                return strAddress;
        }
    }

    private String getStrAddressBusan() {
        String strAddress = "";
        switch (this.addressTail) {
            case "010":
                return (strAddress += "중구");
            case "020":
                return (strAddress += "서구");
            case "030":
                return (strAddress += "동구");
            case "040":
                return (strAddress += "영도구");
            case "050":
                return (strAddress += "부산진구");
            case "060":
                return (strAddress += "동래구");
            case "070":
                return (strAddress += "남구");
            case "080":
                return (strAddress += "북구");
            case "090":
                return (strAddress += "해운대구");
            case "100":
                return (strAddress += "사하구");
            case "110":
                return (strAddress += "금정구");
            case "120":
                return (strAddress += "강서구");
            case "130":
                return (strAddress += "연제구");
            case "140":
                return (strAddress += "수영구");
            case "150":
                return (strAddress += "사상구");
            case "510":
                return (strAddress += "기장군");
            default:
                return strAddress;
        }
    }

    private String getStrAddressDaegu() {
        String strAddress = "";
        switch (this.addressTail) {
            case "010":
                return (strAddress += "중구");
            case "020":
                return (strAddress += "동구");
            case "030":
                return (strAddress += "서구");
            case "040":
                return (strAddress += "남구");
            case "050":
                return (strAddress += "북구");
            case "060":
                return (strAddress += "수성구");
            case "070":
                return (strAddress += "달서구");
            case "510":
                return (strAddress += "달성군");
            default:
                return strAddress;
        }
    }

    private String getStrAddressIncheon() {
        String strAddress = "";
        switch (this.addressTail) {
            case "010":
                return (strAddress += "중구");
            case "020":
                return (strAddress += "동구");
            case "040":
                return (strAddress += "연수구");
            case "050":
                return (strAddress += "남동구");
            case "060":
                return (strAddress += "부평구");
            case "070":
                return (strAddress += "계양구");
            case "080":
                return (strAddress += "서구");
            case "090":
                return (strAddress += "미추홀구");
            case "510":
                return (strAddress += "강화군");
            case "520":
                return (strAddress += "옹진군");
            default:
                return strAddress;
        }
    }

    private String getStrAddressGwangju() {
        String strAddress = "";
        switch (this.addressTail) {
            case "010":
                return (strAddress += "동구");
            case "020":
                return (strAddress += "서구");
            case "030":
                return (strAddress += "남구");
            case "040":
                return (strAddress += "북구");
            case "050":
                return (strAddress += "광산구");
            default:
                return strAddress;
        }
    }

    private String getStrAddressDaejeon() {
        String strAddress = "";
        switch (this.addressTail) {
            case "010":
                return (strAddress += "동구");
            case "020":
                return (strAddress += "중구");
            case "030":
                return (strAddress += "서구");
            case "040":
                return (strAddress += "유성구");
            case "050":
                return (strAddress += "대덕구");
            default:
                return strAddress;
        }
    }

    private String getStrAddressUlsan() {
        String strAddress = "";
        switch (this.addressTail) {
            case "010":
                return (strAddress += "중구");
            case "020":
                return (strAddress += "남구");
            case "030":
                return (strAddress += "동구");
            case "040":
                return (strAddress += "북구");
            case "510":
                return (strAddress += "울주군");
            default:
                return strAddress;
        }
    }

    private String getStrAddressGyeonggi() {
        String strAddress = "";
        switch (this.addressTail) {
            case "010":
                return (strAddress += "수원시");
            case "020":
                return (strAddress += "성남시");
            case "030":
                return (strAddress += "의정부시");
            case "040":
                return (strAddress += "안양시");
            case "050":
                return (strAddress += "부천시");
            case "060":
                return (strAddress += "광명시");
            case "070":
                return (strAddress += "평택시");
            case "080":
                return (strAddress += "동두천시");
            case "090":
                return (strAddress += "안산시");
            case "100":
                return (strAddress += "고양시");
            case "110":
                return (strAddress += "과천시");
            case "120":
                return (strAddress += "구리시");
            case "130":
                return (strAddress += "남양주시");
            case "140":
                return (strAddress += "오산시");
            case "150":
                return (strAddress += "시흥시");
            case "160":
                return (strAddress += "군포시");
            case "170":
                return (strAddress += "의왕시");
            case "180":
                return (strAddress += "하남시");
            case "190":
                return (strAddress += "용인시");
            case "200":
                return (strAddress += "파주시");
            case "210":
                return (strAddress += "이천시");
            case "220":
                return (strAddress += "안성시");
            case "230":
                return (strAddress += "김포시");
            case "240":
                return (strAddress += "화성시");
            case "250":
                return (strAddress += "광주시");
            case "260":
                return (strAddress += "양주시");
            case "270":
                return (strAddress += "포천시");
            case "280":
                return (strAddress += "여주시");
            case "550":
                return (strAddress += "연천군");
            case "570":
                return (strAddress += "가평군");
            case "580":
                return (strAddress += "양평군");
            default:
                return strAddress;
        }
    }

    private String getStrAddressGangwon() {
        String strAddress = "";
        switch (this.addressTail) {
            case "010":
                return (strAddress += "춘천시");
            case "020":
                return (strAddress += "원주시");
            case "030":
                return (strAddress += "강릉시");
            case "040":
                return (strAddress += "동해시");
            case "050":
                return (strAddress += "태백시");
            case "060":
                return (strAddress += "속초시");
            case "070":
                return (strAddress += "삼척시");
            case "510":
                return (strAddress += "홍천군");
            case "520":
                return (strAddress += "횡성군");
            case "530":
                return (strAddress += "영월군");
            case "540":
                return (strAddress += "평창군");
            case "550":
                return (strAddress += "정선군");
            case "560":
                return (strAddress += "철원군");
            case "570":
                return (strAddress += "화천군");
            case "580":
                return (strAddress += "양구군");
            case "590":
                return (strAddress += "인제군");
            case "600":
                return (strAddress += "고성군");
            case "610":
                return (strAddress += "양양군");
            default:
                return strAddress;
        }
    }

    private String getStrAddressChungcheongN() {
        String strAddress = "";
        switch (this.addressTail) {
            case "020":
                return (strAddress += "충주시");
            case "030":
                return (strAddress += "제천시");
            case "040":
                return (strAddress += "청주시");
            case "520":
                return (strAddress += "보은군");
            case "530":
                return (strAddress += "옥천군");
            case "540":
                return (strAddress += "영동군");
            case "550":
                return (strAddress += "진천군");
            case "560":
                return (strAddress += "괴산군");
            case "570":
                return (strAddress += "음성군");
            case "580":
                return (strAddress += "단양군");
            case "590":
                return (strAddress += "증평군");
            default:
                return strAddress;
        }
    }

    private String getStrAddressChungcheongS() {
        String strAddress = "";
        switch (this.addressTail) {
            case "010":
                return (strAddress += "천안시");
            case "020":
                return (strAddress += "공주시");
            case "030":
                return (strAddress += "보령시");
            case "040":
                return (strAddress += "아산시");
            case "050":
                return (strAddress += "서산시");
            case "060":
                return (strAddress += "논산시");
            case "070":
                return (strAddress += "계룡시");
            case "080":
                return (strAddress += "당진시");
            case "510":
                return (strAddress += "금산군");
            case "530":
                return (strAddress += "부여군");
            case "540":
                return (strAddress += "서천군");
            case "550":
                return (strAddress += "청양군");
            case "560":
                return (strAddress += "홍성군");
            case "570":
                return (strAddress += "예산군");
            case "580":
                return (strAddress += "태안군");
            default:
                return strAddress;
        }
    }

    private String getStrAddressJeollaN() {
        String strAddress = "";
        switch (this.addressTail) {
            case "010":
                return (strAddress += "전주시");
            case "020":
                return (strAddress += "군산시");
            case "030":
                return (strAddress += "익산시");
            case "040":
                return (strAddress += "정읍시");
            case "050":
                return (strAddress += "남원시");
            case "060":
                return (strAddress += "김제시");
            case "510":
                return (strAddress += "완주군");
            case "520":
                return (strAddress += "진안군");
            case "530":
                return (strAddress += "무주군");
            case "540":
                return (strAddress += "장수군");
            case "550":
                return (strAddress += "임실군");
            case "560":
                return (strAddress += "순창군");
            case "570":
                return (strAddress += "고창군");
            case "580":
                return (strAddress += "부안군");
            default:
                return strAddress;
        }
    }

    private String getStrAddressJeollaS() {
        String strAddress = "";
        switch (this.addressTail) {
            case "010":
                return (strAddress += "목포시");
            case "020":
                return (strAddress += "여수시");
            case "030":
                return (strAddress += "순천시");
            case "040":
                return (strAddress += "나주시");
            case "060":
                return (strAddress += "광양시");
            case "510":
                return (strAddress += "담양군");
            case "520":
                return (strAddress += "곡성군");
            case "530":
                return (strAddress += "구례군");
            case "550":
                return (strAddress += "고흥군");
            case "560":
                return (strAddress += "보성군");
            case "570":
                return (strAddress += "화순군");
            case "580":
                return (strAddress += "장흥군");
            case "590":
                return (strAddress += "강진군");
            case "600":
                return (strAddress += "해남군");
            case "610":
                return (strAddress += "영암군");
            case "620":
                return (strAddress += "무안군");
            case "630":
                return (strAddress += "함평군");
            case "640":
                return (strAddress += "영광군");
            case "650":
                return (strAddress += "장성군");
            case "660":
                return (strAddress += "완도군");
            case "670":
                return (strAddress += "진도군");
            case "680":
                return (strAddress += "신안군");
            default:
                return strAddress;
        }
    }

    private String getStrAddressGyeongsangN() {
        String strAddress = "";
        switch (this.addressTail) {
            case "010":
                return (strAddress += "포항시");
            case "020":
                return (strAddress += "경주시");
            case "030":
                return (strAddress += "김천시");
            case "040":
                return (strAddress += "안동시");
            case "050":
                return (strAddress += "구미시");
            case "060":
                return (strAddress += "영주시");
            case "070":
                return (strAddress += "영천시");
            case "080":
                return (strAddress += "상주시");
            case "090":
                return (strAddress += "문경시");
            case "100":
                return (strAddress += "경산시");
            case "510":
                return (strAddress += "군위군");
            case "520":
                return (strAddress += "의성군");
            case "530":
                return (strAddress += "청송군");
            case "540":
                return (strAddress += "영양군");
            case "550":
                return (strAddress += "영덕군");
            case "560":
                return (strAddress += "청도군");
            case "570":
                return (strAddress += "고령군");
            case "580":
                return (strAddress += "성주군");
            case "590":
                return (strAddress += "칠곡군");
            case "600":
                return (strAddress += "예천군");
            case "610":
                return (strAddress += "봉화군");
            case "620":
                return (strAddress += "울진군");
            case "630":
                return (strAddress += "울릉군");
            default:
                return strAddress;
        }
    }

    private String getStrAddressGyeongsangS() {
        String strAddress = "";
        switch (this.addressTail) {
            case "030":
                return (strAddress += "진주시");
            case "050":
                return (strAddress += "통영시");
            case "060":
                return (strAddress += "사천시");
            case "070":
                return (strAddress += "김해시");
            case "080":
                return (strAddress += "밀양시");
            case "090":
                return (strAddress += "거제시");
            case "100":
                return (strAddress += "양산시");
            case "110":
                return (strAddress += "창원시");
            case "510":
                return (strAddress += "의령군");
            case "520":
                return (strAddress += "함안군");
            case "530":
                return (strAddress += "창녕군");
            case "540":
                return (strAddress += "고성군");
            case "550":
                return (strAddress += "남해군");
            case "560":
                return (strAddress += "하동군");
            case "570":
                return (strAddress += "산청군");
            case "580":
                return (strAddress += "함양군");
            case "590":
                return (strAddress += "거창군");
            case "600":
                return (strAddress += "합천군");
            default:
                return strAddress;
        }
    }

    private String getStrAddressJeju() {
        String strAddress = "";
        switch (this.addressTail) {
            case "010":
                return (strAddress += "제주시");
            case "020":
                return (strAddress += "서귀포시");
            default:
                return strAddress;
        }
    }
}