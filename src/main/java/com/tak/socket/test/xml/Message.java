package com.tak.socket.test.xml;

import lombok.Data;
import lombok.Setter;

import javax.xml.bind.annotation.*;

//@Data
//@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@XmlType(propOrder = {"body", "header"})
@XmlRootElement(name = "Document")
public class Message {

	private MsgHeader header;

	private MsgBody body;

	private String abc;

	@XmlElement(name = "head")
	public MsgHeader getHeader() {
		return header;
	}

	@XmlElement(name = "body")
	public MsgBody getBody() {
		return body;
	}

	@XmlAttribute(name = "abc")
	public String getAbc() {
		return abc;
	}

}
