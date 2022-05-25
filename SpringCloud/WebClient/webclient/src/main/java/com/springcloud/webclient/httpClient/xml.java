package com.springcloud.webclient.httpClient;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;

@Getter
@XmlRootElement(name = "OpenAPI_ServiceResponse")
public class xml {

    @XmlRootElement(name = "header")
    public static class header {

        @XmlAttribute(name = "resultCode")
        String resultCode;

        @XmlAttribute(name = "resultMsg")
        String resultMsg;

    }

    @XmlRootElement(name = "body")
    public static class body {

        @XmlElement(name = "items")
        List<item> items;
    }

    @XmlRootElement(name = "item")
    public static class item {

        @XmlAttribute(name = "dateKind")
        String dateKind;
        @XmlAttribute(name = "dateName")
        String dateName;
        @XmlAttribute(name = "isHoliday")
        String isHoliday;
        @XmlAttribute(name = "locdate")
        String locdate;
        @XmlAttribute(name = "seq")
        String seq;
    }

}
